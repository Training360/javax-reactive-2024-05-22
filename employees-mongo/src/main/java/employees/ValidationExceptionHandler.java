package employees;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.UUID;

@ControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler
    public Mono<ProblemDetail> handle(WebExchangeBindException exception) {
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                exception.getMessage());
        problemDetail.setType(URI.create("employees/validation-error"));

        var items = exception.getBindingResult().getFieldErrors()
                .stream().map(error -> new ValidationItem(error.getField(), error.getDefaultMessage()))
                .toList();
        problemDetail.setProperty("validation-items", items);

        problemDetail.setProperty("error-id", UUID.randomUUID().toString());

        return Mono.just(problemDetail);
    }

    private static final record ValidationItem(String field, String message) {

    }
}
