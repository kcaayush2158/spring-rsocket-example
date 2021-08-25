package com.example.springrsocket;

import com.example.springrsocket.dto.ComputationRequestDto;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties={ "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.rsocket.RSocketServerAutoConfiguration"})
public class ConnectionManagerTest {

    @Autowired
    private RSocketRequester.Builder builder;

    @Test
    public void connectionTest() throws InterruptedException {
        RSocketRequester rSocketRequester = this.builder.transport(TcpClientTransport.create("localhost",6565));
        RSocketRequester rSocketRequester2 = this.builder.transport(TcpClientTransport.create("localhost",6565));

        rSocketRequester.route("math.service.print").data(new ComputationRequestDto(5)).send();
        rSocketRequester2.route("math.service.print").data(new ComputationRequestDto(5)).send();
        Thread.sleep(10000);
    }

}