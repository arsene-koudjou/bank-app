package com.example.hexagonal_bank;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class HexagonalBankApplication {

	private static final Logger log = LoggerFactory.getLogger(HexagonalBankApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(HexagonalBankApplication.class, args);
		log.info("** starting application****");

	}

}
