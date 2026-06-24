SLF4J = Simple Logging Facade for Java
Bayangin SLF4J itu kayak colokan universal buat charger. Lu punya colokan (SLF4J), nanti bisa colok ke berbagai merek charger (Logback, Log4j, dll).

MDC = Mapped Diagnostic Context
Bayangin MDC itu kotak penyimpanan sementara yang melekat di satu thread. Setiap request masuk, Spring bikin thread baru (atau ambil dari pool). MDC itu kotak yang ikut thread tersebut.

```Thread-1 (Request dari Budi) → MDC: {correlationId: "abc123"}
Thread-2 (Request dari Ani)   → MDC: {correlationId: "xyz789"}```

MDC-nya Thread-1 nggak bisa diakses Thread-2. Jadi aman, nggak ketukar.

Flow Lengkap :
STEP-1
┌─────────────────────────────────────────┐
│  Spring Boot Start                      │
│  - Load semua @Component                │
│  - CorrelationIdFilter terdaftar        │
│  - Order: HIGHEST_PRECEDENCE (paling    │
│    awal jalan sebelum controller)       │
└─────────────────────────────────────────┘
real-code :
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)  // ← Ini: "Gue paling penting, jalan dulu!"
public class CorrelationIdFilter extends OncePerRequestFilter {}

STEP-2 (REQUEST MASUK DI POSTMAN)
POSTMAN / BROWSER
│
│  POST http://localhost:8080/api/v1/customers
│  Header: X-Correlation-Id: abc123  ← (bisa ada, bisa nggak)
│
▼
Di Postman : Headers:
  X-Correlation-Id: abc123   ← (optional, kalau nggak ada nanti digenerate)

STEP-2 : Filter Jalan
┌─────────────────────────────────────────┐
│  CorrelationIdFilter.doFilterInternal() │
│                                         │
│  1. Cek header "X-Correlation-Id"     │
│     → Ada? "abc123" → pakai itu        │
│     → Nggak ada? → generate baru        │
│                                         │
│  2. MDC.put("correlationId", "abc123")  │
│     → Simpan ke kotak thread           │
│                                         │
│  3. response.setHeader("X-Correlation-   │
│     Id", "abc123")                      │
│     → Balikin ke client (biar client   │
│       tahu ID-nya)                      │
│                                         │
│  4. doFilter(request, response)         │
│     → Lanjut ke controller             │
│                                         │
│  5. finally { MDC.clear() }             │
│     → Bersihin kotak thread            │
│       (PENTING! biar nggak bocor ke     │
│        request berikutnya)             │
└─────────────────────────────────────────┘

real-code :
protected void doFilterInternal(HttpServletRequest request,
HttpServletResponse response,
FilterChain filterChain) {
    
// 1. EXTRACT
String correlationId = request.getHeader("X-Correlation-Id");
if (correlationId == null || correlationId.isBlank()) {
    correlationId = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
}

// 2. STORE ke MDC (kotak thread)
MDC.put("correlationId", correlationId);

// 3. SET ke response header
response.setHeader(CORRELATION_ID_HEADER, correlationId);

try {
    // 4. LANJUT ke controller
    filterChain.doFilter(request, response);
} finally {
    // 5. CLEANUP (jangan lupa!)
    MDC.clear();
    }
}

STEP-3 : CONTROLLER TERIMA REQUEST
┌─────────────────────────────────────────┐
│  CustomerController.createCustomer()    │
│                                         │
│  Nggak perlu ngapa-ngapain soal         │
│  correlation ID. MDC sudah diisi oleh   │
│  Filter.                                │
│                                         │
│  Kalau mau log, tinggal:                │
│  log.info("Creating customer...")       │
│  → otomatis MDC "correlationId" kebaca │
│    oleh logback pattern                 │
└─────────────────────────────────────────┘

STEP-4 : SERVICE LAYER LOG
┌─────────────────────────────────────────┐
│  CustomerService.createCustomer()       │
│                                         │
│  logger.info("CUSTOMER_CREATED", ...)   │
│                                         │
│  StructuredLogger.log() jalan:          │
│                                         │
│  1. Ambil correlationId dari MDC.get()  │
│     → "abc123"                          │
│                                         │
│  2. Format log:                         │
│     timestamp=..., level=INFO,           │
│     correlationId=abc123, event=...      │
│                                         │
│  3. Mask PII (nik jadi 317301**********)│
│                                         │
│  4. Output ke console                   │
└─────────────────────────────────────────┘

REAL LIFE CODE :
```// CustomerService
public CustomerResponse createCustomer(CreateCustomerRequest request) {
    // ... logic ...
    
    logger.info("CUSTOMER_CREATED", "Customer created successfully",
            "customerId", saved.getId().toString(),
            "email", saved.getEmail(),      // ← auto-mask jadi "bu****il.com"
            "nik", saved.getNik());          // ← auto-mask jadi "317301**********01"
    
    // Di balik layar, StructuredLogger ambil correlationId dari MDC
    // MDC.get("correlationId") → "abc123"
}```

STEP-5 : ERROR TERJADI (KALO ADA)
┌─────────────────────────────────────────┐
│  GlobalExceptionHandler                 │
│                                         │
│  1. Tangkep exception                   │
│  2. Ambil correlationId dari MDC        │
│  3. Log error dengan correlationId      │
│  4. Build ErrorResponse dengan          │
│     correlationId                       │
│  5. Return ke client                    │
└─────────────────────────────────────────┘

REAL-LIFE CODE :
```
@ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<ErrorResponse> handleValidationErrors(...) {
    String correlationId = CorrelationIdContext.get();  // ← ambil dari MDC
    
    logger.warn("VALIDATION_ERROR", ...);  // ← log dengan correlationId
    
    return ErrorResponse.builder()
            .correlationId(correlationId)  // ← masukin ke response body
            .build();
}```

STEP-6 RESPONSE BALIK KE CLIENT
HTTP/1.1 400 Bad Request
X-Correlation-Id: abc123                    ← Header

{
  "success": false,
  "code": "VALIDATION_ERROR",
  "message": "Invalid request",
  "correlation_id": "abc123",                ← Body
  "errors": [...]
}

"Jadi gini bro, SLF4J itu facade buat logging. Nanti di project gw, ada file CorrelationIdFilter yang jalan paling awal pas request masuk. Dia ngecek 'ada ID nggak?' kalau nggak ada dia generate. Terus dia simpen ID itu di MDC — MDC itu kotak penyimpanan per thread, jadi tiap request punya ID sendiri-sendiri nggak ketukar. Nanti pas log, tinggal ambil dari MDC, otomatis correlation ID-nya nyantol di setiap log. Kalau ada error, ID itu juga dibalikin ke client lewat response body dan header, jadi client bisa bilang 'error saya ID abc123' dan kita bisa trace log-nya."
