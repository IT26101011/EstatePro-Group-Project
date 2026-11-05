package com.realestate.realestateportal.service;

import com.realestate.realestateportal.model.Property;
import com.realestate.realestateportal.repository.PropertyRepository;

import java.util.List;
import java.util.UUID;

public class PropertyService {

    private final PropertyRepository propertyRepository =
            new PropertyRepository();

    // Create Property
    public String createProperty(Property property) {

        if (property.getTitle() == null || property.getTitle().isBlank() ||
                property.getPropertyType() == null || property.getPropertyType().isBlank() ||
                property.getListingType() == null || property.getListingType().isBlank() ||
                property.getLocation() == null || property.getLocation().isBlank() ||
                property.getDescription() == null || property.getDescription().isBlank()) {

            return "Please fill all required fields.";
        }

        if (property.getPrice() <= 0) {
            return "Invalid property price.";
        }

        if (property.getBedrooms() < 0 ||
                property.getBathrooms() < 0 ||
                property.getArea() <= 0) {

            return "Invalid property details.";
        }

        property.setPropertyId(
                "PRP-" + UUID.randomUUID().toString().substring(0, 8)
        );

        if (propertyRepository.saveProperty(property)) {
            return "Property created successfully!";
        }

        return "Failed to create property.";
    }

    // Get All Properties
    public List<Property> getAllProperties() {
        return propertyRepository.getAllProperties();
    }

    // Get Properties By Agent
    public List<Property> getPropertiesByAgent(String email) {
        return propertyRepository.findByAgentEmail(email);
    }

    // Get Property By ID
    public Property getPropertyById(String propertyId) {
        return propertyRepository.findById(propertyId);
    }

    // Update Property
    public String updateProperty(Property property) {

        if (property.getTitle() == null || property.getTitle().isBlank() ||
                property.getPropertyType() == null || property.getPropertyType().isBlank() ||
                property.getListingType() == null || property.getListingType().isBlank() ||
                property.getLocation() == null || property.getLocation().isBlank() ||
                property.getDescription() == null || property.getDescription().isBlank()) {

            return "Please fill all required fields.";
        }

        if (property.getPrice() <= 0 ||
                property.getBedrooms() < 0 ||
                property.getBathrooms() < 0 ||
                property.getArea() <= 0) {

            return "Invalid property details.";
        }

        if (propertyRepository.updateProperty(property)) {
            return "Property updated successfully!";
        }

        return "Failed to update property.";
    }

    // Delete Property
    public String deleteProperty(String propertyId) {

        if (propertyRepository.deleteProperty(propertyId)) {
            return "Property deleted successfully!";
        }

        return "Property not found.";
    }
}