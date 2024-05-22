package employees;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class EmployeeRepository {
    private final AtomicLong idSequence = new AtomicLong();

    private final List<Employee> employees = Collections.synchronizedList(new ArrayList<>(List.of(
            new Employee(idSequence.incrementAndGet(), "John Doe"),
            new Employee(idSequence.incrementAndGet(), "Jane Doe")
    )));


    public Flux<Employee> findAll() {
        return Flux.fromIterable(employees);
    }

    public Mono<Employee> findById(long id) {
        return Flux.fromIterable(employees)
                .filter(employee -> employee.getId() == id)
                .singleOrEmpty();
    }

    public Mono<Employee> save(Employee employee) {
        if (employee.getId() == null) {
            employee.setId(idSequence.incrementAndGet());
            employees.add(employee);
            return Mono.just(employee);
        }
        else {
            return findById(employee.getId())
                    .doOnNext(e -> e.setName(employee.getName())); // Ilyet ne csináljunk
        }
    }
}
