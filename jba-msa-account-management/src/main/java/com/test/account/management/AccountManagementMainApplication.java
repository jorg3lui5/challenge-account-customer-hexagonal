package com.test.account.management;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.test.account.management",
        "com.test.common"}
)
@Slf4j
public class AccountManagementMainApplication {

  public static void main(String[] args) {
        SpringApplication.run(AccountManagementMainApplication.class, args);
    }

}