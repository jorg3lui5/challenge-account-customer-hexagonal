package com.test.account.management.infrastructure.output.adapter;

import com.test.account.management.application.output.port.AccountServicePort;
import com.test.account.management.domain.Account;
import com.test.account.management.infrastructure.exception.custom.DeleteException;
import com.test.account.management.infrastructure.exception.custom.SaveException;
import com.test.account.management.infrastructure.exception.custom.UpdateException;
import com.test.account.management.infrastructure.output.adapter.repository.AccountRepository;
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
public class AccountServiceAdapter implements AccountServicePort {

    AccountRepository accountRepository;
    AccountRepositoryMapper accountRepositoryMapper;

    @NonNull
    @Override
    public Mono<Account> getAccountById(
            @NotNull Long accountId
    ) {
        return accountRepository.getAccountById(accountId)
                .map(entity->accountRepositoryMapper.entityToAccount(entity));
    }

    @NonNull
    @Override
    public Flux<Account> getAccounts(
    ) {
        return accountRepository.getAccounts()
                .map(entity -> accountRepositoryMapper.entityToAccount(entity));
    }

    @NonNull
    @Override
    public Flux<Account> getAccountsByCustomerId(
            @NotNull Long customerId
    ) {
        return accountRepository.getAccountsByCustomerId(customerId)
                .map(entity->accountRepositoryMapper.entityToAccount(entity));
    }

    @Override
    public Mono<Boolean> postAccount(
            @NotNull @Valid Account account
    ) {
        return accountRepository.saveAccount(accountRepositoryMapper.postAccountToEntity(account))
                .thenReturn(Boolean.TRUE)
                .doOnError(e->log.error("Error saving account, detail: {}", e.getMessage()))
                .onErrorMap(error->new SaveException())
                .doOnSuccess(success->log.info("Account saved successfully"));
    }

    @Override
    public Mono<Boolean> putAccount(
            @NotNull @Valid Account account
    ) {
        return accountRepository.saveAccount(accountRepositoryMapper.putAccountToEntity(account))
                .thenReturn(Boolean.TRUE)
                .doOnError(e->log.error("Error updating all field of account, detail: {}", e.getMessage()))
                .onErrorMap(error->new UpdateException())
                .doOnSuccess(success->log.info("Account update with all fields successfully"));
    }

    @Override
    public Mono<Boolean> patchAccount(
            @NotNull @Valid Account account
    ) {
        return accountRepository.getAccountById(account.getAccountId())
                .flatMap(accountEntity ->
                        accountRepository.saveAccount(accountRepositoryMapper.patchAccountToEntity(accountEntity, account))
                        .thenReturn(Boolean.TRUE)
                )
                .doOnError(e->log.error("Error updating some fields of account, detail: {}", e.getMessage()))
                .onErrorMap(error->new UpdateException())
                .doOnSuccess(success->log.info("Account update successfully"));
    }

    @Override
    public Mono<Boolean> deleteAccount(
            @NotNull Long accountId
    ) {
        return  accountRepository.deleteAccount(accountId)
                .doOnError(e->log.error("Error deleting account, detail: {}", e.getMessage()))
                .onErrorMap(error->new DeleteException())
                .doOnSuccess(success->log.info("Account deleted successfully"));
    }



}
