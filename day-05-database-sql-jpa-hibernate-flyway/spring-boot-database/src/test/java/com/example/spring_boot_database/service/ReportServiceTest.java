package com.example.spring_boot_database.service;

import com.example.spring_boot_database.dto.LoanApplicationReportProjection;
import com.example.spring_boot_database.entity.Status;
import com.example.spring_boot_database.exception.BadRequestException;
import com.example.spring_boot_database.repository.LoanApplicationRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @Mock
    private LoanApplicationRepository loanApplicationRepository;

    @InjectMocks
    private ReportService reportService;

    private LoanApplicationReportProjection createProjection() {
        return mock(LoanApplicationReportProjection.class);
    }

    private Status getAnyStatus() {
        return Status.values()[0];
    }

    @Nested
    @DisplayName("getLoanApplicationReport")
    class GetLoanApplicationReportTest {

        @Test
        @DisplayName("Should return report successfully when status, startDate, and endDate are provided")
        void testGetLoanApplicationReport_allParamsProvided_success() {
            Status status = getAnyStatus();
            LocalDate startDate = LocalDate.of(2026, 1, 1);
            LocalDate endDate = LocalDate.of(2026, 1, 31);

            LoanApplicationReportProjection projection = createProjection();
            List<LoanApplicationReportProjection> expectedResult = List.of(projection);

            when(loanApplicationRepository.findLoanApplicationReport(
                    eq(status.name()),
                    eq(LocalDateTime.of(2026, 1, 1, 0, 0)),
                    eq(LocalDateTime.of(2026, 2, 1, 0, 0))
            )).thenReturn(expectedResult);

            List<LoanApplicationReportProjection> result =
                    reportService.getLoanApplicationReport(status, startDate, endDate);

            assertNotNull(result);
            assertEquals(1, result.size());
            assertSame(projection, result.get(0));
            assertSame(expectedResult, result);

            verify(loanApplicationRepository).findLoanApplicationReport(
                    status.name(),
                    LocalDateTime.of(2026, 1, 1, 0, 0),
                    LocalDateTime.of(2026, 2, 1, 0, 0)
            );
        }

        @Test
        @DisplayName("Should convert startDate to start of day and endDate to exclusive next day start of day")
        void testGetLoanApplicationReport_dateConversion_success() {
            Status status = getAnyStatus();
            LocalDate startDate = LocalDate.of(2026, 6, 15);
            LocalDate endDate = LocalDate.of(2026, 6, 23);

            when(loanApplicationRepository.findLoanApplicationReport(
                    any(),
                    any(),
                    any()
            )).thenReturn(List.of());

            reportService.getLoanApplicationReport(status, startDate, endDate);

            ArgumentCaptor<String> statusCaptor = ArgumentCaptor.forClass(String.class);
            ArgumentCaptor<LocalDateTime> startDateTimeCaptor =
                    ArgumentCaptor.forClass(LocalDateTime.class);
            ArgumentCaptor<LocalDateTime> endDateTimeCaptor =
                    ArgumentCaptor.forClass(LocalDateTime.class);

            verify(loanApplicationRepository).findLoanApplicationReport(
                    statusCaptor.capture(),
                    startDateTimeCaptor.capture(),
                    endDateTimeCaptor.capture()
            );

            assertEquals(status.name(), statusCaptor.getValue());
            assertEquals(LocalDateTime.of(2026, 6, 15, 0, 0), startDateTimeCaptor.getValue());
            assertEquals(LocalDateTime.of(2026, 6, 24, 0, 0), endDateTimeCaptor.getValue());
        }

        @Test
        @DisplayName("Should pass null status value when status is null")
        void testGetLoanApplicationReport_nullStatus_success() {
            LocalDate startDate = LocalDate.of(2026, 1, 1);
            LocalDate endDate = LocalDate.of(2026, 1, 31);

            LoanApplicationReportProjection projection = createProjection();
            List<LoanApplicationReportProjection> expectedResult = List.of(projection);

            when(loanApplicationRepository.findLoanApplicationReport(
                    isNull(),
                    eq(LocalDateTime.of(2026, 1, 1, 0, 0)),
                    eq(LocalDateTime.of(2026, 2, 1, 0, 0))
            )).thenReturn(expectedResult);

            List<LoanApplicationReportProjection> result =
                    reportService.getLoanApplicationReport(null, startDate, endDate);

            assertNotNull(result);
            assertEquals(1, result.size());
            assertSame(projection, result.get(0));

            verify(loanApplicationRepository).findLoanApplicationReport(
                    null,
                    LocalDateTime.of(2026, 1, 1, 0, 0),
                    LocalDateTime.of(2026, 2, 1, 0, 0)
            );
        }

        @Test
        @DisplayName("Should pass null startDateTime when startDate is null")
        void testGetLoanApplicationReport_nullStartDate_success() {
            Status status = getAnyStatus();
            LocalDate endDate = LocalDate.of(2026, 1, 31);

            LoanApplicationReportProjection projection = createProjection();
            List<LoanApplicationReportProjection> expectedResult = List.of(projection);

            when(loanApplicationRepository.findLoanApplicationReport(
                    eq(status.name()),
                    isNull(),
                    eq(LocalDateTime.of(2026, 2, 1, 0, 0))
            )).thenReturn(expectedResult);

            List<LoanApplicationReportProjection> result =
                    reportService.getLoanApplicationReport(status, null, endDate);

            assertNotNull(result);
            assertEquals(1, result.size());
            assertSame(projection, result.get(0));

            verify(loanApplicationRepository).findLoanApplicationReport(
                    status.name(),
                    null,
                    LocalDateTime.of(2026, 2, 1, 0, 0)
            );
        }

        @Test
        @DisplayName("Should pass null endDateTimeExclusive when endDate is null")
        void testGetLoanApplicationReport_nullEndDate_success() {
            Status status = getAnyStatus();
            LocalDate startDate = LocalDate.of(2026, 1, 1);

            LoanApplicationReportProjection projection = createProjection();
            List<LoanApplicationReportProjection> expectedResult = List.of(projection);

            when(loanApplicationRepository.findLoanApplicationReport(
                    eq(status.name()),
                    eq(LocalDateTime.of(2026, 1, 1, 0, 0)),
                    isNull()
            )).thenReturn(expectedResult);

            List<LoanApplicationReportProjection> result =
                    reportService.getLoanApplicationReport(status, startDate, null);

            assertNotNull(result);
            assertEquals(1, result.size());
            assertSame(projection, result.get(0));

            verify(loanApplicationRepository).findLoanApplicationReport(
                    status.name(),
                    LocalDateTime.of(2026, 1, 1, 0, 0),
                    null
            );
        }

        @Test
        @DisplayName("Should pass all null filters when status, startDate, and endDate are null")
        void testGetLoanApplicationReport_allParamsNull_success() {
            LoanApplicationReportProjection projection = createProjection();
            List<LoanApplicationReportProjection> expectedResult = List.of(projection);

            when(loanApplicationRepository.findLoanApplicationReport(
                    isNull(),
                    isNull(),
                    isNull()
            )).thenReturn(expectedResult);

            List<LoanApplicationReportProjection> result =
                    reportService.getLoanApplicationReport(null, null, null);

            assertNotNull(result);
            assertEquals(1, result.size());
            assertSame(projection, result.get(0));

            verify(loanApplicationRepository).findLoanApplicationReport(
                    null,
                    null,
                    null
            );
        }

        @Test
        @DisplayName("Should allow same startDate and endDate")
        void testGetLoanApplicationReport_sameStartDateAndEndDate_success() {
            Status status = getAnyStatus();
            LocalDate date = LocalDate.of(2026, 1, 1);

            LoanApplicationReportProjection projection = createProjection();
            List<LoanApplicationReportProjection> expectedResult = List.of(projection);

            when(loanApplicationRepository.findLoanApplicationReport(
                    eq(status.name()),
                    eq(LocalDateTime.of(2026, 1, 1, 0, 0)),
                    eq(LocalDateTime.of(2026, 1, 2, 0, 0))
            )).thenReturn(expectedResult);

            List<LoanApplicationReportProjection> result =
                    reportService.getLoanApplicationReport(status, date, date);

            assertNotNull(result);
            assertEquals(1, result.size());
            assertSame(projection, result.get(0));

            verify(loanApplicationRepository).findLoanApplicationReport(
                    status.name(),
                    LocalDateTime.of(2026, 1, 1, 0, 0),
                    LocalDateTime.of(2026, 1, 2, 0, 0)
            );
        }

        @Test
        @DisplayName("Should return empty list when repository returns empty list")
        void testGetLoanApplicationReport_repositoryReturnsEmptyList_success() {
            Status status = getAnyStatus();
            LocalDate startDate = LocalDate.of(2026, 1, 1);
            LocalDate endDate = LocalDate.of(2026, 1, 31);

            when(loanApplicationRepository.findLoanApplicationReport(
                    eq(status.name()),
                    eq(LocalDateTime.of(2026, 1, 1, 0, 0)),
                    eq(LocalDateTime.of(2026, 2, 1, 0, 0))
            )).thenReturn(List.of());

            List<LoanApplicationReportProjection> result =
                    reportService.getLoanApplicationReport(status, startDate, endDate);

            assertNotNull(result);
            assertTrue(result.isEmpty());

            verify(loanApplicationRepository).findLoanApplicationReport(
                    status.name(),
                    LocalDateTime.of(2026, 1, 1, 0, 0),
                    LocalDateTime.of(2026, 2, 1, 0, 0)
            );
        }

        @Test
        @DisplayName("Should throw BadRequestException when startDate is after endDate")
        void testGetLoanApplicationReport_startDateAfterEndDate_throwBadRequestException() {
            Status status = getAnyStatus();
            LocalDate startDate = LocalDate.of(2026, 2, 1);
            LocalDate endDate = LocalDate.of(2026, 1, 31);

            BadRequestException exception = assertThrows(
                    BadRequestException.class,
                    () -> reportService.getLoanApplicationReport(status, startDate, endDate)
            );

            assertEquals("startDate cannot be after endDate", exception.getMessage());

            verifyNoInteractions(loanApplicationRepository);
        }

        @Test
        @DisplayName("Should not validate date range when startDate is null")
        void testGetLoanApplicationReport_startDateNullDoesNotThrow() {
            Status status = getAnyStatus();
            LocalDate endDate = LocalDate.of(2026, 1, 31);

            when(loanApplicationRepository.findLoanApplicationReport(
                    eq(status.name()),
                    isNull(),
                    eq(LocalDateTime.of(2026, 2, 1, 0, 0))
            )).thenReturn(List.of());

            assertDoesNotThrow(
                    () -> reportService.getLoanApplicationReport(status, null, endDate)
            );

            verify(loanApplicationRepository).findLoanApplicationReport(
                    status.name(),
                    null,
                    LocalDateTime.of(2026, 2, 1, 0, 0)
            );
        }

        @Test
        @DisplayName("Should not validate date range when endDate is null")
        void testGetLoanApplicationReport_endDateNullDoesNotThrow() {
            Status status = getAnyStatus();
            LocalDate startDate = LocalDate.of(2026, 2, 1);

            when(loanApplicationRepository.findLoanApplicationReport(
                    eq(status.name()),
                    eq(LocalDateTime.of(2026, 2, 1, 0, 0)),
                    isNull()
            )).thenReturn(List.of());

            assertDoesNotThrow(
                    () -> reportService.getLoanApplicationReport(status, startDate, null)
            );

            verify(loanApplicationRepository).findLoanApplicationReport(
                    status.name(),
                    LocalDateTime.of(2026, 2, 1, 0, 0),
                    null
            );
        }

        @Test
        @DisplayName("Should propagate repository exception")
        void testGetLoanApplicationReport_repositoryThrowsException() {
            Status status = getAnyStatus();
            LocalDate startDate = LocalDate.of(2026, 1, 1);
            LocalDate endDate = LocalDate.of(2026, 1, 31);

            when(loanApplicationRepository.findLoanApplicationReport(
                    eq(status.name()),
                    eq(LocalDateTime.of(2026, 1, 1, 0, 0)),
                    eq(LocalDateTime.of(2026, 2, 1, 0, 0))
            )).thenThrow(new RuntimeException("Database error"));

            RuntimeException exception = assertThrows(
                    RuntimeException.class,
                    () -> reportService.getLoanApplicationReport(status, startDate, endDate)
            );

            assertEquals("Database error", exception.getMessage());

            verify(loanApplicationRepository).findLoanApplicationReport(
                    status.name(),
                    LocalDateTime.of(2026, 1, 1, 0, 0),
                    LocalDateTime.of(2026, 2, 1, 0, 0)
            );
        }
    }
}