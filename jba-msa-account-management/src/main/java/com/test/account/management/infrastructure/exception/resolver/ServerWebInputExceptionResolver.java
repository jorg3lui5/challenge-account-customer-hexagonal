package com.test.account.management.infrastructure.exception.resolver;

import com.test.account.management.infrastructure.input.adapter.rest.bs.bean.ErrorList;
import com.test.account.management.infrastructure.input.adapter.rest.bs.bean.ErrorModel;
import com.test.account.management.util.ErrorUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.server.ServerWebInputException;

import java.util.List;

import static com.test.account.management.util.Constants.BAD_INPUT;

@Slf4j
public class ServerWebInputExceptionResolver  extends ErrorResolver<ErrorModel> {

    @Override
    protected int status() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @NonNull
    private List<ErrorList> getErrors(@NonNull final ServerWebInputException exception) {
        return List.of(
                new ErrorList()
                        .message("Bad request")
                        .businessMessage(exception.getCause().getMessage())
        );
    }

    @NonNull
    @Override
    protected ErrorModel buildError(@NonNull final String requestPath,
                                         @NonNull final Throwable throwable,
                                         @NonNull final String version) {
        final var exception = (ServerWebInputException) throwable;

        return new ErrorModel()
                .title(BAD_INPUT)
                .detail(exception.getReason())
                .errors(getErrors(exception))
                .instance(ErrorUtils.buildErrorCode(status()))
                .type(requestPath);
    }
}
