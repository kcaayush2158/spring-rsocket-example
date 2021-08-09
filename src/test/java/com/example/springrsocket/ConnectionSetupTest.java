package com.example.springrsocket;

import com.example.springrsocket.dto.ClientConnectionRequest;
import com.example.springrsocket.dto.ComputationRequestDto;
import com.example.springrsocket.dto.ComputationResponseDto;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.concurrent.ThreadLocalRandom;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ConnectionSetupTest {


    private RSocketRequester rSocketRequester;
    @Autowired
    private RSocketRequester.Builder builder;
    @Autowired
    private RSocketMessageHandler rSocketMessageHandler;

    @BeforeAll
    public void setup() {
        ClientConnectionRequest clientConnectionRequest = new ClientConnectionRequest();
        clientConnectionRequest.setClientId("order-service");
        clientConnectionRequest.setSecretKey("password");

        this.rSocketRequester = this.builder
            .setupData(clientConnectionRequest)
            .transport(TcpClientTransport.create("localhost", 6565));
    }

    @RepeatedTest(3)
    public void connectionTest(){
        Mono<ComputationResponseDto> computationResponseDtoMono = this.rSocketRequester.route("math.service.square")
            .data(new ComputationRequestDto(ThreadLocalRandom.current().nextInt(1,50)))
            .retrieveMono(ComputationResponseDto.class)
            .doOnNext(System.out::println);

        StepVerifier.create(computationResponseDtoMono)
            .expectNextCount(1)
            .verifyComplete();
    }

}
