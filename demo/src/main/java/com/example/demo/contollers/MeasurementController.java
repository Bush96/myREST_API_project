package com.example.demo.contollers;

import com.example.demo.models.Measurement;
import com.example.demo.models.Sensor;
import com.example.demo.servers.MeasurementsService;
import com.example.demo.servers.SensorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/measurement")
public class MeasurementController {

    private final MeasurementsService measurementsService;


    @Autowired
    public MeasurementController(MeasurementsService measurementsService) {
        this.measurementsService = measurementsService;
    }


    @GetMapping()
    public List<Measurement> getMeasurements() {
        return measurementsService.findAll();
    }

    @PostMapping("/add")
    public void registration(@RequestBody Measurement measurement) {
       measurementsService.save(measurement);

    }
}
