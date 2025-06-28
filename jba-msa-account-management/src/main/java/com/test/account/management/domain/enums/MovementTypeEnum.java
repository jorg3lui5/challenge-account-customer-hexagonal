package com.test.account.management.domain.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum MovementTypeEnum {
    DEBIT("DEBIT"),
    CREDIT("CREDIT");

    private final String value;
}
