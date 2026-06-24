# Exercise - Add Unit Test, Peer Review Checklist & Structured Logging

## Objective

Peserta mampu meningkatkan kualitas Loan Application API dengan menambahkan unit test, peer code review checklist, structured logging, correlation ID, dan PII-safe logging.

## Case

Pada exercise ini peserta tidak membuat API baru. Peserta meningkatkan kualitas API yang sudah ada.

## Part 1 - Unit Testing

### Technical Requirements

- Gunakan Java 17, Spring Boot 3.x, JUnit 5, dan Mockito.
- Fokus test pada service layer dengan pola Given-When-Then dan nama test yang jelas.
- Test harus mencakup happy path dan negative path.
- Jangan test getter/setter sederhana dan jangan memakai database asli untuk unit test.
- Mock repository atau dependency yang diperlukan.

### Required Test Classes

```text
src/test/java/com/example/training/service/AuthServiceTest.java
src/test/java/com/example/training/service/CustomerServiceTest.java
src/test/java/com/example/training/service/LoanApplicationServiceTest.java
```

### AuthService Test Cases

1. `should_login_successfully_when_username_and_password_are_valid`
2. `should_throw_unauthorized_when_password_is_invalid`
3. `should_return_current_user_when_token_is_valid`
4. `should_throw_unauthorized_when_token_is_missing`
5. `should_throw_unauthorized_when_token_is_invalid`

### CustomerService Test Cases

1. `should_create_customer_successfully`
2. `should_get_customer_by_id_successfully`
3. `should_throw_not_found_when_customer_does_not_exist`
4. `should_return_all_customers`
5. `should_not_allow_approver_to_create_customer`

### LoanApplicationService Test Cases

1. `should_create_loan_application_successfully`
2. `should_throw_not_found_when_customer_does_not_exist`
3. `should_get_loan_application_by_id_successfully`
4. `should_throw_not_found_when_loan_application_does_not_exist`
5. `should_approve_loan_when_user_is_approver`
6. `should_reject_loan_when_user_is_approver`
7. `should_throw_forbidden_when_staff_tries_to_approve_loan`
8. `should_throw_forbidden_when_staff_tries_to_reject_loan`

### Example Unit Test Structure

```java
@Test
void should_throw_forbidden_when_staff_tries_to_approve_loan() {
    // given
    Long loanId = 1L;
    AuthContext staffContext = new AuthContext("staff", "STAFF");
    LoanApplication loan = new LoanApplication();
    loan.setId(loanId);
    loan.setStatus("SUBMITTED");

    when(loanApplicationRepository.findById(loanId))
            .thenReturn(Optional.of(loan));

    // when & then
    ForbiddenException exception = assertThrows(ForbiddenException.class,
            () -> loanApplicationService.approveLoan(loanId, staffContext));

    assertEquals("FORBIDDEN", exception.getCode());
    verify(loanApplicationRepository, never()).save(any());
}
```

`given` menyiapkan data, `when` menjalankan action, dan `then` memverifikasi hasil. `never()` memastikan `STAFF` tidak menyimpan perubahan approval.

## Tasks

1. Tambahkan unit test untuk semua service dan coverage dan unit test di atas 92 persen.
<!-- 2. Gunakan Given-When-Then dan Mockito untuk happy path, negative path, behavior `401`/`403`. -->


## Acceptance Criteria

- [ ] semua di folder service memiliki unit test dengan JUnit 5, Mockito, dan Given-When-Then.
- [ ] Happy path, negative path,semuaa service.

