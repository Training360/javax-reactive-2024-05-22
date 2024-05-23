package employees;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;
import java.util.function.Function;

@Configuration
@Slf4j
public class EmployeeGateway {

    @Bean
    public Function<Flux<EmployeeDto>, Flux<EmployeeDto>> messageHandler() {
//        return dto -> log.info("Message has come: {}", dto);
//        return flux -> flux.subscribe(dto -> log.info("Message has come: {}", dto));
        return flux -> flux
                .doOnNext(dto -> log.info("Message has come: {}", dto))
                .map(dto -> new EmployeeDto(dto.id(), "Reply: " + dto));
    }
}
