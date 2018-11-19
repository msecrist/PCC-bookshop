package io.pivotal.bookshop;

import io.pivotal.bookshop.dao.CustomerRepository;
import io.pivotal.bookshop.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class CustomerWebApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {

		return application.sources(CustomerWebApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(CustomerWebApplication.class, args);
	}

	@Component
	public class MyCommandlineRunner implements CommandLineRunner {
		@Autowired
		CustomerRepository repo;

		@Override
		public void run(String... args) throws Exception {
			repo.save(new Customer(1001, "Hatty", "Carsberg", "34102"));
			repo.save(new Customer(1002, "Conrado", "Carvill", "76129"));
			repo.save(new Customer(1003, "Jasun", "Barwood", "66606"));

		}
	}

}
