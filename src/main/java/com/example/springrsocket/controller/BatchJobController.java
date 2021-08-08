package com.example.springrsocket.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class BatchJobController {
    @MessageMapping("batch.job.request ")
    public Mono<Void> submitJob(Mono<Integer> integerMono , RSocketRequester rSocketRequester){
        this.process(integerMono,rSocketRequester);
        return Mono.empty();
    }

    private void process(Mono<Integer> integerMono,RSocketRequester rSocketRequester){
        integerMono.
            map(i -> i*i*i)
            .flatMap(i -> rSocketRequester.route("batch.job.request").data(i).send())
            .subscribe();



    }

}