package com.test.account.management.infrastructure.exception.custom;

import com.test.account.management.infrastructure.exception.resolver.FailureException;
import com.test.account.management.infrastructure.input.adapter.rest.bs.bean.ErrorModel;
import org.springframework.http.HttpStatus;

import static com.test.account.management.util.Constants.CONFLICT;

public class MovementByIdNotFoundException extends FailureException {
    public MovementByIdNotFoundException() {
        super(new ErrorModel()
                    .type(CONFLICT)
                    .title("Registro no encontrado")
                    .detail("Movimiento no encontrado, verifique el Id")
                , HttpStatus.CONFLICT.value()
        );
    }
}
