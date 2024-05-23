package employees;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table
public record Employee(@Id Long id, String name) {


}
