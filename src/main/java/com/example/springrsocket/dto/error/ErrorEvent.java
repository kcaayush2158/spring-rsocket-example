package com.example.springrsocket.dto.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ErrorEvent {

    private StatusCode statusCode;
    private final LocalDate localDate= LocalDate.now();
}
