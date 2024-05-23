package employees;

import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import reactor.core.publisher.Flux;

@HttpExchange("/api/employees")
public interface EmployeeClient {

    @GetExchange
    Flux<Employee> findAll();
}
