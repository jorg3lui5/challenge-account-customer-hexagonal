package com.test.account.management.application.input.port;

import com.test.account.management.domain.Movement;
import io.micrometer.common.lang.NonNull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Validated
public interface MovementService {

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
            @NotNull @Valid Movement account
    );

    Mono<Boolean> postMovement(
            @NotNull @Valid Movement account
    );

    Mono<Boolean> putMovement(
            @NotNull @Valid Movement account
    );

    Mono<Boolean> deleteMovement(
            @NotNull Long movementId
    );

    @NotNull
    Mono<Boolean> checkAccountWithoutMovements(
            @NotNull Long accountId
    );
}
