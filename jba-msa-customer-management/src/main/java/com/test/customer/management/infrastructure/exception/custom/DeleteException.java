package com.test.customer.management.infrastructure.exception.custom;

import com.test.customer.management.infrastructure.exception.resolver.FailureException;
import com.test.customer.management.infrastructure.input.adapter.rest.bs.bean.ErrorModel;
import org.springframework.http.HttpStatus;

import static com.test.customer.management.util.Constants.CONFLICT;

public class DeleteException extends FailureException {
    public DeleteException() {
        super(new ErrorModel()
                    .type(CONFLICT)
                    .title("Registro no eliminado")
                    .detail("Error al eliminar el registro, verifique el Id")
                , HttpStatus.CONFLICT.value()
        );
    }
}
