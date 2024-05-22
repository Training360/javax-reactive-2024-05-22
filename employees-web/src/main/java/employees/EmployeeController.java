package employees;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    public Mono<EmployeeDto> findById(@PathVariable long id) {
        return employeeService.findById(id);
    }

    @PostMapping
    public Mono<EmployeeDto> create(@RequestBody EmployeeDto employeeDto) {
        if (employeeDto.id() != null) {
            throw new IllegalArgumentException("Do not send id!");
        }
        return employeeService.save(employeeDto);
    }

    @PutMapping("/{id}")
    public Mono<EmployeeDto> update(@PathVariable long id, @RequestBody EmployeeDto employeeDto) {
        if (id != employeeDto.id()) {
            throw new IllegalArgumentException("Ids are not the same");
        }
        return employeeService.save(employeeDto);
    }
}
