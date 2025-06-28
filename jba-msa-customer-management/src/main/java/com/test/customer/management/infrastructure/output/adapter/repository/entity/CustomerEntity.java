package com.test.customer.management.infrastructure.output.adapter.repository.entity;

import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("customer")
@ToString
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEntity {
    @Id
    @Column("customer_id")
    private Long customerId;

    @Column("password")
    @Size(min = 1, max = 30)
    private String password;

    @Column("status")
    private Boolean status;

    @Column("person_id")
    private Long personId;
}
