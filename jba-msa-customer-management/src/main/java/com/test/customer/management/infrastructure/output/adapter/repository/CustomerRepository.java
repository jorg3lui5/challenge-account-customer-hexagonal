package com.test.customer.management.infrastructure.output.adapter.repository;

import com.test.customer.management.infrastructure.output.adapter.repository.entity.CustomerDataEntity;
import com.test.customer.management.infrastructure.output.adapter.repository.entity.CustomerEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerRepository extends ReactiveCrudRepository<CustomerEntity, Long> {
    @Query("select * from customer c " +
            "INNER JOIN person p ON c.person_id = p.person_id " +
            "WHERE c.customer_id = :customerId ")
    Mono<CustomerDataEntity> findByCustomerId(Long customerId);

    @Query("select * from customer c " +
            "INNER JOIN person p ON c.person_id = p.person_id " +
            "WHERE p.identification = :identification ")
    Mono<CustomerDataEntity> findByIdentification(String identification);

    @Query("select * from customer c " +
            "INNER JOIN person p ON c.person_id = p.person_id " +
            "and c.status = true")
    Flux<CustomerDataEntity> findAllCustomers();
}
