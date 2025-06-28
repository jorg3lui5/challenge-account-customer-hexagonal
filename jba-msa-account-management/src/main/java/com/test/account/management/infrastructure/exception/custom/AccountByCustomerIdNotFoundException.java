package com.test.account.management.infrastructure.exception.custom;

import com.test.account.management.infrastructure.exception.resolver.FailureException;
import com.test.account.management.infrastructure.input.adapter.rest.bs.bean.ErrorModel;
import org.springframework.http.HttpStatus;

import static com.test.account.management.util.Constants.CONFLICT;

public class AccountByCustomerIdNotFoundException extends FailureException {
    public AccountByCustomerIdNotFoundException() {
        super(new ErrorModel()
                    .type(CONFLICT)
                    .title("Registro no encontrado")
                    .detail("Cuenta no encontrada por cliente, verifique el Id del cliente")
                , HttpStatus.CONFLICT.value()
        );
    }
}
