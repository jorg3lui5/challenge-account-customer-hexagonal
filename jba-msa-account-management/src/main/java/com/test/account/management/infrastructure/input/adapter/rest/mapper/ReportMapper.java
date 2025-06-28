package com.test.account.management.infrastructure.input.adapter.rest.mapper;

import com.test.account.management.domain.Account;
import com.test.account.management.domain.Customer;
import com.test.account.management.domain.Movement;
import com.test.account.management.infrastructure.input.adapter.rest.bs.bean.AccountReportByCustomerResponse;
import com.test.account.management.domain.AccountMovementReport;
import com.test.account.management.infrastructure.input.adapter.rest.bs.bean.MovementType;
import org.mapstruct.*;

@Mapper(
  componentModel = "spring",
  nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
  builder = @Builder(disableBuilder = true)
)
public interface ReportMapper {

  AccountReportByCustomerResponse toAccountReportByCustomer(AccountMovementReport request);

  @Mapping(target = "date", source = "movement.date")
  @Mapping(target = "customer", source = "customer.name")
  @Mapping(target = "accountNumber", source = "account.number")
  @Mapping(target = "type", source = "account.type")
  @Mapping(target = "initialBalance", source = "movement.balance")
  @Mapping(target = "status", source = "movement.status")
  @Mapping(target = "movement", source = "movement", qualifiedByName = "getAmountMovement")
  @Mapping(target = "availableBalance", source = "movement", qualifiedByName = "getAvailableBalance")
  AccountMovementReport toAccountMovementReport(Movement movement, Account account, Customer customer);

  @Named("getAmountMovement")
  static String getAmountMovement(Movement movement) {
    return movement.getType().value().equals(MovementType.DEBIT.getValue())
            ? "-" + movement.getValue()
            : String.valueOf(movement.getValue());
  }

  @Named("getAvailableBalance")
  static Double getAvailableBalance(Movement movement) {
    double balance = movement.getBalance();
    double amount = movement.getValue();

    return movement.getType().value().equals(MovementType.DEBIT.getValue())
            ? balance - amount
            : balance + amount;
  }
}
