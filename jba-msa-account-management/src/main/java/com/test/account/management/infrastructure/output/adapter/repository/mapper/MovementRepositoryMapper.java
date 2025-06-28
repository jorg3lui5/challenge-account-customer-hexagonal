package com.test.account.management.infrastructure.output.adapter.repository.mapper;

import com.test.account.management.domain.Account;
import com.test.account.management.domain.Movement;
import com.test.account.management.infrastructure.output.adapter.repository.entity.AccountEntity;
import com.test.account.management.infrastructure.output.adapter.repository.entity.MovementEntity;
import org.mapstruct.*;

import java.math.BigDecimal;

@Mapper(
        componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface MovementRepositoryMapper {

    @Mapping(target = "movementId", source = "movementId")
    @Mapping(target = "date", source = "date")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "value", source = "value")
    @Mapping(target = "balance", source = "balance")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "account.accountId", source = "accountId")
    Movement entityToMovement(MovementEntity entity);

    @Mapping(target = "movementId", ignore = true)
    @Mapping(target = "date", source = "date")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "value", source = "value")
    @Mapping(target = "balance", source = "balance")
    @Mapping(target = "status", expression = "java(Boolean.TRUE)")
    @Mapping(target = "accountId", source = "account.accountId")
    MovementEntity postMovementToEntity(Movement request);

    @Mapping(target = "movementId", source = "movementId")
    @Mapping(target = "date", source = "date")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "value", source = "value")
    @Mapping(target = "balance", source = "balance")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "accountId", source = "account.accountId")
    MovementEntity putMovementToEntity(Movement request);

    @Mapping(target = "movementId", ignore = true)
    @Mapping(target = "date", source = "date")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "value", source = "value")
    @Mapping(target = "balance", source = "balance")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "accountId", ignore = true)
    MovementEntity patchMovementToEntity(@MappingTarget MovementEntity entity, Movement request);
}
