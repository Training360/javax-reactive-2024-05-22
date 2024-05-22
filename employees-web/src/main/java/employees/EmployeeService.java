package employees;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@AllArgsConstructor
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    public Flux<EmployeeDto> findAll() {
        return employeeRepository
                .findAll()
                .map(employee -> new EmployeeDto(employee.getId(), employee.getName()));
    }
}
