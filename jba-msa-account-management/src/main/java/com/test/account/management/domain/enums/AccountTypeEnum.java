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
public enum AccountTypeEnum {
    SAVINGS("SAVINGS"),

    CURRENT("CURRENT");

    private final String value;
}
