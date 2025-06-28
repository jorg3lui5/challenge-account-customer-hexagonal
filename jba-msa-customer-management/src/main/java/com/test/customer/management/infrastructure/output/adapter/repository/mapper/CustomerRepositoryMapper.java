package com.test.customer.management.infrastructure.output.adapter.repository.mapper;

import com.test.customer.management.domain.Customer;
import com.test.customer.management.infrastructure.output.adapter.repository.entity.CustomerDataEntity;
import com.test.customer.management.infrastructure.output.adapter.repository.entity.CustomerEntity;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface CustomerRepositoryMapper {

    @Mapping(target = "customerId", source = "customerId")
    @Mapping(target = "identification", source = "identification")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "gender", source = "gender")
    @Mapping(target = "age", source = "age")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "status", source = "status")
    Customer entityDataToCustomer(CustomerDataEntity entity);

    @Mapping(target = "customerId", ignore = true)
    @Mapping(target = "password", source = "request.password")
    @Mapping(target = "status", expression = "java(Boolean.TRUE)")
    @Mapping(target = "personId", source = "personId")
    CustomerEntity postCustomerToEntity(Customer request, Long personId);

    @Mapping(target = "customerId", source = "request.customerId")
    @Mapping(target = "password", source = "request.password")
    @Mapping(target = "status", source = "request.status")
    @Mapping(target = "personId", source = "personId")
    CustomerEntity putCustomerToEntity(Customer request, Long personId);

    @Mapping(target = "customerId",ignore = true)
    @Mapping(target = "password", source = "password")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "personId", ignore = true)
    CustomerEntity patchCustomerToEntity(@MappingTarget CustomerEntity entity, Customer request);
}
