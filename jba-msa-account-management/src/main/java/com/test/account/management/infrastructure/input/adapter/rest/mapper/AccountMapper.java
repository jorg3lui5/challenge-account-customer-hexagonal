package com.test.account.management.infrastructure.input.adapter.rest.mapper;

import com.test.account.management.infrastructure.input.adapter.rest.bs.bean.GetAccountResponse;
import com.test.account.management.infrastructure.input.adapter.rest.bs.bean.PatchAccountRequest;
import com.test.account.management.infrastructure.input.adapter.rest.bs.bean.PostAccountRequest;
import com.test.account.management.infrastructure.input.adapter.rest.bs.bean.PutAccountRequest;
import com.test.account.management.domain.Account;
import org.mapstruct.*;

@Mapper(
  componentModel = "spring",
  nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
  builder = @Builder(disableBuilder = true)
)
public interface AccountMapper {

  @Mapping(target = "accountId", source = "accountId")
  @Mapping(target = "number", source = "number")
  @Mapping(target = "type", source = "type")
  @Mapping(target = "initialBalance", source = "initialBalance")
  @Mapping(target = "status", source = "status")
  @Mapping(target = "customer", source = "customer")
  GetAccountResponse toAccountResponse(Account request);

  @Mapping(target = "accountId", ignore = true)
  @Mapping(target = "number", source = "number")
  @Mapping(target = "type", source = "type")
  @Mapping(target = "initialBalance", source = "initialBalance")
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "customer.customerId", source = "customerId")
  Account toAccount(PostAccountRequest request);

  @Mapping(target = "accountId", source = "accountId")
  @Mapping(target = "number", source = "number")
  @Mapping(target = "type", source = "type")
  @Mapping(target = "initialBalance", source = "initialBalance")
  @Mapping(target = "status", source = "status")
  @Mapping(target = "customer.customerId", source = "customerId")
  Account toAccount(PutAccountRequest request);

  @Mapping(target = "accountId", source = "accountId")
  @Mapping(target = "number", source = "number")
  @Mapping(target = "type", source = "type")
  @Mapping(target = "initialBalance", source = "initialBalance")
  @Mapping(target = "status", source = "status")
  @Mapping(target = "customer", ignore = true)
  Account toAccount(PatchAccountRequest request);

}
