package employees;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/api/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    @GetMapping
    public Flux<EmployeeDto> findAll() {
        return employeeService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<EmployeeDto>> findById(@PathVariable String id) {
        return employeeService.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<EmployeeDto>> create(@Valid @RequestBody EmployeeDto employeeDto) {
        if (employeeDto.id() != null) {
            throw new IllegalArgumentException("Do not send id!");
        }
        return employeeService
                .save(employeeDto)
                .map(result -> ResponseEntity.created(URI.create("/api/employees/%s".formatted(result.id()))).body(result));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<EmployeeDto>> update(@PathVariable String id, @Valid @RequestBody EmployeeDto employeeDto) {
        if (!id.equals(employeeDto.id())) {
            throw new IllegalArgumentException("Ids are not the same");
        }
        return employeeService.save(employeeDto)
                .map(ResponseEntity::ok)
                .onErrorReturn(TransientDataAccessResourceException.class, ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteById(@PathVariable String id) {
        return employeeService.deleteById(id);
    }
}
