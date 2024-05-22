package employees;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class EmployeeRepository {

    private final List<Employee> employees = Collections.synchronizedList(new ArrayList<>(List.of(
            new Employee(1L, "John Doe"),
            new Employee(2L, "Jane Doe")
    )));

    public Flux<Employee> findAll() {
        return Flux.fromIterable(employees);
    }
}
