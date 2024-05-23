package employees;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Slf4j
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    private ReactiveRedisTemplate<Long, EmployeeDto> reactiveRedisTemplate;

    public Flux<EmployeeDto> findAll() {
//        return employeeRepository
//                .findAll()
//                .map(EmployeeService::toDto);
        return employeeRepository.findAllEmployeeDto();
    }

    public Mono<EmployeeDto> findById(long id) {
//        return employeeRepository
//                .findById(id)
//                .map(EmployeeService::toDto);

//        return employeeRepository.findEmployeeDtoById(id);

        return reactiveRedisTemplate.opsForValue().get(id)
                .switchIfEmpty(
                        employeeRepository
                                .findEmployeeDtoById(id)
                                .flatMap(dto -> reactiveRedisTemplate.opsForValue().set(id, dto).thenReturn(dto))
                )
                ;
    }

    @Transactional
    public Mono<EmployeeDto> save(EmployeeDto employeeDto) {
//        var entity = toEntity(employeeDto);
//        return employeeRepository.save(entity)
//                .map(EmployeeService::toDto);

        return Mono.just(employeeDto)
                .map(EmployeeService::toEntity)
                // Ez nem létező id esetén  TransientDataAccessResourceException kivételt dob
                .flatMap(employeeRepository::save)
                .map(EmployeeService::toDto)
                .doOnNext(dto -> log.debug("Created dto: {}", dto));
//                .handle((dto, sink) -> sink.error(new IllegalStateException("Rollback")));
    }

    public Mono<Void> deleteById(long id) {
        return employeeRepository.deleteById(id);
    }

    private static Employee toEntity(EmployeeDto employeeDto) {
        return new Employee(employeeDto.id(), employeeDto.name());
    }

    private static EmployeeDto toDto(Employee employee) {
        return new EmployeeDto(employee.id(), employee.name());
    }

}
