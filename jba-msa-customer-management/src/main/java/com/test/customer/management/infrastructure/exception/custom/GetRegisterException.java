package com.test.customer.management.infrastructure.exception.custom;

import com.test.customer.management.infrastructure.exception.resolver.FailureException;
import com.test.customer.management.infrastructure.input.adapter.rest.bs.bean.ErrorModel;
import org.springframework.http.HttpStatus;

import static com.test.customer.management.util.Constants.CONFLICT;

public class GetRegisterException extends FailureException {
    public GetRegisterException() {
        super(new ErrorModel()
                    .type(CONFLICT)
                    .title("Registro no recuperado")
                    .detail("Error al recuperar el registro")
                , HttpStatus.CONFLICT.value()
        );
    }
}
