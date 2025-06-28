package com.test.account.management.infrastructure.exception.custom;

import com.test.account.management.infrastructure.exception.resolver.FailureException;
import com.test.account.management.infrastructure.input.adapter.rest.bs.bean.ErrorModel;
import org.springframework.http.HttpStatus;

import static com.test.account.management.util.Constants.CONFLICT;

public class AccountNotUpdateByExistingMovementsException extends FailureException {
    public AccountNotUpdateByExistingMovementsException() {
        super(new ErrorModel()
                    .type(CONFLICT)
                    .title("Registro no actualizado")
                    .detail("No se puede modificar la cuenta porque existen movimientos ya realizados")
                , HttpStatus.CONFLICT.value()
        );
    }
}
