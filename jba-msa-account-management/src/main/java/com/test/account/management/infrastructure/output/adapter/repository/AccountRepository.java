package com.test.account.management.infrastructure.output.adapter.repository;

import com.test.account.management.infrastructure.output.adapter.repository.entity.AccountEntity;
import io.micrometer.common.lang.NonNull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Validated
public interface AccountRepository {

    @NonNull
    Mono<AccountEntity> getAccountById(
            @NotNull Long accountId
    );

    @NonNull
    Flux<AccountEntity> getAccounts(
    );

    @NonNull
    Flux<AccountEntity> getAccountsByCustomerId(
            @NotNull Long customerId
    );

    Mono<AccountEntity> saveAccount(
            @NotNull @Valid AccountEntity entity
    );

    Mono<Boolean> deleteAccount(
            @NotNull Long accountId
    );

}
