package employees;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@SpringBootApplication
public class EmployeesRestclientApplication {


	public static void main(String[] args) {
		SpringApplication.run(EmployeesRestclientApplication.class, args);
	}

	@Bean
	public WebClient webClient(WebClient.Builder builder) {
		return builder.baseUrl("http://localhost:8080").build();
	}

	@Bean
	public EmployeeClient employeeClient(WebClient webClient) {
		var factory = HttpServiceProxyFactory.builderFor(WebClientAdapter.create(webClient)).build();
		return factory.createClient(EmployeeClient.class);
	}

}
