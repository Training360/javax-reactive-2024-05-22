package employees;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
public class HelloController {

    @GetMapping("/api/hello")
    public Mono<String> hello() {
        return Mono.just("Hello %s".formatted(LocalDateTime.now()));
    }
}
