package com.example.demo.controller;

import com.example.demo.model.Deal;
import com.example.demo.model.DealEvent;
import com.example.demo.repository.DealRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/deals")
public class DealController {

    private DealRepository repository;

    public DealController(DealRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    Flux<Deal> getAllDeals() {
        return repository.findAll();  // no subscribe because Spring will do it itself
    }


    @GetMapping("/{id}")
    Mono<ResponseEntity<Deal>> getById(@PathVariable("id") String id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Mono<Deal> save(@RequestBody Deal deal) {
        return repository.save(deal);
    }

    @PutMapping("/{id}")
    Mono<ResponseEntity<Deal>> getById(@PathVariable("id") String id, @RequestBody Deal deal) {

        return repository.findById(id)
                .flatMap(existingDeal -> {
                    existingDeal.setName(deal.getName());
                    return repository.save(existingDeal);
                })
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());

    }

    @DeleteMapping("/{id}")
    Mono<ResponseEntity<Void>> deleteById(@PathVariable("id") String id) {
        return repository.findById(id)
                .flatMap(existingDeal ->
                        repository.delete(existingDeal)
                                .then(Mono.just(ResponseEntity.ok().<Void>build())))
                .defaultIfEmpty(ResponseEntity.notFound().build());

    }

    @DeleteMapping
    Mono<Void> deleteAll() {
        return repository.deleteAll();
    }

    @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<DealEvent> getAllEvents() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(val -> new DealEvent(val, "type-event"));
    }


}
