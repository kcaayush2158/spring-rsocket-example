package com.example.springrsocket.dto.error;

public enum StatusCode {
    EC001("Given number is not within range"),
    EC002("Your ugage limit exceed");

        private final String description;
    StatusCode(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
