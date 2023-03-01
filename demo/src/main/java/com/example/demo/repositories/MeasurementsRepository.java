package com.example.demo.repositories;

import com.example.demo.models.Measurement;
import com.example.demo.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasurementsRepository extends JpaRepository<Measurement, Integer> {

}
