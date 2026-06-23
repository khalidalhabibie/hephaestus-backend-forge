package com.fif.finance_training.web;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class StructuredLogger {

    // PII field names (case-insensitive partial match)
    private static final String[] PII_FIELDS = {
        "password", "token", "secret", "pin", "credential",
        "nik", "email", "phone", "phoneNumber", "fullName"
    };

    public void info(String event, String message, Object... keyValues) {
        log("INFO", event, message, null, keyValues);
    }

    public void warn(String event, String message, Object... keyValues) {
        log("WARN", event, message, null, keyValues);
    }

    public void error(String event, String message, Throwable error, Object... keyValues) {
        log("ERROR", event, message, error, keyValues);
    }

    public void security(String event, String message, Object... keyValues) {
        log("SECURITY", event, message, null, keyValues);
    }

    private void log(String level, String event, String message, Throwable error, Object... keyValues) {

        Map<String, String> context = new HashMap<>();
        for (int i = 0; i < keyValues.length; i += 2) {
            if (i + 1 < keyValues.length) {
                String key = String.valueOf(keyValues[i]);
                String value = maskIfSensitive(key, String.valueOf(keyValues[i + 1]));
                context.put(key, value);
            }
        }

        String contextStr = context.entrySet().stream()
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining(", "));

        // Format: timestamp | level | correlationId | event | message | context
        String correlationId = MDC.get("correlationId");
        if (correlationId == null) correlationId = "unknown";

        String logMessage = String.format("timestamp=%s, level=%s, correlationId=%s, event=%s, message=%s, %s",
                Instant.now().toString(), level, correlationId, event, message, contextStr);

        switch (level) {
            case "ERROR" -> log.error(logMessage, error);
            case "WARN", "SECURITY" -> log.warn(logMessage);
            default -> log.info(logMessage);
        }
    }

    private String maskIfSensitive(String key, String value) {
        String lowerKey = key.toLowerCase();

        // Direct field name match
        for (String pii : PII_FIELDS) {
            if (lowerKey.contains(pii.toLowerCase())) {
                return mask(value);
            }
        }

        // Auto-detect NIK pattern in value (16 digits)
        if (value.matches("\\d{16}")) {
            return maskNik(value);
        }

        return value;
    }

    private String mask(String value) {
        if (value == null || value.length() <= 4) return "****";
        return value.substring(0, 2) + "****" + value.substring(value.length() - 2);
    }

    private String maskNik(String nik) {
        if (nik.length() != 16) return mask(nik);
        return nik.substring(0, 6) + "********" + nik.substring(14);
    }
}