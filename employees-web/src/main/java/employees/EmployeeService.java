package employees;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    public Flux<EmployeeDto> findAll() {
        return employeeRepository
                .findAll()
                .map(EmployeeService::toDto);
    }

    public Mono<EmployeeDto> findById(long id) {
        return employeeRepository
                .findById(id)
                .map(EmployeeService::toDto);
    }

    private static EmployeeDto toDto(Employee employee) {
        return new EmployeeDto(employee.getId(), employee.getName());
    }

}
