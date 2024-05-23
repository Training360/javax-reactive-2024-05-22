package employees;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@AllArgsConstructor
@Slf4j
public class ClientRunner implements CommandLineRunner  {

    private WebClient webClient;

    @Override
    public void run(String... args) throws Exception {
        log.info("hello");

        var json = webClient.get().uri("/api/employees").exchangeToMono(response -> response.bodyToMono(String.class)).block();
        log.info(json);

        var employees = webClient.get().uri("/api/employees").exchangeToFlux(clientResponse -> clientResponse.bodyToFlux(Employee.class)).collectList().block();
        employees.forEach(employee -> log.info("employee: {}", employee));
    }

    public record Employee(Long id, String name) {}

}
