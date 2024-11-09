package com.spring.xmltomysql;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.spring.xmltomysql")
@EnableBatchProcessing

public class XmlToMysqlDatabaseSpringBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(XmlToMysqlDatabaseSpringBatchApplication.class, args);
	}

}
