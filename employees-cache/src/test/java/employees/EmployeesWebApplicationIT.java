package employees;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class EmployeesWebApplicationIT {

	@Autowired
	WebTestClient webTestClient;

	@Container
	@ServiceConnection
	static PostgreSQLContainer<?> postgresql = new PostgreSQLContainer<>("postgres");

	@Test
	void contextLoads() {
	}

	@Test
	void create() {
		webTestClient
				.post()
				.uri("/api/employees")
				.contentType(MediaType.APPLICATION_JSON)
//				.bodyValue("""
//						{"name": "John Doe"}
//						""")
				.bodyValue(new EmployeeDto(null, "John Doe"))
				.exchange()
				.expectStatus().isCreated()
//				.expectBody().jsonPath("name").isEqualTo("John Doe")
				.expectBody(EmployeeDto.class)
					.value(dto -> assertThat(dto.name()).isEqualTo("John Doe"))
 					.value(dto -> assertThat(dto.id()).isNotNull());
	}

}
