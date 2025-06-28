package com.test.account.management.infrastructure.input.adapter.rest.impl;

import com.test.account.management.infrastructure.input.adapter.rest.bs.ReportsApi;
import com.test.account.management.infrastructure.input.adapter.rest.bs.bean.*;
import com.test.account.management.application.input.port.ReportService;
import com.test.account.management.infrastructure.input.adapter.rest.mapper.ReportMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@RestController
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportController implements ReportsApi {

    ReportService reportService;
    ReportMapper reportMapper;

    @Override
    public Mono<ResponseEntity<Flux<AccountReportByCustomerResponse>>> getAccountsReportByCustomer(
            Long customerId,
            LocalDate startDate,
            LocalDate endDate,
            final ServerWebExchange exchange
    ) {
        log.info("|--> ReportController - getAccountsReportByCustomer init with customerId: {}, startDate: {} and endDate: {}", customerId, startDate, endDate);
        return reportService.getAccountsReportByCustomer(customerId,startDate,endDate)
                .map(reportMapper::toAccountReportByCustomer)
                .collectList()
                .map(response -> ResponseEntity.ok().body(Flux.fromIterable(response)))
                .doOnSuccess(response -> log
                        .info("<--| ReportController - getAccountsReportByCustomer finished successfully"))
                .doOnError(error -> log.error(
                        "<--| ReportController - getAccountsReportByCustomer finished with error: {}",
                        error.getMessage()));
    }
}
