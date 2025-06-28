package com.test.account.management.infrastructure.output.adapter;

import com.test.account.management.application.output.port.ReportServicePort;
import com.test.account.management.domain.AccountMovementReport;
import io.micrometer.common.lang.NonNull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@Service
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportServiceAdapter implements ReportServicePort {

    @NonNull
    @Override
    public Flux<AccountMovementReport> getAccountsReportByCustomer(
            @NotNull Long customerId,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @NotNull @Valid LocalDate startDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @NotNull @Valid LocalDate endDate
    ) {
        return Flux.empty();
    }

}
