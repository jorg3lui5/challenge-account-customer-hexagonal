package com.test.account.management.infrastructure.output.adapter.mapper;

import com.test.account.management.domain.Account;
import com.test.account.management.domain.Customer;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface AccountAdapterMapper {

    @Mapping(target = "accountId", source = "account.accountId")
    @Mapping(target = "number", source = "account.number")
    @Mapping(target = "type", source = "account.type")
    @Mapping(target = "initialBalance", source = "account.initialBalance")
    @Mapping(target = "status", source = "account.status")
    @Mapping(target = "customer", source = "customer")
    Account accountAndCustomerToAccount(Account account, Customer customer);

}
