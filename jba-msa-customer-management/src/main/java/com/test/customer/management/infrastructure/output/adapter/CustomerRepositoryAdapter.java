package com.test.customer.management.infrastructure.output.adapter;

import com.test.customer.management.application.output.port.CustomerRepositoryPort;
import com.test.customer.management.domain.Customer;
import com.test.customer.management.infrastructure.exception.custom.*;
import com.test.customer.management.infrastructure.output.adapter.repository.CustomerRepository;
import com.test.customer.management.infrastructure.output.adapter.repository.PersonRepository;
import com.test.customer.management.infrastructure.output.adapter.repository.entity.CustomerEntity;
import com.test.customer.management.infrastructure.output.adapter.repository.mapper.CustomerRepositoryMapper;
import com.test.customer.management.infrastructure.output.adapter.repository.mapper.PersonRepositoryMapper;
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
public class CustomerRepositoryAdapter implements CustomerRepositoryPort {

    CustomerRepository customerRepository;
    PersonRepository personRepository;
    CustomerRepositoryMapper customerRepositoryMapper;
    PersonRepositoryMapper personRepositoryMapper;

    @Override
    @NonNull
    public Mono<Customer> getCustomerById(
            @NotNull Long customerId
    ) {
        log.info("Starts getting the customer with id: {}",customerId);
        return customerRepository.findByCustomerId(customerId)
                .switchIfEmpty(Mono.error(new RegisterNotFoundException()))
                .doOnError(error->log.error("Error retrieving customer by ID, detail: {}", error.getMessage()))
                .onErrorMap(error->new GetRegisterException())
                .map(customerRepositoryMapper::entityDataToCustomer)
                .doOnSuccess(success->log.info("Cliente recuperado correctamente."));
    }

    @Override
    @NonNull
    public Mono<Customer> getCustomerByFilter(
            @NotNull @NotBlank String identification
    ) {
        log.info("Starts getting the customer with identification: {}",identification);
        return customerRepository.findByIdentification(identification)
                .switchIfEmpty(Mono.error(new RegisterNotFoundException()))
                .doOnError(error->log.error("Error retrieving customer by identification, detail: {}", error.getMessage()))
                .onErrorResume(error->Mono.error(new GetRegisterException()))
                .map(entity->customerRepositoryMapper.entityDataToCustomer(entity))
                .doOnSuccess(success->log.info("Customer successfully recovered"));
    }

    @Override
    @NonNull
    public Flux<Customer> getCustomers() {
        log.info("Start obtaining the Customer list");
        return customerRepository.findAllCustomers()
                .doOnError(error->log.error("Error retrieving customers, details: {}", error.getMessage()))
                .onErrorMap(error->new GetRegistersException())
                .map(customerRepositoryMapper::entityDataToCustomer);
    }

    @Override
    public Mono<Void> postCustomer(
            @NotNull @Valid Customer customer
    ) {
        log.info("Start saving the customer.");
        log.debug("Save customer with Request: [{}]", customer);
        return personRepository.save(personRepositoryMapper.customerToEntity(customer))
                .doOnError(error->log.error("Error saving person, detail: {}", error.getMessage()))
                .flatMap(personEntity ->
                        customerRepository.save(customerRepositoryMapper.postCustomerToEntity(customer,personEntity.getPersonId()))
                                .doOnError(error->log.error("Error saving customer, detail: {}", error.getMessage()))
                )
                .doOnSuccess(success->log.info("Customer saved successfully"))
                .then();
    }

    @Override
    public Mono<Void> patchCustomer(
            @NotNull @Valid Customer customer
    ) {
        log.info("Start the modification of customer data.");
        log.debug("Modify customer data with Request: {}", customer);
        return getCustomerEntityById(customer.getCustomerId())
                .flatMap(customerEntity ->
                        personRepository.findByPersonId(customerEntity.getPersonId())
                        .doOnError(error->log.error("Error modifying customer, detail: {}", error.getMessage()))
                        .flatMap(personEntity ->
                                Mono.zip(
                                        personRepository.save(personRepositoryMapper.patchRequestToEntity(personEntity,customer))
                                                .doOnError(error->log.error("Error when modifying the person's data, detail: {}", error.getMessage())),
                                        customerRepository.save(customerRepositoryMapper.patchCustomerToEntity(customerEntity,customer))
                                                .doOnError(error->log.error("Error modifying customer data, detail: {}", error.getMessage()))
                                )
                        )
                )
                .doOnSuccess(success->log.info("Customer data modified successfully"))
                .then();
    }

    @Override
    public Mono<Void> putCustomer(
            @NotNull @Valid Customer customer
    ) {
        log.info("Starts the complete customer modification.");
        log.debug("Modify customer with Request: [{}]", customer);
        return getCustomerEntityById(customer.getCustomerId())
                .flatMap(customerEntity ->
                        Mono.zip(
                                personRepository.save(personRepositoryMapper.putRequestToEntity(customer, customerEntity.getPersonId()))
                                        .doOnError(error->log.error("Error modifying person, detail: {}", error.getMessage())),
                                customerRepository.save(customerRepositoryMapper.putCustomerToEntity(customer, customerEntity.getPersonId()))
                                        .doOnError(error->log.error("Error modifying complete customer, detail: {}", error.getMessage()))
                        )
                )
                .doOnSuccess(success->log.info("Customer modified successfully"))
                .then();
    }

    @Override
    public Mono<Void> deleteCustomer(
            @NotNull Long customerId
    ) {
        log.info("Start deleting the client with Id: {}", customerId);
        return getCustomerEntityById(customerId)
                .flatMap(customerEntity -> {
                    customerEntity.setStatus(Boolean.FALSE);
                    return customerRepository.save(customerEntity)
                            .doOnError(error->log.error("Error deleting customer, details: {}", error.getMessage()))
                            .onErrorMap(error->new DeleteException());
                })
                .doOnSuccess(success->log.info("Customer deleted successfully"))
                .then();
    }

    public Mono<CustomerEntity> getCustomerEntityById(Long customerId) {
        log.info("Returns client by id: {}", customerId);
        return customerRepository.findById(customerId)
                .doOnError(error->log.error("Error retrieving client by id, detail: {}", error.getMessage()))
                .switchIfEmpty(Mono.error(new RegisterNotFoundException()));
    }

}
