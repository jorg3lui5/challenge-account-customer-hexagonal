package com.test.account.management.infrastructure.exception.resolver;

import com.test.account.management.infrastructure.input.adapter.rest.bs.bean.ErrorModel;
import com.test.account.management.util.ErrorUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

import static com.test.account.management.util.Constants.UNEXPECTED_ERROR;

@Slf4j
public class UnexpectedErrorResolver extends ErrorResolver<ErrorModel> {

    @Override
    protected int status() {
        return HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    @NonNull
    @Override
    protected ErrorModel buildError(@NonNull final String requestPath,
                                    @NonNull final Throwable throwable,
                                    @NonNull final String version) {

        return new ErrorModel()
                .title(UNEXPECTED_ERROR)
                .detail("An unexpected error has occurred")
                .instance(ErrorUtils.buildErrorCode(status()))
                .type(requestPath);
    }
}
