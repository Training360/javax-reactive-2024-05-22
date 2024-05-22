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
        return employeeService.create(employeeDto);
    }
}
