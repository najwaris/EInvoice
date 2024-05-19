package com.moabi.demoeinvoice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DemoEInvoiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoEInvoiceApplication.class, args);
	}

}
