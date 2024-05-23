package employees;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class EmployeeGateway {

    @Bean
    public Consumer<Flux<EmployeeDto>> messageHandler() {
//        return dto -> log.info("Message has come: {}", dto);
        return flux -> flux.subscribe(dto -> log.info("Message has come: {}", dto));
    }
}
