package com.test.account.management.infrastructure.exception.resolver;

import com.test.account.management.infrastructure.input.adapter.rest.bs.bean.ErrorList;
import com.test.account.management.infrastructure.input.adapter.rest.bs.bean.ErrorModel;
import com.test.account.management.util.ErrorUtils;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.server.MissingRequestValueException;

import java.util.List;

public class MissingRequestValueExceptionResolver extends ErrorResolver<ErrorModel> {
    @Override
    protected int status() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @NonNull
    private List<ErrorList> getErrors(@NonNull final MissingRequestValueException exception) {
        return List.of(
                new ErrorList()
                        .message(String.format("Bad Request: %s", exception.getName()))
                        .businessMessage(exception.getReason())
        );
    }

    @Override
    protected ErrorModel buildError(@NonNull String requestPath,
                                         @NonNull Throwable throwable,
                                         @NonNull String version) {
        final var exception = (MissingRequestValueException) throwable;
        final var status = exception.getStatusCode();
        return new ErrorModel()
                .title("Missing Input")
                .detail("The input data is missing")
                .errors(getErrors(exception))
                .instance(ErrorUtils.buildErrorCode(status.value()))
                .type(requestPath);
    }
}
