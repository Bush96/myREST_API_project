package com.example.demo.service;

import com.example.demo.models.Measurement;
import com.example.demo.repositories.MeasurementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class MeasurementsService {

    private final MeasurementsRepository measurementsRepository;


    @Autowired
    public MeasurementsService(MeasurementsRepository measurementsRepository) {
        this.measurementsRepository = measurementsRepository;
    }


    public List<Measurement> findAll() {
        return measurementsRepository.findAll();
    }



    @Transactional
    public void save(Measurement measurement){
        measurement.setCreatedAt(LocalDateTime.now());
        measurementsRepository.save(measurement);

    }


}
