package com.realestate.realestateportal.service;

import com.realestate.realestateportal.model.Admin;
import com.realestate.realestateportal.repository.AdminRepository;

import java.util.List;
import java.util.UUID;

public class AdminService {


    private final AdminRepository adminRepository =
            new AdminRepository();

    // Register Admin
    public boolean registerAdmin(String name, String email,
                                 String password, String accessLevel) {

        if (name == null || name.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
            return false;
        }

        if (adminRepository.findByEmail(email.trim()) != null) {
            return false;
        }

        String adminId = "ADM-" + UUID.randomUUID();

        Admin admin = new Admin(
                adminId,
                name.trim(),
                email.trim(),
                password,
                accessLevel == null ? "ADMIN" : accessLevel
        );

        return adminRepository.saveAdmin(admin);
    }

    // Get All Admins
    public List<Admin> getAllAdmins() {
        return adminRepository.getAllAdmins();
    }

    // Find Admin By Email
    public Admin findAdminByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

    // Update Admin Profile
    public boolean updateAdminProfile(String adminId,
                                      String name,
                                      String email) {

        if (adminId == null || name == null ||
                name.trim().isEmpty() || email == null ||
                email.trim().isEmpty()) {
            return false;
        }

        Admin admin = null;

        for (Admin current : adminRepository.getAllAdmins()) {
            if (current.getAdminId().equals(adminId)) {
                admin = current;
                break;
            }
        }

        if (admin == null) {
            return false;
        }

        Admin existing = adminRepository.findByEmail(email.trim());

        if (existing != null &&
                !existing.getAdminId().equals(adminId)) {
            return false;
        }

        admin.setName(name.trim());
        admin.setEmail(email.trim());

        return adminRepository.updateAdmin(admin);
    }

    // Update Admin Access Level
    public boolean updateAccessLevel(String adminId,
                                     String accessLevel) {

        if (adminId == null || accessLevel == null ||
                accessLevel.trim().isEmpty()) {
            return false;
        }

        for (Admin admin : adminRepository.getAllAdmins()) {

            if (admin.getAdminId().equals(adminId)) {

                admin.setAccessLevel(accessLevel.trim());

                return adminRepository.updateAdmin(admin);
            }
        }

        return false;
    }

    // Delete Admin
    public boolean deleteAdmin(String adminId) {

        if (adminId == null || adminId.trim().isEmpty()) {
            return false;
        }

        return adminRepository.deleteAdmin(adminId);
    }


}
