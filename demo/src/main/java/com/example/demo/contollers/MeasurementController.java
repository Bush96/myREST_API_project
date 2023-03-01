package com.example.demo.contollers;

import com.example.demo.models.Measurement;
import com.example.demo.servers.MeasurementsService;
import com.example.demo.servers.SensorsService;
import com.example.demo.util.MeasurementInvalidOwner;
import com.example.demo.util.SensorErrorResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/measurement")
public class MeasurementController {

    private final MeasurementsService measurementsService;

    private final SensorsService sensorsService;


    @Autowired
    public MeasurementController(MeasurementsService measurementsService, SensorsService sensorsService) {
        this.measurementsService = measurementsService;
        this.sensorsService = sensorsService;
    }


    @GetMapping()
    public List<Measurement> getMeasurements() {
        return measurementsService.findAll();
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid Measurement measurement, BindingResult bindingResult) {
        boolean match = sensorsService.findAll().stream()
                .anyMatch(sensor1 -> sensor1.getName().equals(measurement.getOwner().getName()));
       if (!match) {
            throw new MeasurementInvalidOwner();
        } else
            measurementsService.save(measurement);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handlerException(MeasurementInvalidOwner m) {
        SensorErrorResponse response = new SensorErrorResponse("This Owner was not registered", System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
    }

}
