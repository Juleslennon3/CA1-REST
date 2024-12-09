package com.greenhouse.controller;

import com.greenhouse.model.Emission;
import com.greenhouse.repository.EmissionRepository;
import com.greenhouse.parser.XMLParser;
import com.greenhouse.parser.JSONParser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/emissions")
public class EmissionController {

    @Autowired
    private XMLParser xmlParser;

    @Autowired
    private JSONParser jsonParser;

    @Autowired
    private EmissionRepository emissionRepository;

    @PostMapping
    public ResponseEntity<Emission> createEmission(@RequestBody Emission emission) {
        Emission savedEmission = emissionRepository.save(emission);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmission);
    }

    @GetMapping
    public ResponseEntity<List<Emission>> getAllEmissions() {
        List<Emission> emissions = emissionRepository.findAll();
        return ResponseEntity.ok(emissions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Emission> getEmissionById(@PathVariable Long id) {
        Optional<Emission> emission = emissionRepository.findById(id);
        return emission.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Emission> updateEmission(@PathVariable Long id, @RequestBody Emission updatedEmission) {
        return emissionRepository.findById(id)
                .map(emission -> {
                    emission.setCategory(updatedEmission.getCategory());
                    emission.setScenario(updatedEmission.getScenario());
                    emission.setYear(updatedEmission.getYear());
                    emission.setGasUnit(updatedEmission.getGasUnit());
                    emission.setValue(updatedEmission.getValue());
                    Emission savedEmission = emissionRepository.save(emission);
                    return ResponseEntity.ok(savedEmission);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEmission(@PathVariable Long id) {
        return emissionRepository.findById(id)
                .map(emission -> {
                    emissionRepository.delete(emission);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/parse-xml")
    public ResponseEntity<String> parseXML() {
        try {
            String filePath = "src/main/resources/GreenhouseGasEmission.xml"; 
            System.out.println("Starting XML Parsing for file: " + filePath);
            xmlParser.parseAndSaveEmissions(filePath);
            return ResponseEntity.ok("XML parsing and data import completed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during XML parsing: " + e.getMessage());
        }
    }


    @PostMapping("/parse-json")
    public ResponseEntity<String> parseJson() {
        try {
            String filePath = "src/main/resources/GreenhouseGasEmissions.json";
            jsonParser.parse(filePath);
            return ResponseEntity.ok("JSON Parsing Completed and Data Saved Successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during JSON parsing: " + e.getMessage());
        }
    }
}
