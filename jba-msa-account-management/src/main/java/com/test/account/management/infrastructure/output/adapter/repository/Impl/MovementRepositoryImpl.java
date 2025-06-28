package com.test.account.management.infrastructure.output.adapter.repository.Impl;

import com.test.account.management.infrastructure.exception.custom.AccountNotUpdateByExistingMovementsException;
import com.test.account.management.infrastructure.exception.custom.MovementByIdNotFoundException;
import com.test.account.management.infrastructure.output.adapter.repository.MovementDataRepository;
import com.test.account.management.infrastructure.output.adapter.repository.MovementRepository;
import com.test.account.management.infrastructure.output.adapter.repository.entity.MovementEntity;
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
public class MovementRepositoryImpl implements MovementRepository {

    MovementDataRepository movementDataRepository;

    @NonNull
    @Override
    public Mono<MovementEntity> getMovementById(
            @NotNull Long movementId
    ) {
        log.info("Start retrieving the movement with id: {}",movementId);
        return movementDataRepository.findByMovementId(movementId)
                .doOnError(error->log.error("Error retrieving movement by id, detail: {}", error.getMessage()))
                .switchIfEmpty(Mono.error(new MovementByIdNotFoundException()))
                .doOnSuccess(success->log.info("Movement successfully recovered"));
    }

    @NonNull
    @Override
    public Flux<MovementEntity> getMovementsByAccountId(
            @NotNull Long accountId
    ) {
        log.info("Start retrieving movements by account id: {}", accountId);
        return movementDataRepository.findMovementsByAccountId(accountId)
                .doOnError(error->log.error("Error retrieving accounts, detail: {}", error.getMessage()))
                .doOnComplete(()->log.info("All movements recovered successfully"));
    }

    @NonNull
    @Override
    public Flux<MovementEntity> getMovements(
    ) {
        log.info("Start retrieving movements");
        return movementDataRepository.findAllMovements()
                .doOnError(error->log.error("Error retrieving accounts, detail: {}", error.getMessage()))
                .doOnComplete(()->log.info("All movements recovered successfully"));
    }

    @NonNull
    @Override
    public Mono<MovementEntity> saveMovement(
            @NotNull @Valid MovementEntity entity
    ) {
        return movementDataRepository.save(entity)
                .doOnError(error->log.error("Error saving movement, details: {}", error.getMessage()))
                .doOnSuccess(success->log.info("Movement saved successfully"));
    }

    @NonNull
    @Override
    public Mono<Boolean> deleteMovement(
            @NotNull Long movementId
    ) {
        return getMovementById(movementId)
                .flatMap(entity -> {
                    entity.setStatus(Boolean.FALSE);
                    return saveMovement(entity);
                }).thenReturn(Boolean.TRUE);
    }

    @NotNull
    @Override
    public Mono<Boolean> checkAccountWithoutMovements(
            @NotNull Long accountId
    ) {
        return movementDataRepository.findMovementsByAccountId(accountId)
                .doOnError(error->log.error("Error checking if the account already has movements, details: {}", error.getMessage()))
                .hasElements()
                .flatMap(hasMovements -> {
                    if (!hasMovements) {
                        log.warn("The account has no movements");
                        return Mono.just(Boolean.TRUE);
                    }
                    log.info("The account has movements");
                    return Mono.error(new AccountNotUpdateByExistingMovementsException());
                });
    }

    @Override
    public @NotNull Mono<MovementEntity> getLastMovementByAccountId(
            @NotNull Long accountId
    ){
        return movementDataRepository.findLastMovementByAccountId(accountId)
                .doOnError(error->log.error("Error getting the last movement by accountId, details: {}", error.getMessage()))
                .doOnSuccess(success->log.info("Last movement consulted successfully"));

    }
}
