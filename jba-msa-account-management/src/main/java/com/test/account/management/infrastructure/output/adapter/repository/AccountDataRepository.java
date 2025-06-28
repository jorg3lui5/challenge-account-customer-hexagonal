package com.test.account.management.infrastructure.output.adapter.repository;

import com.test.account.management.infrastructure.output.adapter.repository.entity.AccountEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AccountDataRepository extends ReactiveCrudRepository<AccountEntity, Long> {

	Mono<AccountEntity> findByAccountId(Long accountId);

	@Query("select * from account c " +
			"WHERE c.customer_id = :customerId " +
			"and status = true ")
	Flux<AccountEntity> findByCustomerId(Long customerId);

	@Query("select * from account c " +
			"WHERE status = true ")
	Flux<AccountEntity> findAllAccounts();
}
