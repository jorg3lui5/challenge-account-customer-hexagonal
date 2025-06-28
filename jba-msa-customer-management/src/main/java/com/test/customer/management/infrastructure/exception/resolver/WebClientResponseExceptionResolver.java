package com.test.customer.management.infrastructure.exception.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.customer.management.infrastructure.input.adapter.rest.bs.bean.ErrorModel;
import com.test.customer.management.util.ErrorUtils;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import static com.test.customer.management.util.Constants.UNEXPECTED_ERROR;

public class WebClientResponseExceptionResolver extends ErrorResolver<ErrorModel> {
    @Override
    protected int status() {
        return HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    @NonNull
    @Override
    protected ErrorModel buildError(@NonNull final String requestPath,
                               @NonNull final Throwable throwable,
                               @NonNull final String version) {
        final var exception = (WebClientResponseException) throwable;
        try {
            return new ObjectMapper().readValue(exception.getResponseBodyAsByteArray(), ErrorModel.class);
        } catch (final Exception e) {
            return new ErrorModel()
                    .title(UNEXPECTED_ERROR)
                    .detail("The service failed to serve the request due to an internal error")
                    .instance(ErrorUtils.buildErrorCode(status()))
                    .type(requestPath);
        }
    }
}
