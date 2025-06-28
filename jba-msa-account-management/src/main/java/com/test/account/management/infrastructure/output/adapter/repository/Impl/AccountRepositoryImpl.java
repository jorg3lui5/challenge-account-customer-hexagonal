package com.test.account.management.infrastructure.output.adapter.repository.Impl;

import com.test.account.management.infrastructure.exception.custom.AccountByCustomerIdNotFoundException;
import com.test.account.management.infrastructure.exception.custom.AccountByIdNotFoundException;
import com.test.account.management.infrastructure.exception.custom.GetRegisterException;
import com.test.account.management.infrastructure.exception.custom.GetRegistersException;
import com.test.account.management.infrastructure.output.adapter.repository.AccountDataRepository;
import com.test.account.management.infrastructure.output.adapter.repository.AccountRepository;
import com.test.account.management.infrastructure.output.adapter.repository.entity.AccountEntity;
import com.test.account.management.infrastructure.output.adapter.repository.mapper.AccountRepositoryMapper;
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
public class AccountRepositoryImpl implements AccountRepository {

    AccountDataRepository accountDataRepository;
    AccountRepositoryMapper accountRepositoryMapper;

    @NonNull
    @Override
    public Mono<AccountEntity> getAccountById(
            @NotNull Long accountId
    ) {
        log.info("Start retrieving the account with id: {}",accountId);
        return accountDataRepository.findByAccountId(accountId)
                .doOnError(error->log.error("Error retrieving account by id, detail: {}", error.getMessage()))
                .onErrorMap(error->new GetRegisterException())
                .switchIfEmpty(Mono.error(new AccountByIdNotFoundException()))
                .doOnSuccess(success->log.info("Account successfully recovered"));
    }

    @NonNull
    @Override
    public Flux<AccountEntity> getAccounts(
    ) {
        log.info("Start retrieving accounts");
        return accountDataRepository.findAllAccounts()
                .doOnError(error->log.error("Error retrieving accounts, detail: {}", error.getMessage()))
                .onErrorMap(error->new GetRegistersException())
                .doOnComplete(()->log.info("All Accounts successfully recovered"));
    }

    @NonNull
    @Override
    public Flux<AccountEntity> getAccountsByCustomerId(
            @NotNull Long customerId
    ) {
        log.info("Start retrieving accounts with customer id: {}", customerId);
        return accountDataRepository.findByCustomerId(customerId)
                .doOnError(error->log.error("Error retrieving accounts by customer id, detail: {}", error.getMessage()))
                .onErrorMap(error->new GetRegisterException())
                .switchIfEmpty(Mono.error(new AccountByCustomerIdNotFoundException()))
                .doOnComplete(()->log.info("Accounts by customer Id recovered successfully"));

    }

    @Override
    public Mono<AccountEntity> saveAccount(
            @NotNull @Valid AccountEntity entity
    ) {
        return accountDataRepository.save(entity)
                .doOnError(error->log.error("Error saving account, details: {}", error.getMessage()))
                .doOnSuccess(success->log.info("Account saved successfully"));
    }

    @Override
    public Mono<Boolean> deleteAccount(
            @NotNull Long accountId
    ) {
        return getAccountById(accountId)
                .flatMap(entity -> {
                    entity.setStatus(Boolean.FALSE);
                    return saveAccount(entity);
                }).thenReturn(Boolean.TRUE);
    }
}
