package com.test.customer.management.infrastructure.input.adapter.rest.mapper;

import com.test.customer.management.domain.Customer;
import com.test.customer.management.infrastructure.input.adapter.rest.bs.bean.GetCustomerResponse;
import com.test.customer.management.infrastructure.input.adapter.rest.bs.bean.PatchCustomerRequest;
import com.test.customer.management.infrastructure.input.adapter.rest.bs.bean.PostCustomerRequest;
import com.test.customer.management.infrastructure.input.adapter.rest.bs.bean.PutCustomerRequest;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
  componentModel = "spring",
  nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
  builder = @Builder(disableBuilder = true)
)
public interface CustomerMapper {

  GetCustomerResponse toCustomerResponse(Customer request);

  Customer toCustomer(PostCustomerRequest request);

  Customer toCustomer(PutCustomerRequest request);

  Customer toCustomer(PatchCustomerRequest request);

}
