package com.test.account.management.infrastructure.input.adapter.rest.mapper;

import com.test.account.management.infrastructure.input.adapter.rest.bs.bean.GetMovementResponse;
import com.test.account.management.infrastructure.input.adapter.rest.bs.bean.PatchMovementRequest;
import com.test.account.management.infrastructure.input.adapter.rest.bs.bean.PostMovementRequest;
import com.test.account.management.infrastructure.input.adapter.rest.bs.bean.PutMovementRequest;
import com.test.account.management.domain.Movement;
import org.mapstruct.*;

import java.math.BigDecimal;

@Mapper(
  componentModel = "spring",
  nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
  builder = @Builder(disableBuilder = true)
)
public interface MovementMapper {

  @Mapping(target = "movementId", source = "movementId")
  @Mapping(target = "date", source = "date")
  @Mapping(target = "type", source = "type")
  @Mapping(target = "value", source = "value")
  @Mapping(target = "balance", source = "balance")
  @Mapping(target = "status", source = "status")
  @Mapping(target = "account", source = "account")
  GetMovementResponse toMovementResponse(Movement request);

  @Mapping(target = "movementId", ignore = true)
  @Mapping(target = "date", source = "date")
  @Mapping(target = "type", source = "type")
  @Mapping(target = "value", source = "value")
  @Mapping(target = "balance", ignore = true)
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "account.accountId", source = "accountId")
  Movement toMovement(PostMovementRequest request);

  @Mapping(target = "movementId", source = "movementId")
  @Mapping(target = "date", source = "date")
  @Mapping(target = "type", source = "type")
  @Mapping(target = "value", source = "value")
  @Mapping(target = "balance", ignore = true)
  @Mapping(target = "status", source = "status")
  @Mapping(target = "account", ignore = true)
  Movement toMovement(PutMovementRequest request);

  @Mapping(target = "movementId", source = "movementId")
  @Mapping(target = "date", source = "date")
  @Mapping(target = "type", source = "type")
  @Mapping(target = "value", source = "value")
  @Mapping(target = "balance", ignore = true)
  @Mapping(target = "status", source = "status")
  @Mapping(target = "account", ignore = true)
  Movement toMovement(PatchMovementRequest request);

}
