package com.test.account.management.infrastructure.output.adapter.repository.mapper;

import com.test.account.management.domain.Account;
import com.test.account.management.domain.Customer;
import com.test.account.management.infrastructure.output.adapter.repository.entity.AccountEntity;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface AccountRepositoryMapper {

    @Mapping(target = "accountId", source = "accountId")
    @Mapping(target = "number", source = "number")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "initialBalance", source = "initialBalance")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "customer.customerId", source = "customerId")
    Account entityToAccount(AccountEntity entity);

    @Mapping(target = "accountId", ignore = true)
    @Mapping(target = "number", source = "number")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "initialBalance", source = "initialBalance")
    @Mapping(target = "status", expression = "java(Boolean.TRUE)")
    @Mapping(target = "customerId", source = "customer.customerId")
    AccountEntity postAccountToEntity(Account request);

    @Mapping(target = "accountId", source = "accountId")
    @Mapping(target = "number", source = "number")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "initialBalance", source = "initialBalance")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "customerId", source = "customer.customerId")
    AccountEntity putAccountToEntity(Account request);

    @Mapping(target = "accountId", ignore = true)
    @Mapping(target = "number", source = "number")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "initialBalance", source = "initialBalance")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "customerId", ignore = true)
    AccountEntity patchAccountToEntity(@MappingTarget AccountEntity entity, Account request);

}
