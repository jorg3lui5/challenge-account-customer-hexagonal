package com.test.customer.management.application.input.port;

import com.test.customer.management.domain.Customer;
import io.micrometer.common.lang.NonNull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Validated
public interface CustomerService {

    @NonNull
    Mono<Customer> getCustomerByFilter(
            @NotNull @NotBlank String identification
    );

    @NonNull
    Mono<Customer> getCustomerById(
            @NotNull Long customerId
    );

    @NonNull
    Flux<Customer> getCustomers(
    );

    Mono<Void> patchCustomer(
            @NotNull @Valid Customer customer
    );

    Mono<Void> postCustomer(
            @NotNull @Valid Customer customer
    );

    Mono<Void> putCustomer(
            @NotNull @Valid Customer customer
    );

    Mono<Void> deleteCustomer(
            @NotNull Long customerId
    );
}
