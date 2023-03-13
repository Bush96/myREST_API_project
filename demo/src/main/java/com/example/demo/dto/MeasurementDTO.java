package com.example.demo.dto;

import com.example.demo.models.Sensor;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.util.Map;

public class MeasurementDTO {

    @Min(value = -100,message = "Value should be between -100 and 100")
    @Max(value = 100,message = "Value should be between -100 and 100")
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
