package com.test.account.management.infrastructure.exception.custom;

import com.test.account.management.infrastructure.exception.resolver.FailureException;
import com.test.account.management.infrastructure.input.adapter.rest.bs.bean.ErrorModel;
import org.springframework.http.HttpStatus;

import static com.test.account.management.util.Constants.CONFLICT;

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
