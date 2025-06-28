package com.test.customer.management.infrastructure.exception.resolver;

import com.test.customer.management.infrastructure.input.adapter.rest.bs.bean.ErrorList;
import com.test.customer.management.infrastructure.input.adapter.rest.bs.bean.ErrorModel;
import com.test.customer.management.util.ErrorUtils;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.List;

import static com.test.customer.management.util.Constants.BAD_INPUT;

public class WebExchangeBindExceptionResolver extends ErrorResolver<ErrorModel> {
  @Override
  protected int status() {
    return HttpStatus.BAD_REQUEST.value();
  }

  @NonNull
  private List<ErrorList> getErrors(@NonNull final WebExchangeBindException exception) {
    return exception.getFieldErrors()
      .stream()
      .map(exceptionWebExchange -> new ErrorList()
              .message(String.format("Bad Request: %s", exceptionWebExchange.getField()))
              .businessMessage(
                      String.format("%s: %s", exceptionWebExchange.getField(),
                              exceptionWebExchange.getDefaultMessage())
              )
      )
      .toList();
  }

  @NonNull
  @Override
  protected ErrorModel buildError(@NonNull final String requestPath,
                                       @NonNull final Throwable throwable,
                                       @NonNull final String version) {
    final var exception = (WebExchangeBindException) throwable;

    return new ErrorModel()
            .title(BAD_INPUT)
            .detail(exception.getReason())
            .errors(getErrors(exception))
            .instance(ErrorUtils.buildErrorCode(status()))
            .type(requestPath);
  }
}
