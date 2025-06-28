package com.test.account.management.infrastructure.exception.resolver;

import com.test.account.management.infrastructure.input.adapter.rest.bs.bean.ErrorModel;
import com.test.account.management.util.ErrorUtils;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;

public class UnsupportedMediaTypeStatusExceptionResolver extends ErrorResolver<ErrorModel> {

    @Override
    protected int status() {
        return HttpStatus.UNSUPPORTED_MEDIA_TYPE.value();
    }

    @NonNull
    @Override
    protected ErrorModel buildError(@NonNull final String requestPath,
                                    @NonNull final Throwable throwable,
                                    @NonNull final String version) {
        final var exception = (UnsupportedMediaTypeStatusException) throwable;

        return new ErrorModel()
                .title("Unsupported media type")
                .detail(exception.getReason())
                .instance(ErrorUtils.buildErrorCode(status()))
                .type(requestPath);
    }
}
