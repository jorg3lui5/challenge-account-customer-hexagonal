package com.test.account.management.domain;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountMovementReport {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull
    @Valid
    private LocalDate date;
    @NotNull
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z\\sñÑÁáÉéÍíÓóÚú]+$")
    @Size(min = 1, max = 50)
    private String customer;
    @NotNull
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    @Size(min = 1, max = 15)
    private String accountNumber;
    @NotNull
    @NotBlank
    private String type;
    @DecimalMin("0.0")
    private Double initialBalance;
    private Boolean status;
    private String movement;
    @DecimalMin("0.0")
    private Double availableBalance;
}
