package com.example.demo.client;

import com.example.demo.model.Deal;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

public class WebClientAPI {

    private WebClient webClient;

    public WebClientAPI() {
        this.webClient =
                WebClient.builder()
                        .baseUrl("http://localhost:8080/deals")
                        .build();
    }


    Flux<Deal> getAllDeals() {
        return webClient.get()
                .retrieve()
                .bodyToFlux(Deal.class)
                .doOnNext(System.out::println);
    }

    public static void main(String[] args) throws InterruptedException {
        WebClientAPI clientAPI = new WebClientAPI();
        clientAPI.getAllDeals()
                .log()
                .subscribe(System.out::println);
        Thread.sleep(5000);
    }
}
