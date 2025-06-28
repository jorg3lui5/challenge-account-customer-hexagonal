package com.test.customer.management.infrastructure.exception.resolver;

import com.test.customer.management.infrastructure.input.adapter.rest.bs.bean.ErrorModel;
import com.test.customer.management.util.ErrorUtils;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import static com.test.customer.management.util.Constants.CLIENT_ERROR;

public class WebClientRequestExceptionResolver extends ErrorResolver<ErrorModel> {
    @Override
    protected int status() {
        return HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    @NonNull
    @Override
    protected ErrorModel buildError(@NonNull final String requestPath,
                               @NonNull final Throwable throwable,
                               @NonNull final String version) {
        final var exception = (WebClientRequestException) throwable;
        return new ErrorModel()
                .title(CLIENT_ERROR)
                .detail("Connection refused of client path=" + exception.getUri().getPath())
                .instance(ErrorUtils.buildErrorCode(status()))
                .type(requestPath);
    }
}
