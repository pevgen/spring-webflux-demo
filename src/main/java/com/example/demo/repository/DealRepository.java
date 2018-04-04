package com.example.demo.repository;

import com.example.demo.model.Deal;
import org.reactivestreams.Publisher;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface DealRepository extends ReactiveMongoRepository<Deal, String> {

    Flux<Deal> findByName(String name);
    Flux<Deal> findByName(Publisher<String> name);
}
