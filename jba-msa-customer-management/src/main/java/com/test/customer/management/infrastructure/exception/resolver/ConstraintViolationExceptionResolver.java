package com.test.customer.management.infrastructure.exception.resolver;

import com.test.customer.management.infrastructure.input.adapter.rest.bs.bean.ErrorList;
import com.test.customer.management.infrastructure.input.adapter.rest.bs.bean.ErrorModel;
import com.test.customer.management.util.ErrorUtils;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import java.util.List;

import static com.test.customer.management.util.Constants.BAD_INPUT;

public class ConstraintViolationExceptionResolver extends ErrorResolver<ErrorModel> {

    public String getField(Path fieldPath) {
        PathImpl path = (PathImpl) fieldPath;
        return path.getLeafNode().toString();
    }

    @Override
    protected int status() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @NonNull
    private List<ErrorList> getErrors(@NonNull final ConstraintViolationException exception) {
        return exception.getConstraintViolations()
                .stream()
                .map(constraintViolation -> new ErrorList()
                        .message("Bad request")
                        .businessMessage(
                                String.format("%s %s", getField(constraintViolation.getPropertyPath()),
                                        constraintViolation.getMessage())
                        )
                )
                .toList();
    }

    @NonNull
    @Override
    protected ErrorModel buildError(@NonNull final String requestPath,
                                    @NonNull final Throwable throwable,
                                    @NonNull final String version) {
        final var exception = (ConstraintViolationException) throwable;

        return new ErrorModel()
                .title(BAD_INPUT)
                .detail("The input data is invalid")
                .errors(getErrors(exception))
                .instance(ErrorUtils.buildErrorCode(status()))
                .type(requestPath);
    }
}
