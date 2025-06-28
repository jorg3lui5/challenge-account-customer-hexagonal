package com.test.account.management.infrastructure.exception.custom;

import com.test.account.management.infrastructure.exception.resolver.FailureException;
import com.test.account.management.infrastructure.input.adapter.rest.bs.bean.ErrorModel;
import org.springframework.http.HttpStatus;

import static com.test.account.management.util.Constants.CONFLICT;

public class AccountByIdNotFoundException extends FailureException {
    public AccountByIdNotFoundException() {
        super(new ErrorModel()
                    .type(CONFLICT)
                    .title("Registro no encontrado")
                    .detail("Cuenta no encontrada, verifique el Id")
                , HttpStatus.CONFLICT.value()
        );
    }
}
