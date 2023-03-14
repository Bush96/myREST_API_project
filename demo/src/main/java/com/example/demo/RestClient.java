package com.example.demo;

import com.example.demo.XchartUtil.Value;
import com.example.demo.models.Sensor;

import com.fasterxml.jackson.core.JsonProcessingException;


import com.fasterxml.jackson.databind.ObjectMapper;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

public class RestClient {

    public static void main(String[] args) throws JsonProcessingException {
        sensorRegistration();
        putMeasurements();
        getMeasurements();
        getMeasurementsXchart();
    }


    static void getMeasurements() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/measurement";

        String result = restTemplate.getForObject(url, String.class);
        System.out.println("Measurements: " + result);
    }

    static void getMeasurementsXchart() throws JsonProcessingException {

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/measurement";

        String json = restTemplate.getForObject(url, String.class);

        final ObjectMapper objectMapper = new ObjectMapper();
        Value[] values = objectMapper.readValue(json, Value[].class);

        List<Value> valueList = new ArrayList(Arrays.asList(values));
        List<String> figures = new ArrayList<>(valueList.size());
        for (Value v : valueList) {
            figures.add(v.getValue());
        }

        List<Integer> newList = figures.stream()
                .map(s -> Integer.parseInt(s))
                .collect(Collectors.toList());

        List<Integer> xData = new ArrayList<>(100);
        List<Integer> yData = newList;

        for (int i = 0; i < newList.size(); i++) {
            xData.add(i);
        }
        // Create Chart
        XYChart chart = QuickChart.getChart("Sample Chart", "X", "Y", "y(x)", xData, yData);
        // Show it
        new SwingWrapper(chart).displayChart();
    }


    static void sensorRegistration() {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> jsonToSend = new HashMap<>();
        jsonToSend.put("name", "test_sensor7");

        HttpEntity<Map<String, String>> request = new HttpEntity<>(jsonToSend);

        String url = "http://localhost:8080/sensors/registration";
        String result = restTemplate.postForObject(url, request, String.class);
        System.out.println(result);
    }


    static void putMeasurements() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/measurement/add";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        for (int i = 0; i < 100; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("value", randomValue());
            map.put("raining", randomRaining());
            map.put("owner", new Sensor(1, "test_sensor"));

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        }
    }


    private static String randomRaining() {
        final Random random = new Random();
        return String.valueOf(random.nextBoolean());
    }

    private static Integer randomValue() {
        return (int) (Math.random() * (200 + 1)) - 100;
    }

}
