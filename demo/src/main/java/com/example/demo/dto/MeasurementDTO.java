package com.example.demo.dto;

import com.example.demo.models.Sensor;

import jakarta.validation.constraints.Min;

public class MeasurementDTO {

    @Min(value = 0,message = "Value should be greater than 0")
    private int value;
    private boolean raining;
    private Sensor owner;

    public void setValue(int value) {
        this.value = value;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public void setOwner(Sensor owner) {
        this.owner = owner;
    }

    public int getValue() {
        return value;
    }

    public boolean isRaining() {
        return raining;
    }

    public Sensor getOwner() {
        return owner;
    }
}
