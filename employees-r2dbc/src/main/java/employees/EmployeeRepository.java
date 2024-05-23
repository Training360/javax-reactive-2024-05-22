package employees;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmployeeRepository extends ReactiveCrudRepository<Employee, Long> {

    // select id, name from employee where name like :name;
//    Flux<Employee> findAllByNameLike(String name);

    @Query("select id, name from employee")
    Flux<EmployeeDto> findAllEmployeeDto();

    @Query("select id, name from employee where id = :id")
    Mono<EmployeeDto> findEmployeeDtoById(long id);
}
