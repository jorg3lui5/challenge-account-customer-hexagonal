package com.test.account.management.infrastructure.input.adapter.rest.impl;

import com.test.account.management.infrastructure.input.adapter.rest.bs.AccountsApi;
import com.test.account.management.infrastructure.input.adapter.rest.bs.bean.GetAccountResponse;
import com.test.account.management.infrastructure.input.adapter.rest.bs.bean.PatchAccountRequest;
import com.test.account.management.infrastructure.input.adapter.rest.bs.bean.PostAccountRequest;
import com.test.account.management.infrastructure.input.adapter.rest.bs.bean.PutAccountRequest;
import com.test.account.management.application.input.port.AccountService;
import com.test.account.management.infrastructure.input.adapter.rest.mapper.AccountMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountController implements AccountsApi {

    AccountService accountService;
    AccountMapper accountMapper;

    @Override
    public Mono<ResponseEntity<GetAccountResponse>> getAccountById(
            Long accountId,
            final ServerWebExchange exchange
    ) {
        log.info("|--> AccountController - getAccountById init");
        log.debug(
                "|--> AccountController - getAccountById with accountId: {}",accountId);
        return accountService.getAccountById(accountId)
                .map(accountMapper::toAccountResponse)
                .map(response -> ResponseEntity.ok().body(response))
                .doOnSuccess(response -> log
                        .info("<--| AccountController - getAccountById finished successfully"))
                .doOnError(error -> log.error(
                        "<--| AccountController - getAccountById finished with error: {}",
                        error.getMessage()));
    }

    @Override
    public Mono<ResponseEntity<Flux<GetAccountResponse>>> getAccounts(
            final ServerWebExchange exchange
    ) {
        log.info("|--> AccountController - getAccounts init");
        return accountService.getAccounts()
                .map(accountMapper::toAccountResponse)
                .collectList()
                .map(accountList -> ResponseEntity.ok().body(Flux.fromIterable(accountList)))
                .doOnSuccess(response -> log
                        .info("<--| AccountController - getAccounts finished successfully"))
                .doOnError(error -> log.error(
                        "<--| AccountController - getAccounts finished with error: {}",
                        error.getMessage()));
    }

    @Override
    public Mono<ResponseEntity<Void>> patchAccount(
            Mono<PatchAccountRequest> patchAccountRequest,
            final ServerWebExchange exchange
    ) {
        log.info("|--> AccountController - patchAccount init");
        return patchAccountRequest.flatMap(request -> {
                    log.debug(
                            "|--> AccountController - patchAccount Request in Json: [request:{}]",
                            request);
                    return accountService.patchAccount(accountMapper.toAccount(request));
                })
                .doOnSuccess(response -> log.info(
                        "<--| AccountController - patchAccount finished successfully"))
                .doOnError(error -> log.error(
                        "<--| AccountController - patchAccount finished with error: {}",
                        error.getMessage()))
                .map(response -> ResponseEntity.ok().build());
    }

    @Override
    public Mono<ResponseEntity<Void>> postAccount(
            Mono<PostAccountRequest> postAccountRequest,
            final ServerWebExchange exchange
    ) {
        log.info("|--> AccountController - postAccount init");
        return postAccountRequest.flatMap(request -> {
                    log.debug(
                            "|--> AccountController - postAccount Request in Json: [request:{}]",
                            request);
                    return accountService.postAccount(accountMapper.toAccount(request));
                })
                .doOnSuccess(response -> log.info(
                        "<--| AccountController - postAccount finished successfully"))
                .doOnError(error -> log.error(
                        "<--| AccountController - postAccount finished with error: {}",
                        error.getMessage()))
                .map(response -> ResponseEntity.ok().build());
    }

    @Override
    public Mono<ResponseEntity<Void>> putAccount(
            Mono<PutAccountRequest> putAccountRequest,
            final ServerWebExchange exchange
    ) {
        log.info("|--> AccountController - putAccount init");
        return putAccountRequest.flatMap(request -> {
                    log.debug(
                            "|--> AccountController - putAccount Request in Json: [request:{}]",
                            request);
                    return accountService.putAccount(accountMapper.toAccount(request));
                })
                .doOnSuccess(response -> log.info(
                        "<--| AccountController - putAccount finished successfully"))
                .doOnError(error -> log.error(
                        "<--| AccountController - putAccount finished with error: {}",
                        error.getMessage()))
                .map(response -> ResponseEntity.ok().build());
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteAccount(
            Long accountId,
            final ServerWebExchange exchange
    ) {
        log.info("|--> AccountController - deleteAccount init");
        log.debug(
                "|--> AccountController - deleteAccount with accountId: {}",accountId);
        return accountService.deleteAccount(accountId)
                .doOnSuccess(response -> log
                        .info("<--| AccountController - deleteAccount finished successfully"))
                .doOnError(error -> log.error(
                        "<--| AccountController - deleteAccount finished with error: {}",
                        error.getMessage()))
                .map(response -> ResponseEntity.ok().build());
    }
}
