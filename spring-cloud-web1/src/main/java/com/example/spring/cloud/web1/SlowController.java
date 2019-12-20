package com.example.spring.cloud.web1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
public class SlowController {

    @GetMapping("/slow")
    public Mono<String> find() {
        return Mono.delay(Duration.ofSeconds(10))
                .then(Mono.just("こしあん"));
    }

    @GetMapping("/ping")
    public Mono<String> ping() {
        return Mono.just("hello !!!");
    }

}
