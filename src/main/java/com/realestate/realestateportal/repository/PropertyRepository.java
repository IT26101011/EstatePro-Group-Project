package com.realestate.realestateportal.repository;

import com.realestate.realestateportal.model.Property;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PropertyRepository {

    private static final String FILE_PATH = "data/properties.txt";

    // Save Property
    public boolean saveProperty(Property property) {

        try {
            File file = new File(FILE_PATH);

            File parent = file.getParentFile();

            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }

            try (BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(file, true),
                            StandardCharsets.UTF_8))) {

                writer.write(convertToLine(property));
                writer.newLine();
            }

            return true;

        } catch (IOException e) {
            System.out.println("Error saving property: " + e.getMessage());
            return false;
        }
    }

    // Get All Properties
    public List<Property> getAllProperties() {

        List<Property> properties = new ArrayList<>();

        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return properties;
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(file),
                        StandardCharsets.UTF_8))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split("\\|", -1);

                if (parts.length == 11) {

                    try {

                        Property property = new Property(
                                parts[0],
                                parts[1],
                                parts[2],
                                parts[3],
                                parts[4],
                                parts[5],
                                Double.parseDouble(parts[6]),
                                Integer.parseInt(parts[7]),
                                Integer.parseInt(parts[8]),
                                Double.parseDouble(parts[9]),
                                parts[10]
                        );

                        properties.add(property);

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid property data: " + line);
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading properties: " + e.getMessage());
        }

        return properties;
    }

    // Find Property By ID
    public Property findById(String propertyId) {

        for (Property property : getAllProperties()) {

            if (property.getPropertyId().equalsIgnoreCase(propertyId)) {
                return property;
            }
        }

        return null;
    }

    // Get Properties By Agent Email
    public List<Property> findByAgentEmail(String email) {

        List<Property> agentProperties = new ArrayList<>();

        for (Property property : getAllProperties()) {

            if (property.getAgentEmail().equalsIgnoreCase(email)) {
                agentProperties.add(property);
            }
        }

        return agentProperties;
    }

    // Update Property
    public boolean updateProperty(Property updatedProperty) {

        List<Property> properties = getAllProperties();

        boolean found = false;

        for (int i = 0; i < properties.size(); i++) {

            if (properties.get(i).getPropertyId()
                    .equalsIgnoreCase(updatedProperty.getPropertyId())) {

                properties.set(i, updatedProperty);
                found = true;
                break;
            }
        }

        if (!found) {
            return false;
        }

        return rewriteFile(properties);
    }

    // Delete Property
    public boolean deleteProperty(String propertyId) {

        List<Property> properties = getAllProperties();

        boolean removed = properties.removeIf(
                property -> property.getPropertyId()
                        .equalsIgnoreCase(propertyId)
        );

        if (!removed) {
            return false;
        }

        return rewriteFile(properties);
    }

    // Rewrite File
    private boolean rewriteFile(List<Property> properties) {

        try {

            File file = new File(FILE_PATH);

            File parent = file.getParentFile();

            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }

            try (BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(file, false),
                            StandardCharsets.UTF_8))) {

                for (Property property : properties) {

                    writer.write(convertToLine(property));
                    writer.newLine();
                }
            }

            return true;

        } catch (IOException e) {
            System.out.println("Error updating property file: " + e.getMessage());
            return false;
        }
    }

    // Convert Property Object To Text Line
    private String convertToLine(Property property) {

        return property.getPropertyId() + "|" +
                property.getAgentEmail() + "|" +
                property.getTitle() + "|" +
                property.getPropertyType() + "|" +
                property.getListingType() + "|" +
                property.getLocation() + "|" +
                property.getPrice() + "|" +
                property.getBedrooms() + "|" +
                property.getBathrooms() + "|" +
                property.getArea() + "|" +
                property.getDescription();
    }
}