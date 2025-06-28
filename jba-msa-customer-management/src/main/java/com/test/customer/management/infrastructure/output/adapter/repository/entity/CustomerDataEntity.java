package com.test.customer.management.infrastructure.output.adapter.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDataEntity {

    private Long customerId;

    private String identification;

    private String name;

    private String gender;

    private Integer age;

    private String address;

    private String phone;

    private String password;

    private Boolean status;
}
