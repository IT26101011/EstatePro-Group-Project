
package com.realestate.realestateportal.controller;

import com.realestate.realestateportal.model.Admin;
import com.realestate.realestateportal.service.AdminService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
@CrossOrigin
public class AdminController {

    private final AdminService adminService = new AdminService();

    // 1. Register a new Admin
    @PostMapping("/register")
    public String registerAdmin(@RequestBody Admin admin) {

        boolean result = adminService.registerAdmin(
                admin.getName(),
                admin.getEmail(),
                admin.getPassword(),
                admin.getAccessLevel()
        );

        if (result) {
            return "Admin registered successfully!";
        }

        return "Admin registration failed. Check the details or email.";
    }

    // 2. Get all Admins
    @GetMapping
    public List<Admin> getAllAdmins() {

        return adminService.getAllAdmins();
    }

    // 3. Find Admin by Email
    @GetMapping("/email/{email}")
    public Admin findAdminByEmail(@PathVariable String email) {

        return adminService.findAdminByEmail(email);
    }

    // 4. Find Admin by ID
    @GetMapping("/{adminId}")
    public Admin findAdminById(@PathVariable String adminId) {

        return adminService.findAdminById(adminId);
    }

    // 5. Update Admin Profile
    @PutMapping("/{adminId}")
    public String updateAdminProfile(
            @PathVariable String adminId,
            @RequestBody Admin admin) {

        boolean result = adminService.updateAdminProfile(
                adminId,
                admin.getName(),
                admin.getEmail()
        );

        return result
                ? "Admin profile updated successfully!"
                : "Admin profile update failed.";
    }

    // 6. Update Admin Role
    @PutMapping("/{adminId}/role")
    public String updateAdminRole(
            @PathVariable String adminId,
            @RequestBody Admin admin) {

        boolean result = adminService.updateAccessLevel(
                adminId,
                admin.getAccessLevel()
        );

        return result
                ? "Admin role updated successfully!"
                : "Admin role update failed.";
    }

    // 7. Delete Admin
    @DeleteMapping("/{adminId}")
    public String deleteAdmin(@PathVariable String adminId) {

        boolean result = adminService.deleteAdmin(adminId);

        return result
                ? "Admin deleted successfully!"
                : "Admin deletion failed.";
    }
}
