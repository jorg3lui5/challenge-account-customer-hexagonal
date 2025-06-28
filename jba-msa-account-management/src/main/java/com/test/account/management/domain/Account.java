package com.test.account.management.domain;

import com.test.account.management.domain.enums.AccountTypeEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Account {

    Long accountId;
    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    @Size(min = 1, max = 15)
    String number;
    AccountTypeEnum type;
    @DecimalMin("0.0")
    Double initialBalance;
    Boolean status;
    Customer customer;
}
