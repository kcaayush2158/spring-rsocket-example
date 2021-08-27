package com.example.springrsocket.dto;

public class ChartResponseDto {
    private int input;
    private int output;

    public ChartResponseDto(){

    }


    public ChartResponseDto(int input, int output){
        this.input = input;
        this.output=output;
    }

    public int getInput() {
        return input;
    }

    public int getOutput() {
        return output;
    }

    public String getFormat(int value){
        return "%3s|%"+value+"s";

    }

    @Override
    public String toString() {
        return String.format(getFormat(this.output),this.input,"X");
    }
}
