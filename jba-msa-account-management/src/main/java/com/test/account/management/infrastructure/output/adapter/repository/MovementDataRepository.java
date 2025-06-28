package com.test.account.management.infrastructure.output.adapter.repository;

import com.test.account.management.infrastructure.output.adapter.repository.entity.MovementEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface MovementDataRepository extends ReactiveCrudRepository<MovementEntity, Long> {

	Mono<MovementEntity> findByMovementId(Long movementId);

	@Query("select * from movement c " +
			"and status = true ")
	Flux<MovementEntity> findAllMovements();

	@Query("select * from movement m " +
			"WHERE m.account_id = :accountId " +
			"and status = true " +
			"order by m.date desc " +
			"limit 1")
	Mono<MovementEntity> findLastMovementByAccountId(Long accountId);

	@Query("select * from movement m " +
			"WHERE m.account_id = :accountId " +
			"order by m.date desc, m.movement_id desc")
	Flux<MovementEntity> findMovementsByAccountId(Long accountId);

	@Query("select * from movement m " +
			"WHERE m.account_id = :accountId " +
			"and status = true " +
			"order by m.date desc, m.movement_id desc")
	Flux<MovementEntity> findActiveMovementsByAccountId(Long accountId);
}
