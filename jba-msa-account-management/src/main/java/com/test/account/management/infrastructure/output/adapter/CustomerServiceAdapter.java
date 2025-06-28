package com.test.account.management.infrastructure.output.adapter;

import com.test.account.management.application.output.port.CustomerServicePort;
import com.test.account.management.domain.Customer;
import com.test.account.management.infrastructure.exception.custom.CustomerAccountNotFoundException;
import com.test.account.management.infrastructure.exception.custom.CustomerNotFoundException;
import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerServiceAdapter implements CustomerServicePort {

    WebClient webClient;

    @NonNull
    @Override
    public Mono<Customer> getCustomerById(@NotNull Long customerId) {
        log.info("|--> SP start CustomerService - getCustomerById with customerId [{}]", customerId);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("customerId", customerId);
        return webClient
                .get()
                .uri("/customer/{customerId}", params)
                .retrieve()
                .bodyToMono(Customer.class)
                .doOnError(error->log.error("Error retrieving customer by id, detail: {}", error.getMessage()))
                .switchIfEmpty(Mono.error(new CustomerNotFoundException()))
                .map(customer -> customer)
                .doOnSuccess(postPaymentOrderResponse -> log.info("Customer retrieved successfully"));
    }
}
