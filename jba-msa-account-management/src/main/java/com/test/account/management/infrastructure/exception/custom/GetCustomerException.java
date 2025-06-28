package com.test.account.management.infrastructure.exception.custom;

import com.test.account.management.infrastructure.exception.resolver.FailureException;
import com.test.account.management.infrastructure.input.adapter.rest.bs.bean.ErrorModel;
import org.springframework.http.HttpStatus;

import static com.test.account.management.util.Constants.CONFLICT;

public class GetCustomerException extends FailureException {
    public GetCustomerException() {
        super(new ErrorModel()
                    .type(CONFLICT)
                    .title("Registro no recuperado")
                    .detail("Error al recuperar el cliente de la cuenta.")
                , HttpStatus.CONFLICT.value()
        );
    }
}
