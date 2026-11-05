package com.realestate.realestateportal.service;

import com.realestate.realestateportal.model.Agent;
import com.realestate.realestateportal.repository.AgentRepository;

import java.util.List;
import java.util.UUID;

public class AgentService {


    private final AgentRepository agentRepository = new AgentRepository();

    // Create Agent Profile
    public String createAgentProfile(Agent agent) {

        if (agent.getEmail() == null || agent.getEmail().isBlank() ||
                agent.getAgencyName() == null || agent.getAgencyName().isBlank() ||
                agent.getSpecialization() == null || agent.getSpecialization().isBlank() ||
                agent.getLocation() == null || agent.getLocation().isBlank()) {

            return "Please fill all required fields.";
        }

        if (agent.getExperienceYears() < 0) {
            return "Invalid experience years.";
        }

        if (agentRepository.findByEmail(agent.getEmail()) != null) {
            return "Agent profile already exists.";
        }

        String agentId = "AGT-" +
                UUID.randomUUID().toString().substring(0, 8);

        agent.setAgentId(agentId);
        agent.setRating(0.0);

        if (agentRepository.saveAgent(agent)) {
            return "Agent profile created successfully!";
        }

        return "Failed to create agent profile.";
    }

    // Get All Agents
    public List<Agent> getAllAgents() {

        return agentRepository.getAllAgents();
    }

    // Find Agent by ID
    public Agent getAgentById(String agentId) {

        return agentRepository.findById(agentId);
    }

    // Find Agent by Email
    public Agent getAgentByEmail(String email) {

        return agentRepository.findByEmail(email);
    }

    // Update Agent Profile
    public String updateAgentProfile(Agent agent) {

        if (agentRepository.findById(agent.getAgentId()) == null) {
            return "Agent not found.";
        }

        if (agent.getExperienceYears() < 0) {
            return "Invalid experience years.";
        }

        if (agentRepository.updateAgent(agent)) {
            return "Agent profile updated successfully!";
        }

        return "Failed to update agent profile.";
    }

    // Update Agent Average Rating
    public boolean updateAgentRating(String agentEmail, double rating) {

        Agent agent = agentRepository.findByEmail(agentEmail);

        if (agent == null) {
            return false;
        }

        agent.setRating(rating);

        return agentRepository.updateAgent(agent);
    }

    // Delete Agent Profile
    public String deleteAgentProfile(String agentId) {

        if (agentRepository.findById(agentId) == null) {
            return "Agent not found.";
        }

        if (agentRepository.deleteAgent(agentId)) {
            return "Agent profile deleted successfully!";
        }

        return "Failed to delete agent profile.";
    }

}
