package com.test.account.management.application.input.port;

import com.test.account.management.domain.AccountMovementReport;
import io.micrometer.common.lang.NonNull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@Validated
public interface ReportService {

    @NonNull
    Flux<AccountMovementReport> getAccountsReportByCustomer(
            @NotNull Long customerId,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @NotNull @Valid LocalDate startDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @NotNull @Valid LocalDate endDate
    );
}
