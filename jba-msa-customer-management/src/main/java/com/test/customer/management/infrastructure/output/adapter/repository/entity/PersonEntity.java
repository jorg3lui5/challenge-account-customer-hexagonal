package com.test.customer.management.infrastructure.output.adapter.repository.entity;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("person")
@ToString
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonEntity {

  @Id
  @Column("person_id")
  private Long personId;

  @Column("identification")
  @NotNull
  @NotBlank
  @Pattern(regexp = "^[0-9]+$")
  @Size(min = 10, max = 13)
  private String identification;

  @Column("name")
  @NotNull
  @NotBlank
  @Pattern(regexp = "^[a-zA-Z\\sñÑÁáÉéÍíÓóÚú]+$")
  @Size(min = 1, max = 50)
  private String name;

  @Column("gender")
  @NotNull
  @NotBlank
  private String gender;

  @Column("age")
  @NotNull
  @Min(0)
  @Max(100)
  private Integer age;

  @Column("address")
  @Pattern(regexp = "^[a-zA-Z0-9\\sñÑÁáÉéÍíÓóÚú.,\"&():_/-]+$")
  @Size(min = 1, max = 100)
  private String address;

  @Column("phone")
  @Pattern(regexp = "^[0-9]+$")
  @Size(min = 1, max = 10)
  private String phone;

}
