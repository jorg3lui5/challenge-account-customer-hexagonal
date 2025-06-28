package com.test.account.management.infrastructure.input.adapter.rest.impl;

import com.test.account.management.infrastructure.input.adapter.rest.bs.MovementsApi;
import com.test.account.management.infrastructure.input.adapter.rest.bs.bean.GetMovementResponse;
import com.test.account.management.infrastructure.input.adapter.rest.bs.bean.PatchMovementRequest;
import com.test.account.management.infrastructure.input.adapter.rest.bs.bean.PostMovementRequest;
import com.test.account.management.infrastructure.input.adapter.rest.bs.bean.PutMovementRequest;
import com.test.account.management.application.input.port.MovementService;
import com.test.account.management.infrastructure.input.adapter.rest.mapper.MovementMapper;
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
public class MovementController implements MovementsApi {

    MovementService movementService;
    MovementMapper movementMapper;

    @Override
    public Mono<ResponseEntity<GetMovementResponse>> getMovementById(
            Long movementId,
            final ServerWebExchange exchange
    ) {
        log.info("|--> MovementController - getMovementById init");
        log.debug(
                "|--> MovementController - getMovementById with movementId: {}",movementId);
        return movementService.getMovementById(movementId)
                .map(movementMapper::toMovementResponse)
                .map(response -> ResponseEntity.ok().body(response))
                .doOnSuccess(response -> log
                        .info("<--| MovementController - getMovementById finished successfully"))
                .doOnError(error -> log.error(
                        "<--| MovementController - getMovementById finished with error: {}",
                        error.getMessage()));
    }

    @Override
    public Mono<ResponseEntity<Flux<GetMovementResponse>>> getMovements(
            final ServerWebExchange exchange
    ) {
        log.info("|--> MovementController - getMovements init");
        return movementService.getMovements()
                .map(movementMapper::toMovementResponse)
                .collectList()
                .map(movementList -> ResponseEntity.ok().body(Flux.fromIterable(movementList)))
                .doOnSuccess(response -> log
                        .info("<--| MovementController - getMovements finished successfully"))
                .doOnError(error -> log.error(
                        "<--| MovementController - getMovements finished with error: {}",
                        error.getMessage()));
    }

    @Override
    public Mono<ResponseEntity<Void>> patchMovement(
            Mono<PatchMovementRequest> patchMovementRequest,
            final ServerWebExchange exchange
    ) {
        log.info("|--> MovementController - patchMovement init");
        return patchMovementRequest.flatMap(request -> {
                    log.debug(
                            "|--> MovementController - patchMovement Request in Json: [request:{}]",
                            request);
                    return movementService.patchMovement(movementMapper.toMovement(request));
                })
                .doOnSuccess(response -> log.info(
                        "<--| MovementController - patchMovement finished successfully"))
                .doOnError(error -> log.error(
                        "<--| MovementController - patchMovement finished with error: {}",
                        error.getMessage()))
                .map(response -> ResponseEntity.ok().build());
    }

    @Override
    public Mono<ResponseEntity<Void>> postMovement(
            Mono<PostMovementRequest> postMovementRequest,
            final ServerWebExchange exchange
    ) {
        log.info("|--> MovementController - postMovement init");
        return postMovementRequest.flatMap(request -> {
                    log.debug(
                            "|--> MovementController - postMovement Request in Json: [request:{}]",
                            request);
                    return movementService.postMovement(movementMapper.toMovement(request));
                })
                .doOnSuccess(response -> log.info(
                        "<--| MovementController - postMovement finished successfully"))
                .doOnError(error -> log.error(
                        "<--| MovementController - postMovement finished with error: {}",
                        error.getMessage()))
                .map(response -> ResponseEntity.ok().build());
    }

    @Override
    public Mono<ResponseEntity<Void>> putMovement(
            Mono<PutMovementRequest> putMovementRequest,
            final ServerWebExchange exchange
    ) {
        log.info("|--> MovementController - putMovement init");
        return putMovementRequest.flatMap(request -> {
                    log.debug(
                            "|--> MovementController - putMovement Request in Json: [request:{}]",
                            request);
                    return movementService.putMovement(movementMapper.toMovement(request));
                })
                .doOnSuccess(response -> log.info(
                        "<--| MovementController - putMovement finished successfully"))
                .doOnError(error -> log.error(
                        "<--| MovementController - putMovement finished with error: {}",
                        error.getMessage()))
                .map(response -> ResponseEntity.ok().build());
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteMovement(
            Long movementId,
            final ServerWebExchange exchange
    ) {
        log.info("|--> MovementController - deleteMovement init");
        log.debug(
                "|--> MovementController - deleteMovement with movementId: {}",movementId);
        return movementService.deleteMovement(movementId)
                .doOnSuccess(response -> log
                        .info("<--| MovementController - deleteMovement finished successfully"))
                .doOnError(error -> log.error(
                        "<--| MovementController - deleteMovement finished with error: {}",
                        error.getMessage()))
                .map(response -> ResponseEntity.ok().build());
    }
}
