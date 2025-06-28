package com.test.account.management.application.service;

import com.test.account.management.application.input.port.AccountService;
import com.test.account.management.application.input.port.MovementService;
import com.test.account.management.application.output.port.MovementServicePort;
import com.test.account.management.domain.Account;
import com.test.account.management.domain.Movement;
import com.test.account.management.domain.enums.MovementTypeEnum;
import com.test.account.management.infrastructure.exception.custom.AccountMovementNotFoundException;
import com.test.account.management.infrastructure.exception.custom.InvalidDateMovement;
import com.test.account.management.infrastructure.exception.custom.InvalidDebitAmountException;
import com.test.account.management.infrastructure.exception.custom.MethodNotImplementException;
import com.test.account.management.infrastructure.output.adapter.mapper.MovementAdapterMapper;
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

import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MovementServiceImp implements MovementService {

    MovementServicePort movementServicePort;
    AccountService accountService;
    MovementAdapterMapper movementAdapterMapper;

    @NonNull
    @Override
    public Mono<Movement> getMovementById(
            @NotNull Long movementId
    ) {
        log.info("Inicia obtención del movimiento con Id: {}",movementId);
        return movementServicePort.getMovementById(movementId)
                .flatMap(this::getMovementWithAccount)
                .doOnSuccess(success -> log.info("Movement retrieved successfully"));
    }

    @NonNull
    @Override
    public Flux<Movement> getMovements(
    ) {
        log.info("Inicia obtención de la lista de movimientos");
        return movementServicePort.getMovements()
                .flatMap(this::getMovementWithAccount)
                .doOnComplete(() -> log.info("Movements retrieved successfully"));

    }

    @NonNull
    public Flux<Movement> getMovementsByAccountId(
            @NotNull Long accountId
    ) {
        log.info("Inicia obtención de la lista de movimientos with accountId: {}", accountId);
        return movementServicePort.getMovementsByAccountId(accountId)
                .flatMap(this::getMovementWithAccount)
                .doOnComplete(() -> log.info("Movements by accountId retrieved successfully"));
    }

    @Override
    public Mono<Boolean> postMovement(
            @NotNull @Valid Movement movement
    ) {
        log.info("Inicia el guardado del movimiento.");
        log.debug("Guarda movimiento. Request: [{}]", movement);
        return accountService.getAccountById(movement.getAccount().getAccountId())
                .switchIfEmpty(Mono.error(new AccountMovementNotFoundException()))
                .flatMap(account ->
                    canPerformTransaction(account.getInitialBalance(),movement)
                    .flatMap(ok-> saveMovement(account, movement))
                )
                .doOnSuccess(success->log.info("Movimiento guardado correctamente"));
    }

    @Override
    public Mono<Boolean> putMovement(
            @NotNull @Valid Movement movement
    ) {
//        log.info("Inicia modificación de datos del movimiento");
//        log.debug("Movimiento a modificar, request: {}", movement);
//        return movementServicePort.getMovementById(movement.getMovementId())
//                .flatMap(existingMovement ->
//                        accountService.getAccountById(movement.getAccount().getAccountId())
//                        .onErrorMap(e->AccountManagementError.FE08_NO_EXISTE_CUENTA_PARA_MOVIMIENTO)
//                        .flatMap(customer -> movementServicePort.putMovement(movement))
//                ).doOnSuccess(success->log.info("Movimiento modificado correctamente"));

        log.warn("Método no implementado");
        return Mono.error(new MethodNotImplementException());
    }

    @Override
    public Mono<Boolean> patchMovement(
            @NotNull @Valid Movement movement
    ) {
//        log.info("Inicia modificación de datos del movimiento");
//        log.debug("Datos de movimiento a modificar, request: [{}]", movement);
//        return getMovementById(movement.getMovementId())
//                .flatMap(existingMovement ->
//                        movementServicePort.patchMovement(movement)
//                )
//                .doOnSuccess(success->log.info("Datos del movimiento modificados correctamente"));
        log.warn("Método no implementado");
        return Mono.error(new MethodNotImplementException());
    }

    @Override
    public Mono<Boolean> deleteMovement(
            @NotNull Long movementId
    ) {
        log.info("Inicia la eliminación del movimiento con id: {}", movementId);
        return movementServicePort.deleteMovement(movementId)
                .doOnSuccess(success->log.info("Movimiento eliminado correctamente"));
    }

    @NotNull
    @Override
    public Mono<Boolean> checkAccountWithoutMovements(
            @NotNull Long accountId
    ) {
        return movementServicePort.checkAccountWithoutMovements(accountId);
    }

    private Mono<Movement> getMovementWithAccount(
            Movement movement
    ) {
        log.info("Inicia obtención del movimiento con su cuenta");
        return accountService.getAccountById(movement.getAccount().getAccountId())
                .map(account ->
                        movementAdapterMapper.movementAndAccountToMovement(movement, account)
                )
                .doOnError(error->log.error("Error al recuperar el movimiento con cuenta, detalle: {}", error.getMessage()))
                .doOnSuccess(success->log.info("Movimiento con su cuenta recuperado correctamente"));
    }

    private Mono<Boolean> canPerformTransaction(Double availableBalance, Movement movement) {
        return validateTransactionDate(movement)
                .then(validateDebitOrCredit(availableBalance, movement));
    }

    private Mono<Boolean> validateTransactionDate(Movement movement) {
        return movementServicePort.getLastMovementByAccountId(movement.getAccount().getAccountId())
                .flatMap(existingMovement -> {
                    if (existingMovement.getDate().isAfter(movement.getDate())) {
                        Mono.error(new InvalidDateMovement());
                    }
                    log.info("Movement date is valid");
                    return Mono.just(Boolean.TRUE);
                })
                .defaultIfEmpty(Boolean.TRUE);
    }

    private Mono<Boolean> validateDebitOrCredit(Double availableBalance, Movement movement) {
        if (Objects.equals(movement.getType(), MovementTypeEnum.DEBIT)
            && availableBalance < movement.getValue()) {
            return Mono.error(new InvalidDebitAmountException());
        }
        log.info("Movement is allowed (debit or credit)");
        return Mono.just(Boolean.TRUE);
    }

    private Mono<Boolean> saveMovement(Account account, Movement movement) {
        movement.setBalance(account.getInitialBalance());

        return movementServicePort.postMovement(movement)
                .doOnError(error -> log.error("Failed to save movement, details: {}", error.getMessage()))
                .then(updateAccountBalance(account, movement));
    }

    private Mono<Boolean> updateAccountBalance(Account account, Movement movement) {
        double updatedBalance = movement.getType() == MovementTypeEnum.CREDIT
                ? account.getInitialBalance() + movement.getValue()
                : account.getInitialBalance() - movement.getValue();

        account.setInitialBalance(updatedBalance);

        return accountService.patchAccount(account)
                .doOnError(error -> log.error("Failed to update account balance, details: {}", error.getMessage()));
    }
}
