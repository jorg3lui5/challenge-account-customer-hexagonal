package com.test.account.management.infrastructure.exception.custom;

import com.test.account.management.infrastructure.exception.resolver.FailureException;
import com.test.account.management.infrastructure.input.adapter.rest.bs.bean.ErrorModel;
import org.springframework.http.HttpStatus;

import static com.test.account.management.util.Constants.CONFLICT;

public class InvalidDebitAmountException extends FailureException {
    public InvalidDebitAmountException() {
        super(new ErrorModel()
                    .type(CONFLICT)
                    .title("Saldo no disponible")
                    .detail("El valor que se desea debitar es mayor al saldo actual")
                , HttpStatus.CONFLICT.value()
        );
    }
}
