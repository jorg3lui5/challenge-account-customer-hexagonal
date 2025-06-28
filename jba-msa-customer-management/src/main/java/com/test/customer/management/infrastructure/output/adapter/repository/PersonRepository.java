package com.test.customer.management.infrastructure.output.adapter.repository;

import com.test.customer.management.infrastructure.output.adapter.repository.entity.PersonEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PersonRepository extends ReactiveCrudRepository<PersonEntity, Long> {

    @Query("select * from person WHERE person_id = :personId")
    Mono<PersonEntity> findByPersonId(Long personId);
}
