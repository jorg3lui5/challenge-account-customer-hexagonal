package com.test.customer.management;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.test.customer.management",
        "com.test.common"}
)
@Slf4j
public class CustomerManagementMainApplication {

  public static void main(String[] args) {
        SpringApplication.run(CustomerManagementMainApplication.class, args);
    }

}