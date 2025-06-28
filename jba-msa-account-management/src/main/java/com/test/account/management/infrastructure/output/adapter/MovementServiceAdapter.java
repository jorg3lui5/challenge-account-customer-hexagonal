package com.test.account.management.infrastructure.output.adapter;

import com.test.account.management.application.output.port.MovementServicePort;
import com.test.account.management.domain.Movement;
import com.test.account.management.infrastructure.output.adapter.repository.MovementRepository;
import com.test.account.management.infrastructure.output.adapter.repository.entity.MovementEntity;
import com.test.account.management.infrastructure.output.adapter.repository.mapper.MovementRepositoryMapper;
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
public class MovementServiceAdapter implements MovementServicePort {

    MovementRepository movementRepository;
    MovementRepositoryMapper movementRepositoryMapper;

    @NonNull
    @Override
    public Mono<Movement> getMovementById(
            @NotNull Long movementId
    ) {
        return movementRepository.getMovementById(movementId)
                .map(entity->movementRepositoryMapper.entityToMovement(entity));
    }

    @NonNull
    @Override
    public Flux<Movement> getMovements(
    ) {
        return movementRepository.getMovements()
                .map(entity -> movementRepositoryMapper.entityToMovement(entity));
    }

    @NonNull
    public Flux<Movement> getMovementsByAccountId(
            @NotNull Long accountId
    ) {
        return movementRepository.getMovementsByAccountId(accountId)
                .map(entity -> movementRepositoryMapper.entityToMovement(entity));
    }

    @Override
    public Mono<Boolean> postMovement(
            @NotNull @Valid Movement movement
    ) {
        return movementRepository.saveMovement(movementRepositoryMapper.postMovementToEntity(movement))
                .thenReturn(Boolean.TRUE)
                .doOnError(e->log.error("Error saving movement, detail: {}", e.getMessage()))
                .doOnSuccess(success->log.info("Movement saved successfully"));
    }

    @Override
    public Mono<Boolean> putMovement(
            @NotNull @Valid Movement movement
    ) {
        return movementRepository.saveMovement(movementRepositoryMapper.putMovementToEntity(movement))
                .thenReturn(Boolean.TRUE)
                .doOnError(e->log.error("Error updating all field of movement, detail: {}", e.getMessage()))
                .doOnSuccess(success->log.info("Movement update with all fields successfully"));
    }

    @Override
    public Mono<Boolean> patchMovement(
            @NotNull @Valid Movement movement
    ) {
        return movementRepository.getMovementById(movement.getMovementId())
                .flatMap(movementEntity ->
                        movementRepository.saveMovement(movementRepositoryMapper.patchMovementToEntity(movementEntity, movement))
                                .thenReturn(Boolean.TRUE)
                )
                .doOnError(e->log.error("Error updating some fields of movement, detail: {}", e.getMessage()))
                .doOnSuccess(success->log.info("Movement update successfully"));
    }

    @Override
    public Mono<Boolean> deleteMovement(
            @NotNull Long movementId
    ) {
        return  movementRepository.deleteMovement(movementId)
                .doOnError(e->log.error("Error deleting movement, detail: {}", e.getMessage()))
                .doOnSuccess(success->log.info("Movement deleted successfully"));
    }

    @NotNull
    @Override
    public Mono<Boolean> checkAccountWithoutMovements(
            @NotNull Long accountId
    ) {
        log.info("Start checking account movements");
        return movementRepository.checkAccountWithoutMovements(accountId)
                .doOnError(error->log.error("Error to verify movements of account: {}", error.getMessage()))
                .doOnSuccess(success->log.info("Account movements verify successfully"));

    }

    @Override
    public @NotNull Mono<Movement> getLastMovementByAccountId(
            @NotNull Long accountId
    ) {
        log.info("Start getting Last movement by account id.");
        return movementRepository.getLastMovementByAccountId(accountId)
                .map(entity->movementRepositoryMapper.entityToMovement(entity));
    }

}
