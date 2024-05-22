package employees;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
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

//        new Employee("John Doe", 1970).getAgeAt(1960);

        Flux.just(
                new Employee("John Doe", 1970),
                new Employee("Jack Doe", 1980)
        )
                .map(employee -> employee.getAgeAt(1960))
//                .onErrorReturn(-1)
                .onErrorResume(t -> Mono.just(-1))
                .subscribe(System.out::println);

        // FlatMap
        // Írjátok ki egymás alá, hogy milyen nyelvekhez ért a csapat, mindegyik nyelv csak egyszer szerepelhet
        // ábécé sorrendben
        // Flux.fromIterable()
        Flux.just(
                new Employee("John Doe", 1970, List.of("Java", "JavaScript", "Python")),
                new Employee("Jack Doe", 1980, List.of("Java", "C#")),
                new Employee("Jane Doe", 1980, List.of("Java", "Kotlin"))
        )
                .flatMap(employee -> Flux.fromIterable(employee.skills()))
                .sort()
                .distinct()
                .subscribe(System.out::println);
        // C#
        // Java
        // JavaScript
        // Kotlin
        // Python

        // Hibakezelés úgy, hogy a stream ne álljon le
        // Belső stream, ami leállhat
        Flux.just(
                        new Employee("John Doe", 1970),
                        new Employee("Jack Doe", 1980)
                )
                .flatMap(employee ->
                        Mono.just(employee).map(e -> e.getAgeAt(1960))
                                .doOnError(System.out::println)
                                .onErrorReturn(-1)
                )
                .subscribe(System.out::println);

    }
}
