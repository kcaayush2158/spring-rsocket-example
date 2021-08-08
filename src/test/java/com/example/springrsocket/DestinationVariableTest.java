package com.example.springrsocket;

import com.example.springrsocket.dto.ComputationRequestDto;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DestinationVariableTest {

    private RSocketRequester rSocketRequester;
    private RSocketRequester.Builder builder;

    @BeforeAll
    public void setup(){
        this.rSocketRequester = this.builder
            .transport(TcpClientTransport.create("localhost",6565));
    }

    @Test
    public void fireAndForget() {
        Mono<Void> mono =  this.rSocketRequester.route("math.service.print.55")
            .send();

        StepVerifier.create(mono)
            .verifyComplete();
    }

}
