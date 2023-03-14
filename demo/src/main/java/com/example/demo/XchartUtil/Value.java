package com.example.demo.XchartUtil;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Map;

public class Value {
    private String value;
    @JsonIgnore
    private String raining;
    @JsonIgnore
    private Map<String, String> owner;

    public void setRaining(String raining) {
        this.raining = raining;
    }

    public void setOwner(Map<String, String> owner) {
        this.owner = owner;
    }

    public String getRaining() {
        return raining;
    }

    public Map<String, String> getOwner() {
        return owner;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

