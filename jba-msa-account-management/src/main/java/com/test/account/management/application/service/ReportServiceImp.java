package com.test.account.management.application.service;

import com.test.account.management.application.input.port.AccountService;
import com.test.account.management.application.input.port.MovementService;
import com.test.account.management.application.input.port.ReportService;
import com.test.account.management.application.output.port.CustomerServicePort;
import com.test.account.management.application.output.port.ReportServicePort;
import com.test.account.management.domain.AccountMovementReport;
import com.test.account.management.infrastructure.exception.custom.CustomerWithoutAccountsException;
import com.test.account.management.infrastructure.input.adapter.rest.mapper.ReportMapper;
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
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportServiceImp implements ReportService {

    ReportServicePort reportServicePort;
    CustomerServicePort customerServicePort;
    AccountService accountService;
    MovementService movementService;
    ReportMapper reportMapper;

    @NonNull
    @Override
    public Flux<AccountMovementReport> getAccountsReportByCustomer(
            @NotNull Long customerId,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @NotNull @Valid LocalDate startDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @NotNull @Valid LocalDate endDate
    ) {
        log.info("Starting to obtain account report for customer with customerId: {}, start date: {} and end date: {}", customerId, startDate, endDate);
        return customerServicePort.getCustomerById(customerId)
                .doOnError(error -> log.error("Error retrieving the customer of the account, detail: {}", error.getMessage()))
                .flatMapMany(customer ->
                    accountService.getAccountsByCustomerId(customerId)
                        .doOnError(error -> log.error("Error retrieving the list of accounts for the customer, detail: {}", error.getMessage()))
                        .switchIfEmpty(Mono.error(new CustomerWithoutAccountsException()))
                        .flatMap(account ->
                            movementService.getMovementsByAccountId(account.getAccountId())
                            .doOnError(error -> log.error("Error retrieving the movements of the account, detail: {}", error.getMessage()))
                            .map(movement ->
                                reportMapper.toAccountMovementReport(movement, account, customer)
                            )
                        )
                );
    }

}
