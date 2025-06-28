package com.test.customer.management.application.service;

import com.test.customer.management.application.input.port.CustomerService;
import com.test.customer.management.application.output.port.CustomerRepositoryPort;
import com.test.customer.management.domain.Customer;
import com.test.customer.management.infrastructure.exception.custom.*;
import io.micrometer.common.lang.NonNull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerServiceImp implements CustomerService {

    CustomerRepositoryPort customerRepositoryPort;

    @Override
    @NonNull
    public Mono<Customer> getCustomerByFilter(
            @NotNull @NotBlank String identification
    ) {
        log.info("Start customer recovery with identification: {}",identification);
        return customerRepositoryPort.getCustomerByFilter(identification)
                .doOnError(error->log.error("Error retrieving customer by identification, detail: {}", error.getMessage()))
                .onErrorMap(error->new GetRegisterException())
                .switchIfEmpty(Mono.error(new RegisterNotFoundException()))
                .doOnSuccess(success->log.info("Customer retrieved by identification successfully"));
    }

    @Override
    @NonNull
    public Mono<Customer> getCustomerById(
            @NotNull Long customerId
    ) {
        log.info("Start customer recovery with id: {}",customerId);
        return customerRepositoryPort.getCustomerById(customerId)
                .doOnError(error->log.error("Error retrieving customer by ID, detail: {}", error.getMessage()))
                .onErrorResume(error->Mono.error(new GetRegisterException()))
                .switchIfEmpty(Mono.error(new RegisterNotFoundException()))
                .doOnSuccess(success->log.info("Customer retrieved by Id successfully"));
    }

    @Override
    @NonNull
    public Flux<Customer> getCustomers() {
        log.info("Start retrieve customers");
        return customerRepositoryPort.getCustomers()
                .doOnError(error->log.error("Error retrieving customers, detail: {}", error.getMessage()))
                .onErrorMap(error->new GetRegistersException())
                .doOnComplete(()->log.info("Customers retrieved successfully"));
    }

    @Override
    public Mono<Void> postCustomer(
            @NotNull @Valid Customer customer
    ) {
        log.info("Starting save customer");
        log.info("Request in post: [{}]", customer);
        return customerRepositoryPort.postCustomer(customer)
                .doOnError(error->log.error("Error saving customer, detail: {}", error.getMessage()))
                .onErrorMap(error->new SaveException())
                .doOnSuccess(success->log.info("Customer save successfully"))
                .then();
    }

    @Override
    public Mono<Void> putCustomer(
            @NotNull @Valid Customer customer
    ) {
        log.info("Starting update/put customer");
        log.info("Request in put: [{}]", customer);
        return customerRepositoryPort.putCustomer(customer)
                .doOnError(error->log.error("Error updating/put customer, detail: {}", error.getMessage()))
                .onErrorMap(error->new UpdateException())
                .doOnSuccess(success->log.info("Customer update/put successfully"))
                .then();
    }

    @Override
    public Mono<Void> patchCustomer(
            @NotNull @Valid Customer customer
    ) {
        log.info("Starting update/patch customer");
        log.info("Request in patch: [{}]", customer);
        return customerRepositoryPort.patchCustomer(customer)
                .doOnError(error->log.error("Error updating/patch customer, detail: {}", error.getMessage()))
                .onErrorMap(error->new UpdateException())
                .doOnSuccess(success->log.info("Customer update/patch successfully"))
                .then();

    }

    @Override
    public Mono<Void> deleteCustomer(
            @NotNull Long customerId
    ) {
        log.info("Starting delete customer with id: {}", customerId);
        return customerRepositoryPort.deleteCustomer(customerId)
                .doOnError(error->log.error("Error deleting customer, detail: {}", error.getMessage()))
                .onErrorMap(error->new DeleteException())
                .doOnSuccess(success->log.info("Customer delete successfully"))
                .then();
    }

}
