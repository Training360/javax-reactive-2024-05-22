package employees;

import io.reactivex.rxjava3.core.Flowable;
import reactor.core.publisher.Flux;

public class IntegrationMain {

    public static void main(String[] args) {
        Flux.from(create())
                .filter(letter -> !"B".equals(letter))
                .subscribe(System.out::println);
    }

    public static Flowable<String> create() {
        return Flowable.just("a", "b", "c")
                .map(String::toUpperCase);

    }
}
