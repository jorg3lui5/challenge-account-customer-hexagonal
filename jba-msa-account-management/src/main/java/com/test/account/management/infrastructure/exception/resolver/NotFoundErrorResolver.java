package com.test.account.management.infrastructure.exception.resolver;

import com.test.account.management.infrastructure.input.adapter.rest.bs.bean.ErrorModel;
import com.test.account.management.util.ErrorUtils;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

public class NotFoundErrorResolver extends ErrorResolver<ErrorModel> {

    @Override
    protected int status() {
        return HttpStatus.NOT_FOUND.value();
    }

    @NonNull
    @Override
    protected ErrorModel buildError(@NonNull final String requestPath,
                                    @NonNull final Throwable throwable,
                                    @NonNull final String version) {
        return new ErrorModel()
                .title("Not found")
                .detail(throwable.getMessage())
                .instance(ErrorUtils.buildErrorCode(status()))
                .type(requestPath);
    }
}

