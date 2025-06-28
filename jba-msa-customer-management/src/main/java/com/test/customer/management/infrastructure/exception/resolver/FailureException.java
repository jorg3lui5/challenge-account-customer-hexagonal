package com.test.customer.management.infrastructure.exception.resolver;

import com.test.customer.management.infrastructure.input.adapter.rest.bs.bean.ErrorModel;
import lombok.AccessLevel;
import lombok.Generated;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.NonNull;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Generated
public class FailureException extends RuntimeException {

    final ErrorModel failure;
    final Integer errorCode;

    public FailureException(@NonNull final ErrorModel failure, final int errorCode) {
        super(failure.getDetail());
        this.failure = failure;
        this.errorCode = errorCode;
    }
}