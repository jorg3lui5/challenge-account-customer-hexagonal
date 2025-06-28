package com.test.account.management.domain;

import com.test.account.management.domain.enums.GenderEnum;
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
    @NotNull
    Long customerId;
    @NotNull
    @NotBlank
    @Pattern(regexp = "^[0-9]+$")
    @Size(min = 10, max = 13)
    String identification;
    @NotNull
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z\\sñÑÁáÉéÍíÓóÚú]+$")
    @Size(min = 1, max = 50)
    String name;
    @NotNull
    @Valid
    GenderEnum gender;
    @NotNull
    @Min(0)
    @Max(100)
    Integer age;
    @Pattern(regexp = "^[a-zA-Z0-9\\sñÑÁáÉéÍíÓóÚú.,\"&():_/-]+$")
    @Size(min = 1, max = 100)
    String address;
    @Pattern(regexp = "^[0-9]+$")
    @Size(min = 1, max = 10)
    String phone;
    @NotNull
    Boolean status;
}
