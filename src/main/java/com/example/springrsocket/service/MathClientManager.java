package com.example.springrsocket.service;

import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class MathClientManager {

    private final Set<RSocketRequester> set = Collections.synchronizedSet(new HashSet<>());


    public void add(RSocketRequester rSocketRequester){
        rSocketRequester.rsocket()
                .onClose()
                .doFirst(() -> this.set.add(rSocketRequester))
                .doFinally(s ->{
                    System.out.print("finally");
                    this.set.remove(rSocketRequester);
                }).subscribe();
    }

    @Scheduled(fixedDelay = 1000)
    public void print(){

        System.out.print(set);

    }

    public void notify(int i){
        Flux.fromIterable(set)
                .flatMap(r -> r.route("product.update.event").data(i).send())
                .subscribe();
    }
}
