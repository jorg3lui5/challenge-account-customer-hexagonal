package com.test.account.management.application.output.port;

import com.test.account.management.domain.Account;
import io.micrometer.common.lang.NonNull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Validated
public interface AccountServicePort {

    @NonNull
    Mono<Account> getAccountById(
            @NotNull Long accountId
    );

    @NonNull
    Flux<Account> getAccounts(
    );

    @NonNull
    Flux<Account> getAccountsByCustomerId(
            @NotNull Long customerId
    );

    Mono<Boolean> patchAccount(
            @NotNull @Valid Account account
    );

    Mono<Boolean> postAccount(
            @NotNull @Valid Account account
    );

    Mono<Boolean> putAccount(
            @NotNull @Valid Account account
    );

    Mono<Boolean> deleteAccount(
            @NotNull Long accountId
    );
}
