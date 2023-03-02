package com.example.demo.contollers;

import com.example.demo.dto.SensorDTO;
import com.example.demo.models.Sensor;

import com.example.demo.servers.SensorsService;

import com.example.demo.util.SensorErrorResponse;
import com.example.demo.util.SensorInvalidName;
import com.example.demo.util.SensorIsAlreadyRegistered;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sensors")
public class SensorsController {

    private final SensorsService sensorsService;

    private final ModelMapper modelMapper;

    @Autowired
    public SensorsController(SensorsService sensorsService, ModelMapper modelMapper
    ) {
        this.sensorsService = sensorsService;
        this.modelMapper = modelMapper;

    }


    @GetMapping()
    public List<SensorDTO> getSensors() {
        return sensorsService.findAll().stream()
                .map(this::convertToSensorDTO)
                .collect(Collectors.toList());

    }


    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult) {
        boolean match = sensorsService.findAll().stream()
                .anyMatch(sensor1 -> sensor1.getName().equals(sensorDTO.getName()));
        if (bindingResult.hasErrors()) {
            throw new SensorInvalidName();
        } else if (match) {
            throw new SensorIsAlreadyRegistered();
        } else
            sensorsService.save(convertToSensor(sensorDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handlerException(SensorIsAlreadyRegistered s) {
        SensorErrorResponse response = new SensorErrorResponse("This sensor name is already registered", System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handlerException(SensorInvalidName s) {
        SensorErrorResponse response = new SensorErrorResponse("Name should be between 3 and 30 characters", System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);

    }

    private SensorDTO convertToSensorDTO(Sensor sensor) {
        return modelMapper.map(sensor, SensorDTO.class);

    }

}
