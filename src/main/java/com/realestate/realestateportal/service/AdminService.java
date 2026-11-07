
package com.realestate.realestateportal.service;

import com.realestate.realestateportal.model.Admin;
import com.realestate.realestateportal.repository.AdminRepository;

import java.util.List;
import java.util.UUID;

public class AdminService {

    private final AdminRepository adminRepository =
            new AdminRepository();

    // Available Admin Roles
    public static final String ADMIN = "ADMIN";
    public static final String SUPER_ADMIN = "SUPER_ADMIN";

    // Validate Admin Role
    private boolean isValidRole(String accessLevel) {

        return accessLevel != null &&
                (accessLevel.equalsIgnoreCase(ADMIN) ||
                        accessLevel.equalsIgnoreCase(SUPER_ADMIN));
    }

    // 1. Register Admin
    public boolean registerAdmin(String name,
                                 String email,
                                 String password,
                                 String accessLevel) {

        if (name == null || name.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
            return false;
        }

        String cleanEmail = email.trim().toLowerCase();

        // Check whether email already exists
        if (adminRepository.findByEmail(cleanEmail) != null) {
            return false;
        }

        // New registrations receive ADMIN role by default.
        // SUPER_ADMIN should be assigned only through a
        // protected administrative process.
        String role = ADMIN;

        String adminId = "ADM-" + UUID.randomUUID();

        Admin admin = new Admin(
                adminId,
                name.trim(),
                cleanEmail,
                password,
                role
        );

        return adminRepository.saveAdmin(admin);
    }

    // 2. Get All Admins
    public List<Admin> getAllAdmins() {

        return adminRepository.getAllAdmins();
    }

    // 3. Find Admin By Email
    public Admin findAdminByEmail(String email) {

        if (email == null || email.trim().isEmpty()) {
            return null;
        }

        return adminRepository.findByEmail(
                email.trim().toLowerCase()
        );
    }

    // 4. Find Admin By ID
    public Admin findAdminById(String adminId) {

        if (adminId == null || adminId.trim().isEmpty()) {
            return null;
        }

        for (Admin admin : adminRepository.getAllAdmins()) {

            if (admin.getAdminId() != null &&
                    admin.getAdminId().equals(adminId)) {
                return admin;
            }
        }

        return null;
    }

    // 5. Update Admin Profile
    public boolean updateAdminProfile(String adminId,
                                      String name,
                                      String email) {

        if (adminId == null || adminId.trim().isEmpty() ||
                name == null || name.trim().isEmpty() ||
                email == null || email.trim().isEmpty()) {
            return false;
        }

        Admin admin = findAdminById(adminId);

        if (admin == null) {
            return false;
        }

        String cleanEmail = email.trim().toLowerCase();

        // Prevent duplicate email addresses
        Admin existingAdmin = findAdminByEmail(cleanEmail);

        if (existingAdmin != null &&
                !existingAdmin.getAdminId().equals(adminId)) {
            return false;
        }

        admin.setName(name.trim());
        admin.setEmail(cleanEmail);

        return adminRepository.updateAdmin(admin);
    }

    // 6. Update Admin Access Level
    public boolean updateAccessLevel(String adminId,
                                     String accessLevel) {

        if (adminId == null || adminId.trim().isEmpty() ||
                !isValidRole(accessLevel)) {
            return false;
        }

        Admin admin = findAdminById(adminId);

        if (admin == null) {
            return false;
        }

        admin.setAccessLevel(accessLevel.trim().toUpperCase());

        return adminRepository.updateAdmin(admin);
    }

    // 7. Delete Admin
    public boolean deleteAdmin(String adminId) {

        if (adminId == null || adminId.trim().isEmpty()) {
            return false;
        }

        // Check whether the Admin exists
        if (findAdminById(adminId) == null) {
            return false;
        }

        return adminRepository.deleteAdmin(adminId);
    }
}
