package com.test.account.management.infrastructure.output.adapter.repository;

import com.test.account.management.infrastructure.output.adapter.repository.entity.MovementEntity;
import io.micrometer.common.lang.NonNull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Validated
public interface MovementRepository {

    @NonNull
    Mono<MovementEntity> getMovementById(
            @NotNull Long movementId
    );

    @NonNull
    Flux<MovementEntity> getMovements(
    );

    @NonNull
    Flux<MovementEntity> getMovementsByAccountId(
            @NotNull Long accountId
    );

    @NonNull
    public Mono<MovementEntity> saveMovement(
            @NotNull @Valid MovementEntity entity
    );

    @NonNull
    public Mono<Boolean> deleteMovement(
            @NotNull Long movementId
    );

    @NotNull
    Mono<Boolean> checkAccountWithoutMovements(
            @NotNull Long accountId
    );

    @NotNull
    Mono<MovementEntity> getLastMovementByAccountId(
            @NotNull Long accountId
    );
}
