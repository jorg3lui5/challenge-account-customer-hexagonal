package com.test.customer.management.infrastructure.exception.resolver;

import com.test.customer.management.infrastructure.input.adapter.rest.bs.bean.ErrorModel;
import com.test.customer.management.util.ErrorUtils;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

public class AccessDeniedExceptionResolver extends ErrorResolver<ErrorModel> {
    @Override
    protected int status() {
        return HttpStatus.FORBIDDEN.value();
    }

    @NonNull
    @Override
    protected ErrorModel buildError(@NonNull final String requestPath,
                               @NonNull final Throwable throwable,
                               @NonNull final String version) {
        return new ErrorModel()
                .title("Access denied")
                .detail(throwable.getMessage())
                .instance(ErrorUtils.buildErrorCode(status()))
                .type(requestPath);
    }

}
