package com.test.customer.management.infrastructure.exception.resolver;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
public class CodeException extends Exception {
    @SuppressWarnings("java:S1068")
    final int code;

    public CodeException(final int code, @NonNull final String message) {
        super(message);
        this.code = code;
    }
}
