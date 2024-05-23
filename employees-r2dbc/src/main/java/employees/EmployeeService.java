package employees;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Slf4j
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

    public Mono<EmployeeDto> save(EmployeeDto employeeDto) {
//        var entity = toEntity(employeeDto);
//        return employeeRepository.save(entity)
//                .map(EmployeeService::toDto);

        return Mono.just(employeeDto)
                .map(EmployeeService::toEntity)
                .flatMap(employeeRepository::save)
                .map(EmployeeService::toDto)
                .doOnNext(dto -> log.debug("Created dto: {}", dto));
    }

    public Mono<Void> deleteById(long id) {
        return employeeRepository.deleteById(id);
    }

    private static Employee toEntity(EmployeeDto employeeDto) {
        return new Employee(employeeDto.id(), employeeDto.name());
    }

    private static EmployeeDto toDto(Employee employee) {
        return new EmployeeDto(employee.getId(), employee.getName());
    }

}
