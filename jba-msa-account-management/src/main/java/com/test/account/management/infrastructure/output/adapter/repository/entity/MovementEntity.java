package com.test.account.management.infrastructure.output.adapter.repository.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Table("movement")
@ToString
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovementEntity {

  @Id
  @Column("movement_id")
  private Long movementId;

  @Column("date")
  private LocalDate date;

  @Column("type")
  private String type;

  @Column("value")
  private BigDecimal value;

  @Column("balance")
  private BigDecimal balance;

  @Column("status")
  private Boolean status;

  @Column("account_id")
  private Long accountId;
}
