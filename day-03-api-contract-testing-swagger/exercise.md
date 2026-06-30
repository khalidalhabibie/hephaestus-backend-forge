# Exercise - API Contract, API Testing & Swagger

## Objective

Peserta mampu membuat Customer REST API sederhana, menulis API contract, melakukan API testing, dan membuka dokumentasi API menggunakan Swagger UI.

## Case

Buat Customer REST API menggunakan Spring Boot 2.7.x dari Spring Initializr.

API harus mendukung:

- Create customer
- Get customer list
- Get customer by id
- Update customer with PUT
- Patch customer with PATCH

Data masih disimpan di memory menggunakan `Map`.

## Task 1 - Create Project from Spring Initializr (done)

Use:

```text
Project      : Maven
Language     : Java
Spring Boot  : 2.7.x
Group        : com.example
Artifact     : demo
Packaging    : Jar
Java         : 8
```

Dependencies:

```text
Spring Web
Validation
```

## Task 2 - Add Swagger Dependency (done)

Add to `pom.xml`:

```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-ui</artifactId>
    <version>1.6.15</version>
</dependency>
```

## Task 3 - Create DTO (done)

Create:

- `CreateCustomerRequest`
- `UpdateCustomerRequest`
- `PatchCustomerRequest`
- `CustomerResponse`
- `ErrorResponse`
- `FieldErrorResponse`

Rules:

- JSON uses snake_case.
- Java fields use camelCase.
- Use `@JsonProperty`.
- Use validation annotation where needed.

## Task 4 - Create Model (done)

Create Customer model:

- `id`
- `fullName`
- `email`
- `phoneNumber`

## Task 5 - Create Service (done)

Create `CustomerService` using in-memory `Map`.

Methods:

- `createCustomer`
- `getCustomers`
- `getCustomerById`
- `updateCustomer`
- `patchCustomer`

## Task 6 - Create Controller (done)

Create `CustomerController` with:

- `POST /api/v1/customers`
- `GET /api/v1/customers`
- `GET /api/v1/customers/{id}`
- `PUT /api/v1/customers/{id}`
- `PATCH /api/v1/customers/{id}`

Use:

- `@RestController` //untuk spring mengenali ini adalah kelas kontroler
- `@RequestMapping` //untuk menulis API 
- `@PostMapping` // untuk membuat API post
- `@GetMapping` // untuk membuat API get
- `@PutMapping` // untuk membuat API put
- `@PatchMapping` //untuk membuat API patch
- `@RequestBody` // untuk mengambil isi body dari APInya
- `@PathVariable` //untuk mengambil nilai dari URL Path
- `@Valid` //untuk menghubungkan ke DTO
- `ResponseEntity` //untuk mengirim respon ke FE

## Task 7 - Add Swagger Annotation (done)

Add:

- `@Tag` //
- `@Operation` 
- `@ApiResponse`

At minimum:

- Add `@Tag` in `CustomerController`.
- Add `@Operation` for every endpoint.
- Add `@ApiResponse` for success and error response.

## Task 8 - Write API Contract (done)

Write API contract in markdown for:

- `POST /api/v1/customers`
- `GET /api/v1/customers`
- `GET /api/v1/customers/{id}`
- `PUT /api/v1/customers/{id}`
- `PATCH /api/v1/customers/{id}`

Each contract must include:

- Method
- URL
- Description
- Request body
- Success response
- Error response
- Status code

## Task 9 - Test with Postman (done)

Test:

- Valid create customer
- Invalid create customer
- Get customer list
- Get customer by id
- Get unknown customer id
- Update customer with PUT
- Patch customer with PATCH

## Task 10 - Test with Swagger UI (done)

Open:

```text
http://localhost:8080/swagger-ui.html
```

Test from Swagger UI:

- POST customer
- GET customer list
- GET customer by id
- PUT customer
- PATCH customer

## Task 11 - Check OpenAPI JSON (done)

Open:

```text
http://localhost:8080/v3/api-docs
```

Make sure JSON appears.

## Acceptance Criteria

- [ ] Project is created from Spring Initializr.
- [ ] Project uses Java 8.
- [ ] Project uses Spring Boot 2.7.x.
- [ ] Spring Web dependency exists.
- [ ] Validation dependency exists.
- [ ] Swagger dependency exists.
- [ ] Application runs on localhost:8080.
- [ ] `POST /api/v1/customers` returns 201 Created.
- [ ] `GET /api/v1/customers` returns 200 OK.
- [ ] `GET /api/v1/customers/{id}` returns 200 OK for existing customer.
- [ ] `GET /api/v1/customers/{id}` returns 404 for unknown customer.
- [ ] `PUT /api/v1/customers/{id}` updates all fields.
- [ ] `PATCH /api/v1/customers/{id}` updates only provided fields.
- [ ] API contract is written in markdown.
- [ ] Postman testing is completed.
- [ ] Swagger UI can be opened.
- [ ] API can be tested from Swagger UI.
- [ ] `/v3/api-docs` returns OpenAPI JSON.
- [ ] JSON uses snake_case.
- [ ] Java fields use camelCase.

## Optional Challenge

- Add `created_at` and `updated_at` in `CustomerResponse`. (done)
- Add query parameter search by email. ()
- Add simple pagination contract.
- Add OpenAPI description for `ErrorResponse`.
- Add screenshots of Swagger UI result.
