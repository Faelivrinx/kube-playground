package com.example.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.UUID;

@RestController
@Slf4j
public class MessageController {

    @GetMapping(value = "/message")
    Mono<ResponseEntity<UUID>> makeMessage(){
        return Mono.fromCallable(() -> {
            UUID id = UUID.randomUUID();
            log.info("Message send with id {}", id);
            return ResponseEntity.ok(id);
        }).delayElement(Duration.ofSeconds(4500));
    }
}
