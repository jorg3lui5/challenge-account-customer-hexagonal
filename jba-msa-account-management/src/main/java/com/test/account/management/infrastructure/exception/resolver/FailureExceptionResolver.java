package com.test.account.management.infrastructure.exception.resolver;

import com.test.account.management.infrastructure.input.adapter.rest.bs.bean.ErrorModel;
import com.test.account.management.util.ErrorUtils;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

public class FailureExceptionResolver extends ErrorResolver<ErrorModel> {

    @Override
    protected int status() {
        return HttpStatus.CONFLICT.value();
    }

    @NonNull
    @Override
    protected ErrorModel buildError(@NonNull final String requestPath,
                                    @NonNull final Throwable throwable,
                                    @NonNull final String version) {
        final var exception = (FailureException) throwable;
        ErrorModel errorModel = exception.getFailure();
        return new ErrorModel()
                .title(errorModel.getTitle())
                .detail(errorModel.getDetail())
                .type(errorModel.getType())
                .instance(ErrorUtils.buildErrorCode(HttpStatus.CONFLICT.value()));
    }
}
