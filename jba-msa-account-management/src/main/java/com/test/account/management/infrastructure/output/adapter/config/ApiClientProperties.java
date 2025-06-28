package com.test.account.management.infrastructure.output.adapter.config;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Getter
@Generated
@Configuration
@ConfigurationProperties(prefix = "clients.http-client.api")
public class ApiClientProperties {

    @NotNull
    @Valid
    private HttpApiClient clientJbaCustomerManagement;


    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Validated
    public static class HttpApiClient {
        @NotBlank
        @Size(max = 255)
        private String baseUrl;
    }

}
