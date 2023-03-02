package com.example.demo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Measurement")
public class Measurement {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "value")
    @Min(value = -100,message = "Value should be between -100 and 100")
    @Max(value = 100,message = "Value should be between -100 and 100")
    private int value;

    @Column(name = "raining")
    private boolean raining;

    @Column(name = "created_ad")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "sensor_id", referencedColumnName = "id")
    private Sensor owner;

    public Measurement() {

    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
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

}
