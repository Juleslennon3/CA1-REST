package com.greenhouse.parser;

import com.greenhouse.model.Emission;
import com.greenhouse.repository.EmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

@Component
public class XMLParser {

    @Autowired
    private EmissionRepository emissionRepository;

    public void parseAndSaveEmissions(String filePath) {
        try {
            File file = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("Row");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    // Extract data with null checks
                    String category = getElementTextContent(element, "Category__1_3");
                    String scenario = getElementTextContent(element, "Scenario");
                    String yearText = getElementTextContent(element, "Year");
                    String valueText = getElementTextContent(element, "Value");
                    String gasUnit = getElementTextContent(element, "Gas___Units");

                    // Validate extracted data
                    if (category != null && scenario != null && yearText != null && valueText != null) {
                        try {
                            int year = Integer.parseInt(yearText.trim());
                            double value = Double.parseDouble(valueText.trim());

                            if (value > 0 && "WEM".equalsIgnoreCase(scenario.trim()) && year == 2023) {
                                Emission emission = new Emission(category.trim(), scenario.trim(), year, gasUnit != null ? gasUnit.trim() : null, value);
                                emissionRepository.save(emission);
                                System.out.println("Saved emission: " + emission);
                            }
                        } catch (NumberFormatException e) {
                            System.err.println("Error parsing year or value in row " + i + ": " + e.getMessage());
                        }
                    } else {
                        System.err.println("Missing required data in row " + i);
                    }
                }
            }
            System.out.println("XML Parsing Completed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getElementTextContent(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        if (nodeList != null && nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return null;
    }
}
