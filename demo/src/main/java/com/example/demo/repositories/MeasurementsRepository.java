package com.example.demo.repositories;

import com.example.demo.models.Measurement;
import com.example.demo.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeasurementsRepository extends JpaRepository<Measurement, Integer> {
//надо додуматься как вытянуть только булевые тру из бд
}
