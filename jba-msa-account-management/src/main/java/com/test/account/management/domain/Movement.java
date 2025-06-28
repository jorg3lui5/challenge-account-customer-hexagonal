package com.test.account.management.domain;

import com.test.account.management.domain.enums.MovementTypeEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Movement {

    Long movementId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Valid
    LocalDate date;
    @Valid
    MovementTypeEnum type;
    @DecimalMin("0.0")
    Double value;
    @DecimalMin("0.0")
    Double balance;
    Boolean status;
    @Valid
    Account account;
}
