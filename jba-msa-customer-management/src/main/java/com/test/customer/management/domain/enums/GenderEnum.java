package com.test.customer.management.domain.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum GenderEnum {
    MASCULINO("Masculino"),

    FEMENINO("Femenino");

    private final String value;
}
