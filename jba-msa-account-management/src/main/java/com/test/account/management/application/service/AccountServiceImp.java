package com.test.account.management.application.service;

import com.test.account.management.application.input.port.AccountService;
import com.test.account.management.application.output.port.AccountServicePort;
import com.test.account.management.application.output.port.CustomerServicePort;
import com.test.account.management.application.output.port.MovementServicePort;
import com.test.account.management.domain.Account;
import com.test.account.management.infrastructure.exception.custom.CustomerAccountNotFoundException;
import com.test.account.management.infrastructure.exception.custom.GetCustomerException;
import com.test.account.management.infrastructure.output.adapter.mapper.AccountAdapterMapper;
import io.micrometer.common.lang.NonNull;
import jakarta.validation.Valid;
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
public class AccountServiceImp implements AccountService {

    AccountAdapterMapper accountAdapterMapper;

    AccountServicePort accountServicePort;

    CustomerServicePort customerServicePort;

    MovementServicePort movementServicePort;

    @NonNull
    @Override
    public Mono<Account> getAccountById(
            @NotNull Long accountId
    ) {
        log.info("Starting account retrieval by ID. Account ID request: {}", accountId);
        return accountServicePort.getAccountById(accountId)
                .flatMap(this::getAccountWithCustomer)
                .doOnSuccess(success -> log.info("Account retrieved successfully"));
    }

    @NonNull
    @Override
    public Flux<Account> getAccounts(
    ) {
        log.info("Inicia obtención de la lista de cuentas");
        return accountServicePort.getAccounts()
                .flatMap(this::getAccountWithCustomer)
                .doOnComplete(() -> log.info("Accounts retrieved successfully"));
    }

    @NonNull
    @Override
    public Flux<Account> getAccountsByCustomerId(
            @NotNull Long customerId
    ) {
        log.info("Inicia obtención de la lista de cuentas con customerId: {}", customerId);
        return accountServicePort.getAccountsByCustomerId(customerId)
                .flatMap(this::getAccountWithCustomer)
                .doOnComplete(() -> log.info("Accounts by customerId retrieved successfully"));
    }

    @Override
    public Mono<Boolean> postAccount(
            @NotNull @Valid Account account
    ) {
        log.info("Inicia el guardado de la cuenta.");
        log.debug("Guarda cuenta Request: [{}]", account);
        return customerServicePort.getCustomerById(account.getCustomer().getCustomerId())
                .switchIfEmpty(Mono.error(new CustomerAccountNotFoundException()))
                .flatMap(customer ->
                        accountServicePort.postAccount(account)
                )
                .doOnSuccess(success->log.info("Cuenta guardada correctamente"));
    }

    @Override
    public Mono<Boolean> putAccount(
            @NotNull @Valid Account account
    ) {
        log.info("Inicia la modificación completa de la cuenta.");
        log.debug("Modifica cuenta Request: {}", account);
        return accountServicePort.getAccountById(account.getAccountId())
                .flatMap(existingAccount ->
                    movementServicePort.checkAccountWithoutMovements(existingAccount.getAccountId())
                    .flatMap(ok->
                        customerServicePort.getCustomerById(account.getCustomer().getCustomerId())
                        .onErrorMap(e->new CustomerAccountNotFoundException())
                        .flatMap(customer -> accountServicePort.putAccount(account))
                    )
                ).doOnSuccess(success->log.info("Cuenta modificada correctamente"));
    }

    @Override
    public Mono<Boolean> patchAccount(
            @NotNull @Valid Account account
    ) {
        log.info("Inicia la modificación de datos de la cuenta.");
        log.debug("Modifica datos de la cuenta Request: {}", account);
        return getAccountById(account.getAccountId())
                .flatMap(existingAccount ->
                    movementServicePort.checkAccountWithoutMovements(existingAccount.getAccountId())
                    .flatMap(ok -> accountServicePort.patchAccount(account)
                    )
                )
                .doOnSuccess(success->log.info("Datos de la cuenta modificados correctamente"));
    }

    @Override
    public Mono<Boolean> deleteAccount(
            @NotNull Long accountId
    ) {
        log.info("Inicia la eliminación de la cuenta. Id cuenta request: {}", accountId);
        return accountServicePort.deleteAccount(accountId)
                .doOnSuccess(success->log.info("Cuenta eliminada correctamente"));
    }

    private Mono<Account> getAccountWithCustomer(
            Account account
    ) {
        log.info("Inicia obtención de la cuenta con customer");
        return customerServicePort.getCustomerById(account.getCustomer().getCustomerId())
                .doOnError(error->log.error("Error al recuperar la cuenta con customer, detalle: {}", error.getMessage()))
                .onErrorMap(error-> new GetCustomerException())
                .map(customer ->
                        accountAdapterMapper.accountAndCustomerToAccount(account, customer)
                )
                .doOnSuccess(success->log.info("Cuenta con customer recuperado correctamente"));
    }

}
