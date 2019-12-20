package com.example.spring.cloud.circuitbreaker;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class ClientController {

    private final WebClient.Builder webClient;
    private final ReactiveCircuitBreakerFactory reactiveCircuitBreakerFactory;

    @GetMapping("/circuit")
    public Mono<String> hello() {
        return webClient.build()
                .get().uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host("web1-service").path("/slow")
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .transform(it -> {
                    ReactiveCircuitBreaker rcb = reactiveCircuitBreakerFactory.create("slow");
                    return rcb.run(it, throwable -> Mono.just("fallback"));
                });
    }

}
