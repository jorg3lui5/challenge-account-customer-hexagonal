package com.test.account.management.infrastructure.exception.custom;

import com.test.account.management.infrastructure.exception.resolver.FailureException;
import com.test.account.management.infrastructure.input.adapter.rest.bs.bean.ErrorModel;
import org.springframework.http.HttpStatus;

import static com.test.account.management.util.Constants.CONFLICT;

public class UpdateException extends FailureException {
    public UpdateException() {
        super(new ErrorModel()
                    .type(CONFLICT)
                    .title("Registro no actualizado")
                    .detail("Error al actualizar el registro, verifique los datos")
                , HttpStatus.CONFLICT.value()
        );
    }
}
