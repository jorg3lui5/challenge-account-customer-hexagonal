package com.test.account.management.util;

import org.springframework.lang.NonNull;

import java.util.stream.Stream;

public final class ErrorUtils {

    private ErrorUtils(){}

    @NonNull
    public static String buildErrorCode(final int status) {
        return String.format("BS-%d", status);
    }

    @NonNull
    public static String getExceptionOrigin(final Throwable throwable) {
      return Stream.of(throwable.getStackTrace())
          .findFirst()
          .map(origin -> String.format(
              "Class: %s, Method: %s, Line: %d",
              origin.getClassName(), origin.getMethodName(), origin.getLineNumber()))
          .orElse("The origin of the exception could not be determined.");
    }
   
}
