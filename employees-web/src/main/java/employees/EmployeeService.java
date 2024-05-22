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

    public Mono<EmployeeDto> create(EmployeeDto employeeDto) {
        return Mono.just(employeeDto)
                .map(EmployeeService::toEntity)
                .flatMap(employeeRepository::save)
                .map(EmployeeService::toDto);
    }

    private static Employee toEntity(EmployeeDto employeeDto) {
        return new Employee(employeeDto.id(), employeeDto.name());
    }

    private static EmployeeDto toDto(Employee employee) {
        return new EmployeeDto(employee.getId(), employee.getName());
    }

}
