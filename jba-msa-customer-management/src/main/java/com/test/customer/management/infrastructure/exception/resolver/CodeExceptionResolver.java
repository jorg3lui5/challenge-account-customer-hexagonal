package com.test.customer.management.infrastructure.exception.resolver;

import com.test.customer.management.infrastructure.input.adapter.rest.bs.bean.ErrorModel;
import com.test.customer.management.util.ErrorUtils;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

import static com.test.customer.management.util.Constants.UNEXPECTED_ERROR;


public class CodeExceptionResolver extends ErrorResolver<ErrorModel> {

    @Override
    protected int status() {
        return HttpStatus.NOT_FOUND.value();
    }

    @NonNull
    @Override
    protected ErrorModel buildError(@NonNull final String requestPath, @NonNull final Throwable throwable, @NonNull final String version) {
        final var exception = (CodeException) throwable;
        return new ErrorModel()
                .title(UNEXPECTED_ERROR)
                .detail(exception.getMessage())
                .instance(ErrorUtils.buildErrorCode(exception.getCode()))
                .type(requestPath);
    }
}
