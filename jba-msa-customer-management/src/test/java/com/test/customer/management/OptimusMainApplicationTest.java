package com.test.customer.management;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ConfigurableApplicationContext;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class CustomerManagementMainApplicationTest {
    @MockBean
    private ConfigurableApplicationContext configurableApplicationContext;

    @Test
    void shouldReturnCustomerManagementMainApplicationWhenCreatingInstanceOfCustomerManagementMainApplication() {
        assertDoesNotThrow(CustomerManagementMainApplication::new);
    }

    @Test
    void givenApplicationArgumentsWhenRunApplicationThenApplicationExecutionIsVerified() {
        try (final var mockedSpringApplication = mockStatic(SpringApplication.class)) {
            final var applicationArguments = new String[] {};
            mockedSpringApplication.when(() -> SpringApplication.run(CustomerManagementMainApplication.class, applicationArguments))
                    .thenReturn(configurableApplicationContext);
            CustomerManagementMainApplication.main(applicationArguments);
            mockedSpringApplication.verify(() -> SpringApplication.run(CustomerManagementMainApplication.class, applicationArguments));
        }
    }
}
