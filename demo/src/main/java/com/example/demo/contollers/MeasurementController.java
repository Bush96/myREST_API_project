package com.example.demo.contollers;

import com.example.demo.dto.MeasurementDTO;
import com.example.demo.dto.SensorDTO;
import com.example.demo.models.Measurement;
import com.example.demo.models.Sensor;
import com.example.demo.servers.MeasurementsService;
import com.example.demo.servers.SensorsService;
import com.example.demo.util.IncorrectMeasurements;
import com.example.demo.util.MeasurementInvalidOwner;
import com.example.demo.util.SensorErrorResponse;
import com.example.demo.util.SensorWasNotCreatedException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurement")
public class MeasurementController {

    private final MeasurementsService measurementsService;
    private final SensorsService sensorsService;
    private final ModelMapper modelMapper;


    @Autowired
    public MeasurementController(MeasurementsService measurementsService, SensorsService sensorsService,
                                 ModelMapper modelMapper) {
        this.measurementsService = measurementsService;
        this.sensorsService = sensorsService;
        this.modelMapper = modelMapper;
    }


    @GetMapping()
    public List<MeasurementDTO> getMeasurements() {
        return measurementsService.findAll().stream()
                .map(this::convertToMeasurementDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid MeasurementDTO measurementDTO, BindingResult bindingResult) {
        boolean match = sensorsService.findAll().stream()
                .anyMatch(sensor1 -> sensor1.getName().equals(measurementDTO.getOwner().getName()));
        if (!match) {
            throw new MeasurementInvalidOwner();

        } else if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ").
                        append(error.getDefaultMessage())
                        .append(";");
            }
            throw new IncorrectMeasurements(errorMsg.toString());


        } else
            measurementsService.save(convertToMeasurement(measurementDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/rainyDaysCount")
    public int rainyDaysCount() {
        int counter = 0;
        List<Measurement> forCheck = measurementsService.findAll();
        for (Measurement f : forCheck) {
            if (f.isRaining()) {
                counter++;
            }
        }

        return counter;
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handlerException(MeasurementInvalidOwner m) {
        SensorErrorResponse response = new SensorErrorResponse("This Owner was not registered", System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handlerException(IncorrectMeasurements m) {
        SensorErrorResponse response = new SensorErrorResponse("Incorrect measurements", System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);

    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);

    }

}
