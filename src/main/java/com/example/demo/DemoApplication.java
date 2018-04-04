package com.example.demo;

import com.example.demo.model.Deal;
import com.example.demo.repository.DealRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    CommandLineRunner init(DealRepository dealRepository) {
        return args -> {
            Flux<Deal> dealFlux =
                    Flux.just(
                            new Deal("1", "Name 1"),
                            new Deal("2", "Name 2"),
                            new Deal("3", "Name 3"))
                            .flatMap(dealRepository::save);

            // thenMany - will be run only after save(flux) all 3 deals will be complete
            dealFlux
                    .thenMany(dealRepository.findAll())
                    .subscribe(System.out::println);
        };
    }
}
