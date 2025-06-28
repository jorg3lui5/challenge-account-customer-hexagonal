package com.test.account.management.infrastructure.exception.custom;

import com.test.account.management.infrastructure.exception.resolver.FailureException;
import com.test.account.management.infrastructure.input.adapter.rest.bs.bean.ErrorModel;
import org.springframework.http.HttpStatus;

import static com.test.account.management.util.Constants.CONFLICT;

public class MethodNotImplementException extends FailureException {
    public MethodNotImplementException() {
        super(new ErrorModel()
                    .type(CONFLICT)
                    .title("Método no implementado")
                    .detail("Método aún no implementado, lo implementaremos pronto")
                , HttpStatus.CONFLICT.value()
        );
    }
}
