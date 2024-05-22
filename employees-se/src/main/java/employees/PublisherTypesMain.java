package employees;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PublisherTypesMain {

    public static void main(String[] args) {
        Mono<Integer> element = Mono.defer(() -> Mono.just(getValue()))
                .doOnNext(System.out::println);
//                        .map(e -> e.toString()).flux();


        element.subscribe(e -> System.out.println("subscribe"));

        element.subscribe(e -> System.out.println("subscribe"));
    }

    public static int getValue() {
        System.out.println("Called");
        return 5;
    }
}
