package employees;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class ReactiveTest {

    @Test
    void filter() {
        var consonant = Flux.just("a", "b", "c", "d", "e", "A", "B")
                .filter(letter -> !"ae".contains(letter.toLowerCase()));

        StepVerifier
                .create(consonant)
                .expectNext("b")
                .expectNext("c")
                .expectNext("d")
//                .expectNext("B")
                .expectNextMatches(letter -> letter.length() == 1)
                .verifyComplete();
    }
}
