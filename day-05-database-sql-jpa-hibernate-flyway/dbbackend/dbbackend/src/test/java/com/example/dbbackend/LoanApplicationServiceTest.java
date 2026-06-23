package com.example.dbbackend;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import com.example.dbbackend.common.exception.CustomerNotFoundException;
import com.example.dbbackend.common.exception.LoanApplicationNotFoundException;
import com.example.dbbackend.customer.entity.CustomerEntity;
import com.example.dbbackend.customer.repository.CustomerRepository;
import com.example.dbbackend.loanapplication.dto.CreateLoanApplicationRequest;
import com.example.dbbackend.loanapplication.entity.LoanApplicationEntity;
import com.example.dbbackend.loanapplication.entity.LoanApplicationStatus;
import com.example.dbbackend.loanapplication.repository.LoanApplicationRepository;
import com.example.dbbackend.loanapplication.service.LoanApplicationService;
import com.example.dbbackend.repaymentschedule.entity.RepaymentScheduleEntity;
import com.example.dbbackend.repaymentschedule.repository.RepaymentScheduleRepository;

@ExtendWith(MockitoExtension.class)
class LoanApplicationServiceTest {

        @Mock
        private LoanApplicationRepository loanRepository;

        @Mock
        private CustomerRepository customerRepository;

        @Mock
        private RepaymentScheduleRepository scheduleRepository;

        @InjectMocks
        private LoanApplicationService service;

        // SKENARIO 1: CREATE LOAN (SUKSES & GAGAL)

        @Test
        void shouldCreateLoanSuccessfully() {
                // GIVEN
                CreateLoanApplicationRequest req = new CreateLoanApplicationRequest();
                req.setCustomerId(1L);
                req.setLoanAmount(BigDecimal.valueOf(10000000));
                req.setTenorMonth(12);

                CustomerEntity dataNasabahPalsu = new CustomerEntity();
                dataNasabahPalsu.setId(1L);

                when(customerRepository.findById(1L))
                                .thenReturn(Optional.of(dataNasabahPalsu));

                when(loanRepository.save(any(LoanApplicationEntity.class))).thenAnswer(i -> {
                        LoanApplicationEntity loan = i.getArgument(0);
                        loan.setId(1L);
                        loan.setCustomer(dataNasabahPalsu);
                        return loan;
                });

                // WHEN
                var result = service.createLoan(req);
                // THEN
                assertNotNull(result);
                assertEquals(1L, result.getId());
        }

        @Test
        void shouldFailWhenCustomerNotFound() {
                // GIVEN
                when(customerRepository.findById(1L))
                                .thenReturn(Optional.empty());

                CreateLoanApplicationRequest req = new CreateLoanApplicationRequest();
                req.setCustomerId(1L);

                // WHEN & THEN
                assertThrows(CustomerNotFoundException.class,
                                () -> service.createLoan(req));
        }

        // SKENARIO 2: UPDATE STATUS (ALUR VALID)

        @Test
        void shouldUpdateLoanStatusToApproved() {
                // GIVEN
                CustomerEntity dataNasabahPalsu = new CustomerEntity();
                dataNasabahPalsu.setId(1L);

                LoanApplicationEntity dataPinjamanPalsu = new LoanApplicationEntity();
                dataPinjamanPalsu.setId(1L);
                dataPinjamanPalsu.setStatus("SUBMITTED");

                dataPinjamanPalsu.setCustomer(dataNasabahPalsu);

                when(loanRepository.findById(1L))
                                .thenReturn(Optional.of(dataPinjamanPalsu));

                when(loanRepository.save(any(LoanApplicationEntity.class)))
                                .thenAnswer(i -> i.getArgument(0));
                // WHEN
                var hasilAkhir = service.updateStatus(1L, LoanApplicationStatus.APPROVED);
                // THEN
                assertEquals(LoanApplicationStatus.APPROVED.name(), hasilAkhir.getStatus().name());
        }

        // SKENARIO 3: UPDATE STATUS (ALUR ERROR/GAGAL)
        @Test
        void shouldFailUpdateStatusWhenLoanNotFound() {
                // GIVEN
                when(loanRepository.findById(99L))
                                .thenReturn(Optional.empty());
                // WHEN & THEN
                assertThrows(LoanApplicationNotFoundException.class,
                                () -> service.updateStatus(99L, LoanApplicationStatus.APPROVED));
        }

        @Test
        void shouldFailInvalidTransition() {
                // GIVEN
                LoanApplicationEntity loan = new LoanApplicationEntity();
                loan.setId(1L);
                loan.setStatus("SUBMITTED");

                // WHEN & THEN
                when(loanRepository.findById(1L))
                                .thenReturn(Optional.of(loan));

                assertThrows(IllegalArgumentException.class,
                                () -> service.updateStatus(1L, LoanApplicationStatus.CLOSED));
        }

        // SKENARIO TAMBAHAN: GET LOAN BY ID (COVERAGE)

        @Test
        void shouldGetLoanByIdSuccessfully() {
                // GIVEN
                CustomerEntity dataNasabahPalsu = new CustomerEntity();
                dataNasabahPalsu.setId(1L);

                LoanApplicationEntity dataPinjamanPalsu = new LoanApplicationEntity();
                dataPinjamanPalsu.setId(1L);
                dataPinjamanPalsu.setStatus("SUBMITTED");

                dataPinjamanPalsu.setCustomer(dataNasabahPalsu);

                when(loanRepository.findByIdWithCustomer(1L))
                                .thenReturn(Optional.of(dataPinjamanPalsu));
                // WHEN
                var hasilAkhir = service.getLoanById(1L);
                // THEN
                assertNotNull(hasilAkhir);
                assertEquals(1L, hasilAkhir.getId());
        }

        @Test
        void shouldFailGetLoanByIdWhenNotFound() {
                // GIVEN
                when(loanRepository.findByIdWithCustomer(99L))
                                .thenReturn(Optional.empty());
                // WHEN & THEN
                assertThrows(LoanApplicationNotFoundException.class,
                                () -> service.getLoanById(99L));
        }

        // SKENARIO TAMBAHAN: GET ALL & GET BY STATUS

        @Test
        void shouldGetAllLoanApplications() {
                // GIVEN
                CustomerEntity dataNasabahPalsu = new CustomerEntity();
                dataNasabahPalsu.setId(1L);

                LoanApplicationEntity dataPinjamanPalsu = new LoanApplicationEntity();
                dataPinjamanPalsu.setId(1L);
                dataPinjamanPalsu.setStatus("SUBMITTED");
                dataPinjamanPalsu.setCustomer(dataNasabahPalsu);

                java.util.List<LoanApplicationEntity> daftarPinjamanPalsu = java.util.List.of(dataPinjamanPalsu);

                when(loanRepository.findAll()).thenReturn(daftarPinjamanPalsu);
                // WHEN
                var hasilAkhir = service.getAll();
                // THEN
                assertNotNull(hasilAkhir);
                assertEquals(1, hasilAkhir.size());
                assertEquals(1L, hasilAkhir.get(0).getId());
        }

        @Test
        void shouldGetLoanApplicationsByStatus() {
                // GIVEN
                CustomerEntity dataNasabahPalsu = new CustomerEntity();
                dataNasabahPalsu.setId(1L);

                LoanApplicationEntity dataPinjamanPalsu = new LoanApplicationEntity();
                dataPinjamanPalsu.setId(1L);
                dataPinjamanPalsu.setStatus("APPROVED");
                dataPinjamanPalsu.setCustomer(dataNasabahPalsu);

                java.util.List<LoanApplicationEntity> daftarPinjamanPalsu = java.util.List.of(dataPinjamanPalsu);

                when(loanRepository.findByStatus("APPROVED")).thenReturn(daftarPinjamanPalsu);
                // WHEN
                var hasilAkhir = service.getByStatus("APPROVED");
                // THEN
                assertNotNull(hasilAkhir);
                assertEquals(1, hasilAkhir.size());
                assertEquals("APPROVED", hasilAkhir.get(0).getStatus().name());
        }

        @Test
        void shouldGenerateRepaymentScheduleWhenStatusDisbursed() {
                // GIVEN
                CustomerEntity dataNasabahPalsu = new CustomerEntity();
                dataNasabahPalsu.setId(1L);

                LoanApplicationEntity dataPinjamanPalsu = new LoanApplicationEntity();
                dataPinjamanPalsu.setId(1L);
                dataPinjamanPalsu.setStatus(LoanApplicationStatus.APPROVED.name());
                dataPinjamanPalsu.setCustomer(dataNasabahPalsu);
                dataPinjamanPalsu.setTenorMonth(12);
                dataPinjamanPalsu.setLoanAmount(BigDecimal.valueOf(12000000));

                when(loanRepository.findById(1L))
                                .thenReturn(Optional.of(dataPinjamanPalsu));

                when(loanRepository.save(any(LoanApplicationEntity.class)))
                                .thenAnswer(i -> i.getArgument(0));

                when(scheduleRepository.save(any(RepaymentScheduleEntity.class)))
                                .thenAnswer(i -> i.getArgument(0));
                // WHEN
                var hasilAkhir = service.updateStatus(1L, LoanApplicationStatus.DISBURSED);
                // THEN
                assertNotNull(hasilAkhir);
                assertEquals(LoanApplicationStatus.DISBURSED.name(), hasilAkhir.getStatus().name());
        }

        // SKENARIO UNTUK MENGOVER VALIDATE STATUS TRANSITION

        @Test
        void shouldFailWhenTransitionFromApprovedIsNotDisbursed() {
                // Kita buat status awalnya APPROVED, tapi sengaja mau kita ubah langsung ke
                // CLOSED (Harusnya ERROR)
                // GIVEN
                LoanApplicationEntity dataPinjamanPalsu = new LoanApplicationEntity();
                dataPinjamanPalsu.setId(1L);
                dataPinjamanPalsu.setStatus(LoanApplicationStatus.APPROVED.name());

                when(loanRepository.findById(1L)).thenReturn(Optional.of(dataPinjamanPalsu));
                // WHEN & THEN
                assertThrows(IllegalArgumentException.class,
                                () -> service.updateStatus(1L, LoanApplicationStatus.CLOSED));
        }

        @Test
        void shouldFailWhenTransitionFromDisbursedIsNotClosed() {
                // Kita buat status awalnya DISBURSED, tapi sengaja mau kita ubah balik ke
                // APPROVED (Harusnya ERROR)
                LoanApplicationEntity dataPinjamanPalsu = new LoanApplicationEntity();
                dataPinjamanPalsu.setId(1L);
                dataPinjamanPalsu.setStatus(LoanApplicationStatus.DISBURSED.name());

                when(loanRepository.findById(1L)).thenReturn(Optional.of(dataPinjamanPalsu));

                assertThrows(IllegalArgumentException.class,
                                () -> service.updateStatus(1L, LoanApplicationStatus.APPROVED));
        }

        @Test
        void shouldFailWhenTryingToChangeFinalStatus() {
                // Kita buat status awalnya REJECTED (Status Akhir), tidak boleh diubah ke
                // apapun lagi!
                // GIVEN
                LoanApplicationEntity dataPinjamanPalsu = new LoanApplicationEntity();
                dataPinjamanPalsu.setId(1L);
                dataPinjamanPalsu.setStatus(LoanApplicationStatus.REJECTED.name());

                when(loanRepository.findById(1L)).thenReturn(Optional.of(dataPinjamanPalsu));
                // WHEN & THEN
                assertThrows(IllegalArgumentException.class,
                                () -> service.updateStatus(1L, LoanApplicationStatus.APPROVED));
        }

        @Test
        void shouldUpdateStatusFromSubmittedToRejectedSuccessfully() {
                // Status awal SUBMITTED, mau kita ubah ke REJECTED (Ini transisi yang
                // sah/valid)
                // GIVEN
                CustomerEntity dataNasabahPalsu = new CustomerEntity();
                dataNasabahPalsu.setId(1L);

                LoanApplicationEntity dataPinjamanPalsu = new LoanApplicationEntity();
                dataPinjamanPalsu.setId(1L);
                dataPinjamanPalsu.setStatus(LoanApplicationStatus.SUBMITTED.name());
                dataPinjamanPalsu.setCustomer(dataNasabahPalsu);

                when(loanRepository.findById(1L)).thenReturn(Optional.of(dataPinjamanPalsu));
                when(loanRepository.save(any(LoanApplicationEntity.class))).thenAnswer(i -> i.getArgument(0));
                // WHEN
                var hasilAkhir = service.updateStatus(1L, LoanApplicationStatus.REJECTED);
                // THEN
                assertNotNull(hasilAkhir);
                assertEquals(LoanApplicationStatus.REJECTED.name(), hasilAkhir.getStatus().name());
        }

        @Test
        void shouldUpdateStatusFromDisbursedToClosedSuccessfully() {
                // Status awal DISBURSED, mau kita ubah ke CLOSED (Ini transisi yang sah/valid)
                // GIVEN
                CustomerEntity dataNasabahPalsu = new CustomerEntity();
                dataNasabahPalsu.setId(1L);

                LoanApplicationEntity dataPinjamanPalsu = new LoanApplicationEntity();
                dataPinjamanPalsu.setId(1L);
                dataPinjamanPalsu.setStatus(LoanApplicationStatus.DISBURSED.name());
                dataPinjamanPalsu.setCustomer(dataNasabahPalsu);

                when(loanRepository.findById(1L)).thenReturn(Optional.of(dataPinjamanPalsu));
                when(loanRepository.save(any(LoanApplicationEntity.class))).thenAnswer(i -> i.getArgument(0));
                // WHEN
                var hasilAkhir = service.updateStatus(1L, LoanApplicationStatus.CLOSED);
                // THEN
                assertNotNull(hasilAkhir);
                assertEquals(LoanApplicationStatus.CLOSED.name(), hasilAkhir.getStatus().name());
        }

}
