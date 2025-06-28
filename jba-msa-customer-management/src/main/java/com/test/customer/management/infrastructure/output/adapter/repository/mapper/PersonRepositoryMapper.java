package com.test.customer.management.infrastructure.output.adapter.repository.mapper;

import com.test.customer.management.domain.Customer;
import com.test.customer.management.infrastructure.output.adapter.repository.entity.PersonEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true))
public interface PersonRepositoryMapper {

    @Mapping(target = "personId", ignore = true)
    @Mapping(target = "identification", source = "identification")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "gender", source = "gender")
    @Mapping(target = "age", source = "age")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "phone", source = "phone")
    PersonEntity customerToEntity(Customer request);

    @Mapping(target = "personId", source = "personId")
    @Mapping(target = "identification", source = "request.identification")
    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "gender", source = "request.gender")
    @Mapping(target = "age", source = "request.age")
    @Mapping(target = "address", source = "request.address")
    @Mapping(target = "phone", source = "request.phone")
    PersonEntity putRequestToEntity(Customer request, Long personId);

    @Mapping(target = "personId", ignore = true)
    @Mapping(target = "identification", source = "identification")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "gender", source = "gender")
    @Mapping(target = "age", source = "age")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "phone", source = "phone")
    PersonEntity patchRequestToEntity(@MappingTarget PersonEntity entity, Customer request);
}
