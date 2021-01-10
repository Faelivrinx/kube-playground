package com.example.payment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@Slf4j
public class PaymentController {

    private final WebClient.Builder builder;

    @Value("${services.message.makeMessageUrl}")
    private String messageUrl;

    @GetMapping("/payment")
    public Mono<ResponseEntity<PaymentResponse>> makePayment() {
        log.info("Creating new payment...");
        return builder
                .baseUrl(messageUrl).build()
                .get()
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(UUID.class))
                .map(uuid -> ResponseEntity.ok(new PaymentResponse(uuid)));
    }
}

@RequiredArgsConstructor
@Getter
class PaymentResponse {
    private final UUID paymentId;
}
