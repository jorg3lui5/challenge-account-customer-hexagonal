package com.test.account.management.infrastructure.exception.custom;

import com.test.account.management.infrastructure.exception.resolver.FailureException;
import com.test.account.management.infrastructure.input.adapter.rest.bs.bean.ErrorModel;
import org.springframework.http.HttpStatus;

import static com.test.account.management.util.Constants.CONFLICT;

public class InvalidDateMovement extends FailureException {
    public InvalidDateMovement() {
        super(new ErrorModel()
                    .type(CONFLICT)
                    .title("Invalid date movement")
                    .detail("La fecha del movimiento debe ser mayor a la fecha del último movimiento")
                , HttpStatus.CONFLICT.value()
        );
    }
}
