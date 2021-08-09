package com.example.springrsocket;

import com.example.springrsocket.dto.ComputationRequestDto;
import com.example.springrsocket.dto.ComputationResponseDto;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SpringRsocketApplicationTests {
    private RSocketRequester rSocketRequester;
    @Autowired
    private RSocketRequester.Builder builder;

    @BeforeAll
    public void setup(){
       this.rSocketRequester = this.builder
                   .transport(TcpClientTransport.create("localhost",6565));
    }

    @Test
    public void fireAndForget() {
        Mono<Void> mono =  this.rSocketRequester.route("math.service.print")
        .data(new ComputationRequestDto(5))
        .send();

        StepVerifier.create(mono)
            .verifyComplete();
    }

    @Test
    public void requestResponse(){
        Mono<ComputationResponseDto> computationResponseDtoMono = this.rSocketRequester.route("math.service.square")
            .data(new ComputationRequestDto(5))
            .retrieveMono(ComputationResponseDto.class)
            .doOnNext(System.out::println);

        StepVerifier.create(computationResponseDtoMono)
            .expectNextCount(1)
            .verifyComplete();
    }

    @Test
    public void requestStream(){
        Flux<ComputationResponseDto> computationResponseDtoFlux = this.rSocketRequester.route("math.service.table")
            .data(new ComputationRequestDto(5))
            .retrieveFlux(ComputationResponseDto.class)
            .doOnNext(System.out::println);

        StepVerifier.create(computationResponseDtoFlux)
            .expectNextCount(10)
            .verifyComplete();
    }

    @Test
    public void requestChannel(){
      Flux<ComputationRequestDto> computationRequestDtoFlux =  Flux.range(-10,21)
            .map(ComputationRequestDto::new);

        Flux<ComputationResponseDto> computationResponseDtoFlux = this.rSocketRequester.route("math.service.chart")
            .data(computationRequestDtoFlux)
            .retrieveFlux(ComputationResponseDto.class)
            .doOnNext(System.out::println);

        StepVerifier.create(computationResponseDtoFlux)
            .expectNextCount(21)
            .verifyComplete();
    }



}
