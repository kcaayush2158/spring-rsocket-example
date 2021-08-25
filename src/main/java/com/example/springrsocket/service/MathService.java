package com.example.springrsocket.service;

import com.example.springrsocket.dto.ChartResponseDto;
import com.example.springrsocket.dto.ComputationRequestDto;
import com.example.springrsocket.dto.ComputationResponseDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MathService {

    public Mono<Void> print (Mono<ComputationRequestDto> computationRequestDtoMono){
        return computationRequestDtoMono
            . doOnNext(System.out::println)
            .then();
    }

    public Mono<ComputationResponseDto> findSquare (Mono<ComputationRequestDto> computationRequestDtoMono){
        return computationRequestDtoMono
            .map(ComputationRequestDto::getInput)
            .map(i -> new ComputationResponseDto(i,i*i));
    }

    public Flux<ComputationResponseDto> tableStream (ComputationRequestDto computationRequestDtoMono){
        return Flux.range(1,10)
            .map(i -> new  ComputationResponseDto(computationRequestDtoMono.getInput(), computationRequestDtoMono.getInput()*i));

    }
    public Flux<ChartResponseDto> chartStream(Flux<ComputationRequestDto> computationRequestDtoFlux){
        return computationRequestDtoFlux
            .map(ComputationRequestDto::getInput)
            .map(i -> new ChartResponseDto(i ,(i*i)+1));

    }
}
