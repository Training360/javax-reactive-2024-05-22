package employees;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.stream.Stream;

public class ReactiveMain {

    public static void main(String[] args) {
//        Flux<String> letters = Flux.just("a", "b", "c", "d");
//
//        letters.subscribe(System.out::println);

        Flux.just("a", "b", "c")
                .map(String::toUpperCase)
                .subscribe(System.out::println);

        Stream.of("a", "b", "c")
                .map(String::toUpperCase)
                .forEach(System.out::println);

        Mono.just("a")
                .subscribe(System.out::println);

        Optional.of("a")
                .stream()
                .forEach(System.out::println);

        Flux.just(
                new Employee("John Doe", 1970),
                new Employee("Jack Doe", 1980)
        )
                // Csak 1980 előtt születettek
                .filter(employee -> employee.yearOfBirth() < 1980)
                // Csak nevek
                .map(Employee::name)
                .subscribe(System.out::println);

        int year = Flux.just(
                new Employee("John Doe", 1970),
                new Employee("Jack Doe", 1980)
        )
                .filter(employee -> "John Doe".equals(employee.name()))
                .single()
                .map(Employee::yearOfBirth)
                .block(); // Reaktív Spring es alkalmazásban SOSEM hívunk block()-ot!
        System.out.println(year);

    }
}
