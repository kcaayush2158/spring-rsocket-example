package com.example.springrsocket.controller;

import com.example.springrsocket.dto.ClientConnectionRequest;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.annotation.ConnectMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class ConnectionHandler {

    @ConnectMapping
    public Mono<Void> handleConnection(ClientConnectionRequest clientConnectionRequest, RSocketRequester rSocketRequester){
        System.out.print("connection setup"+clientConnectionRequest);
        System.out.print("connection setup");
        return clientConnectionRequest.getSecretKey().equals("password") ?
                Mono.empty() :
//                Mono.error(new RuntimeException("Bad Exception"));
                Mono.fromRunnable(() ->rSocketRequester.rsocketClient().dispose());
    }
}
