package com.example.springrsocket.controller;

import com.example.springrsocket.dto.ClientConnectionRequest;
import org.springframework.messaging.rsocket.annotation.ConnectMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class ConnectionHandler {

    @ConnectMapping
    public Mono<Void> handleConnection(ClientConnectionRequest clientConnectionRequest){
        System.out.print("connection setup"+clientConnectionRequest);
        return Mono.empty();
    }
}
