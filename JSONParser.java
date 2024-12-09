package com.greenhouse.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenhouse.model.Emission;
import com.greenhouse.repository.EmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class JSONParser {

    @Autowired
    private EmissionRepository emissionRepository;

    public void parse(String filePath) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(new File(filePath));

            for (JsonNode node : root.get("Emissions")) {
                String category = node.get("Category").asText();
                String gasUnit = node.get("Gas Units").asText();
                double value = node.get("Value").asDouble();
                String scenario = "WEM"; 
                int year = 2023;         

                if (value > 0 && "WEM".equalsIgnoreCase(scenario) && year == 2023) {
                    Emission emission = new Emission(category, scenario, year, gasUnit, value);
                    emissionRepository.save(emission);
                }
            }
            System.out.println("JSON Parsing Completed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
