package com.example.springrsocket;

import com.example.springrsocket.dto.ComputationRequestDto;
import com.example.springrsocket.dto.ComputationResponseDto;
import io.rsocket.core.Resume;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.retry.Retry;

import java.time.Duration;

@SpringBootTest
@TestPropertySource(properties =
        {
                "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.rsocket.RSocketServerAutoConfiguration"
        }
)
public class SessionResumption {

    @Autowired
    private RSocketRequester.Builder builder;

    @Test
    public void connectionTest() throws InterruptedException {

        RSocketRequester requester = this.builder

                .rsocketConnector(c -> c.reconnect(retryStrategy() )
                .reconnect(retryStrategy()))
                .transport(TcpClientTransport.create("localhost", 6565));

        Flux<ComputationResponseDto> computationResponseDtoFlux = requester.route("math.service.table")
                .data(new ComputationRequestDto(5))
                .retrieveFlux(ComputationResponseDto.class)
                .doOnNext(System.out::println);

        StepVerifier.create(computationResponseDtoFlux)
                .expectNextCount(1000)
                .verifyComplete();

            Thread.sleep(2000);

        }

        private Resume resumeStrategy(){
        return new Resume()
                .retry(Retry.fixedDelay(2000,Duration.ofSeconds(2))
                        .doBeforeRetry(s -> System.out.println("resuming retry : " + s.totalRetriesInARow())));
        }
    private Retry retryStrategy() {
        return Retry.fixedDelay(100, Duration.ofSeconds(1))
                .doBeforeRetry(s -> System.out.println("Retrying Connection : "+ s.totalRetriesInARow()));
    }


}