package employees;

import org.springframework.data.annotation.Id;

public record Employee(@Id Long id, String name) {


}
