package com.example.demo.util;

public class SensorWasNotCreatedException extends RuntimeException{
    public SensorWasNotCreatedException(String msg){
        super(msg);
    }
}
