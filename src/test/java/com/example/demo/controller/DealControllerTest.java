package com.example.demo.controller;

import com.example.demo.model.Deal;
import com.example.demo.model.DealEvent;
import com.example.demo.repository.DealRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)  // junit 4 -> @RunWith(SpringRunner.class)
@SpringBootTest
class DealControllerTest {

    private WebTestClient webClient;

    @Autowired
    DealRepository dealRepository;

    List<Deal> expectedList;

    @BeforeEach
    public void beforeEach() {
        webClient =
                WebTestClient.bindToController(new DealController(dealRepository))
                        .configureClient()
                        .baseUrl("/deals")
                        .build();

        expectedList = dealRepository.findAll()
                .collectList().block();          // async to sync !!!

    }

    @org.junit.jupiter.api.Test
    void testGetAllDeals() {
        webClient.get()
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Deal.class).isEqualTo(expectedList);
    }


    @org.junit.jupiter.api.Test
    void testGetEvents() {

        DealEvent expectedEvent = new DealEvent(1L, "Deal 1");

        FluxExchangeResult<DealEvent> result =
                webClient.get().uri("/events")
                        .accept(MediaType.TEXT_EVENT_STREAM)
                        .exchange()
                        .expectStatus().isOk()
                        .returnResult(DealEvent.class);


        StepVerifier.create(result.getResponseBody())
                .expectNext()
                .expectNextCount(1)
                .consumeNextWith(e -> assertEquals(e.getEventId(), expectedEvent.getEventId()))
                .thenCancel()
                .verify();
    }

}