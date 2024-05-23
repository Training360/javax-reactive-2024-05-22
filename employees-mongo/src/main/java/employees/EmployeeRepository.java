package employees;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmployeeRepository extends ReactiveCrudRepository<Employee, String> {

    // select id, name from employee where name like :name;
//    Flux<Employee> findAllByNameLike(String name);

    @Query("{}")
    Flux<EmployeeDto> findAllEmployeeDto();

    @Query("{'_id':  ?0}")
    Mono<EmployeeDto> findEmployeeDtoById(@Param("id") String id);
}
