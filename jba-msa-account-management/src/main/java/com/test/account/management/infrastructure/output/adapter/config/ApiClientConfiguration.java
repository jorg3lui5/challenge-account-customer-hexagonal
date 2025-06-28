package com.test.account.management.infrastructure.output.adapter.config;

import com.test.account.management.infrastructure.output.adapter.rest.jbacustomermanagement.client.CustomerApi;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

@Configuration
@RequiredArgsConstructor
public class ApiClientConfiguration {
  private final ApiClientProperties apiClientProperties;

  @NonNull
  @Bean
  CustomerApi getCustomerApi() {
    final var api = new CustomerApi();
    api.getApiClient().setBasePath(apiClientProperties.getClientJbaCustomerManagement().getBaseUrl());
    return api;
  }

}
