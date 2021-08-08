package com.example.springrsocket.controller;

import com.example.springrsocket.dto.ChatResponseDto;
import com.example.springrsocket.dto.ComputationRequestDto;
import com.example.springrsocket.dto.ComputationResponseDto;
import com.example.springrsocket.service.MathService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class MathController {

    private MathService mathService;

    @MessageMapping("math.service.print")
    private Mono<Void> print(Mono<ComputationRequestDto> computationRequestDtoMono){
        return this.mathService.print(computationRequestDtoMono);
    }
    @MessageMapping("math.service.square")
    private Mono<ComputationResponseDto> findSquare(Mono<ComputationRequestDto> computationRequestDtoMono){
        return this.mathService.findSquare(computationRequestDtoMono);
    }
    @MessageMapping("math.service.table")
    private Flux<ComputationResponseDto> tableStream(Mono<ComputationRequestDto> computationRequestDtoMono){
        return computationRequestDtoMono.flatMapMany(this.mathService::tableStream);
    }
    @MessageMapping("math.service.chart ")
    private Flux<ChatResponseDto> chartStream(Flux<ComputationRequestDto> computationRequestDtoMono){
        return this.mathService.chartStream(computationRequestDtoMono);

    }

}
