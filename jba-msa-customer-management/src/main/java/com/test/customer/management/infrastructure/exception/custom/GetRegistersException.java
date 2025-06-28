package com.test.customer.management.infrastructure.exception.custom;

import com.test.customer.management.infrastructure.exception.resolver.FailureException;
import com.test.customer.management.infrastructure.input.adapter.rest.bs.bean.ErrorModel;
import org.springframework.http.HttpStatus;

import static com.test.customer.management.util.Constants.CONFLICT;

public class GetRegistersException extends FailureException {
    public GetRegistersException() {
        super(new ErrorModel()
                    .type(CONFLICT)
                    .title("Registros no recuperados")
                    .detail("Error al recuperar los registros")
                , HttpStatus.CONFLICT.value()
        );
    }
}
