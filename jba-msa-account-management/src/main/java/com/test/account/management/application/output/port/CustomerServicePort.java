package com.test.account.management.application.output.port;

import com.test.account.management.domain.Customer;
import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotNull;
import reactor.core.publisher.Mono;

public interface CustomerServicePort {
    @NonNull
    Mono<Customer> getCustomerById(@NotNull Long customerId);
}
