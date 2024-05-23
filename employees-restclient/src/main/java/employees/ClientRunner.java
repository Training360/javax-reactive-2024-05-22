package employees;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ClientRunner implements CommandLineRunner  {

    @Autowired
    private WebClient webClient;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("hello");

        var json = webClient.get().uri("/api/employees").exchangeToMono(response -> response.bodyToMono(String.class)).block();
        System.out.println(json);
    }

}
