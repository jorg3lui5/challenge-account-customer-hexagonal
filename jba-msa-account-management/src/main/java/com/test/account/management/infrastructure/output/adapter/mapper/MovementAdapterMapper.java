package com.test.account.management.infrastructure.output.adapter.mapper;

import com.test.account.management.domain.Account;
import com.test.account.management.domain.Movement;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface MovementAdapterMapper {

    @Mapping(target = "movementId", source = "movement.movementId")
    @Mapping(target = "date", source = "movement.date")
    @Mapping(target = "type", source = "movement.type")
    @Mapping(target = "value", source = "movement.value")
    @Mapping(target = "balance", source = "movement.balance")
    @Mapping(target = "status", source = "movement.status")
    @Mapping(target = "account", source = "account")
    Movement movementAndAccountToMovement(Movement movement, Account account);

}
