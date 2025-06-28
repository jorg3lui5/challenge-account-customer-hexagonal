package com.test.account.management.application.output.port;

import com.test.account.management.domain.Movement;
import com.test.account.management.infrastructure.output.adapter.repository.entity.MovementEntity;
import io.micrometer.common.lang.NonNull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Validated
public interface MovementServicePort {

    @NonNull
    Mono<Movement> getMovementById(
            @NotNull Long movementId
    );

    @NonNull
    Flux<Movement> getMovements(
    );

    @NonNull
    Flux<Movement> getMovementsByAccountId(
            @NotNull Long accountId
    );

    Mono<Boolean> patchMovement(
            @NotNull @Valid Movement movement
    );

    Mono<Boolean> postMovement(
            @NotNull @Valid Movement movement
    );

    Mono<Boolean> putMovement(
            @NotNull @Valid Movement movement
    );

    Mono<Boolean> deleteMovement(
            @NotNull Long movementId
    );

    @NotNull
    Mono<Boolean> checkAccountWithoutMovements(
            @NotNull Long accountId
    );

    @NotNull
    Mono<Movement> getLastMovementByAccountId(
            @NotNull Long accountId
    );
}
