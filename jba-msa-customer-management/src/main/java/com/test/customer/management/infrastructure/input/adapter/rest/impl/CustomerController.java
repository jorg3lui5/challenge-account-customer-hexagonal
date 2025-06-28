package com.test.customer.management.infrastructure.input.adapter.rest.impl;

import com.test.customer.management.application.input.port.CustomerService;
import com.test.customer.management.infrastructure.input.adapter.rest.bs.CustomersApi;
import com.test.customer.management.infrastructure.input.adapter.rest.bs.bean.GetCustomerResponse;
import com.test.customer.management.infrastructure.input.adapter.rest.bs.bean.PatchCustomerRequest;
import com.test.customer.management.infrastructure.input.adapter.rest.bs.bean.PostCustomerRequest;
import com.test.customer.management.infrastructure.input.adapter.rest.bs.bean.PutCustomerRequest;
import com.test.customer.management.infrastructure.input.adapter.rest.mapper.CustomerMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerController implements CustomersApi {

    CustomerService customerService;
    CustomerMapper customerMapper;

    @Override
    public Mono<ResponseEntity<GetCustomerResponse>> getCustomerByFilter(
            String identification,
            final ServerWebExchange exchange
    ) {
        log.info("|--> CustomerController - getCustomerByFilter init");
        log.debug(
                "|--> CustomerController - getCustomerByFilter with identification: {}",identification);
        return customerService.getCustomerByFilter(identification)
                .map(customerMapper::toCustomerResponse)
                .map(response -> ResponseEntity.ok().body(response))
                .doOnSuccess(response -> log
                        .info("<--| CustomerController - getCustomerByFilter finished successfully"))
                .doOnError(error -> log.error(
                        "<--| CustomerController - getCustomerByFilter finished with error: {}",
                        error.getMessage()));
    }

    @Override
    public Mono<ResponseEntity<GetCustomerResponse>> getCustomerById(
            Long customerId,
            final ServerWebExchange exchange
    ) {
        log.info("|--> CustomerController - getCustomerById init");
        log.debug(
                "|--> CustomerController - getCustomerById with customerId: {}",customerId);
        return customerService.getCustomerById(customerId)
                .map(customerMapper::toCustomerResponse)
                .map(response -> ResponseEntity.ok().body(response))
                .doOnSuccess(response -> log
                        .info("<--| CustomerController - getCustomerById finished successfully"))
                .doOnError(error -> log.error(
                        "<--| CustomerController - getCustomerById finished with error: {}",
                        error.getMessage()));
    }

    @Override
    public Mono<ResponseEntity<Flux<GetCustomerResponse>>> getCustomers(
            final ServerWebExchange exchange
    ) {
        log.info("|--> CustomerController - getCustomers init");
        return customerService.getCustomers()
                .map(customerMapper::toCustomerResponse)
                .collectList()
                .map(customerList -> ResponseEntity.ok().body(Flux.fromIterable(customerList)))
                .doOnSuccess(response -> log
                        .info("<--| CustomerController - getCustomers finished successfully"))
                .doOnError(error -> log.error(
                        "<--| CustomerController - getCustomers finished with error: {}",
                        error.getMessage()));
    }

    @Override
    public Mono<ResponseEntity<Void>> patchCustomer(
            Mono<PatchCustomerRequest> patchCustomerRequest,
            final ServerWebExchange exchange
    ) {
        log.info("|--> CustomerController - patchCustomer init");
        return patchCustomerRequest.flatMap(request -> {
                    log.debug(
                            "|--> CustomerController - patchCustomer Request in Json: [request:{}]",
                            request);
                    return customerService.patchCustomer(customerMapper.toCustomer(request));
                })
                .doOnSuccess(response -> log.info(
                        "<--| CustomerController - patchCustomer finished successfully"))
                .doOnError(error -> log.error(
                        "<--| CustomerController - patchCustomer finished with error: {}",
                        error.getMessage()))
                .map(response -> ResponseEntity.ok().build());
    }

    @Override
    public Mono<ResponseEntity<Void>> postCustomer(
            Mono<PostCustomerRequest> postCustomerRequest,
            final ServerWebExchange exchange
    ) {
        log.info("|--> CustomerController - postCustomer init");
        return postCustomerRequest.flatMap(request -> {
                    log.debug(
                            "|--> CustomerController - postCustomer Request in Json: [request:{}]",
                            request);
                    return customerService.postCustomer(customerMapper.toCustomer(request));
                })
                .doOnSuccess(response -> log.info(
                        "<--| CustomerController - postCustomer finished successfully"))
                .doOnError(error -> log.error(
                        "<--| CustomerController - postCustomer finished with error: {}",
                        error.getMessage()))
                .map(response -> ResponseEntity.ok().build());
    }

    @Override
    public Mono<ResponseEntity<Void>> putCustomer(
            Mono<PutCustomerRequest> putCustomerRequest,
            final ServerWebExchange exchange
    ) {
        log.info("|--> CustomerController - putCustomer init");
        return putCustomerRequest.flatMap(request -> {
                    log.debug(
                            "|--> CustomerController - putCustomer Request in Json: [request:{}]",
                            request);
                    return customerService.putCustomer(customerMapper.toCustomer(request));
                })
                .doOnSuccess(response -> log.info(
                        "<--| CustomerController - putCustomer finished successfully"))
                .doOnError(error -> log.error(
                        "<--| CustomerController - putCustomer finished with error: {}",
                        error.getMessage()))
                .map(response -> ResponseEntity.ok().build());
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteCustomer(
            Long customerId,
            final ServerWebExchange exchange
    ) {
        log.info("|--> CustomerController - deleteCustomer init");
        log.debug(
                "|--> CustomerController - deleteCustomer with customerId: {}",customerId);
        return customerService.deleteCustomer(customerId)
                .doOnSuccess(response -> log
                        .info("<--| CustomerController - deleteCustomer finished successfully"))
                .doOnError(error -> log.error(
                        "<--| CustomerController - deleteCustomer finished with error: {}",
                        error.getMessage()))
                .map(response -> ResponseEntity.ok().build());
    }
}
