package com.greenhouse.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Emission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;
    private String scenario;     
    private int year;           
    private String gasUnit;
    private double value;

    public Emission(String category, String scenario, int year, String gasUnit, double value) {
        this.category = category;
        this.scenario = scenario;
        this.year = year;
        this.gasUnit = gasUnit;
        this.value = value;
    }

    public Emission() {
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getScenario() { return scenario; }
    public void setScenario(String scenario) { this.scenario = scenario; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public String getGasUnit() { return gasUnit; }
    public void setGasUnit(String gasUnit) { this.gasUnit = gasUnit; }

    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }
}
