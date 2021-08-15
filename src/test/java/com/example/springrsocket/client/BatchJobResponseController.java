package com.example.springrsocket.client;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class  BatchJobResponseController {

    @MessageMapping("batch.job.response")
    public Mono<Void> reponse (Mono<Integer> integerMono){
        return integerMono
            . doOnNext(i ->System.out.print("Client Received :"+i))
            .then();
    }

}
