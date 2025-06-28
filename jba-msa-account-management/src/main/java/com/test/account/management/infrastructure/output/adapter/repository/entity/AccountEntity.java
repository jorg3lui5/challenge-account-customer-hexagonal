package com.test.account.management.infrastructure.output.adapter.repository.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.math.BigDecimal;

@Table("account")
@ToString
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountEntity {

  @Id
  @Column("account_id")
  private Long accountId;

  @Column("number")
  private String number;

  @Column("type")
  private String type;

  @Column("initial_balance")
  private BigDecimal initialBalance;

  @Column("status")
  private Boolean status;

  @Column("customer_id")
  private Long customerId;
}


