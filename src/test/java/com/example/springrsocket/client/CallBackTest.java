package com.example.springrsocket.client;


import io.rsocket.transport.netty.client.TcpClientTransport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CallBackTest {

    private RSocketRequester rSocketRequester;
    @Autowired
    private RSocketRequester.Builder builder;
    @Autowired
    private RSocketMessageHandler rSocketMessageHandler;

    @BeforeAll
    public void setup() {

        this.rSocketRequester = this.builder
            .rsocketConnector(connector -> connector.acceptor(rSocketMessageHandler.responder()))
            .transport(TcpClientTransport.create("localhost", 6565));
    }

    @Test
    public void callBackTest() throws InterruptedException {
        Mono<Void> mono = this.rSocketRequester.route("batch.job.request")
            .data(5).send();

        StepVerifier.create(mono)
            .verifyComplete();
        Thread.sleep(1200);
    }


}
