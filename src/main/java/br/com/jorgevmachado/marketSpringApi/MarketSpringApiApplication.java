package br.com.jorgevmachado.marketSpringApi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MarketSpringApiApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MarketSpringApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception { }
}
