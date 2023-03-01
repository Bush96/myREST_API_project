package com.example.demo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Measurement")
public class Measurement {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "value")
    @NotEmpty(message = "Value should not be empty")
    @Min(value = 0,message = "Value should be greater than 0")
    private int value;

    @Column(name = "raining")
    @NotEmpty(message = "Column should not be empty")
    private boolean raining;

    @ManyToOne
    @JoinColumn(name = "sensor_id", referencedColumnName = "id")
    private Sensor owner;

    public Measurement() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public void setOwner(Sensor owner) {
        this.owner = owner;
    }

    public int getId() {
        return id;
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


//    public Measurement(int id, int value, boolean raining, Sensor owner) {
//        this.id = id;
//        this.value = value;
//        this.raining = raining;
//        this.owner = owner;
//    }
}
