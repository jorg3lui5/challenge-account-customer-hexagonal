package com.test.customer.management.domain;

import com.test.customer.management.domain.enums.GenderEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Customer {

    Long customerId;
    @Pattern(regexp = "^[0-9]+$")
    @Size(min = 10, max = 13)
    String identification;
    @Pattern(regexp = "^[a-zA-Z\\sñÑÁáÉéÍíÓóÚú]+$")
    @Size(min = 1, max = 50)
    String name;
    GenderEnum gender;
    @Min(0)
    @Max(100)
    Integer age;
    @Pattern(regexp = "^[a-zA-Z0-9\\sñÑÁáÉéÍíÓóÚú.,\"&():_/-]+$")
    @Size(min = 1, max = 100)
    String address;
    @Pattern(regexp = "^[0-9]+$")
    @Size(min = 1, max = 10)
    String phone;
    @Size(min = 1, max = 30)
    String password;
    Boolean status;
}
