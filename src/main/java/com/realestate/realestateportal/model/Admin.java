
package com.realestate.realestateportal.model;

public class Admin {

    private String adminId;
    private String name;
    private String email;
    private String password;
    private String accessLevel;

    // Default Constructor
    public Admin() {
    }

    // Parameterized Constructor
    public Admin(String adminId, String name, String email,
                 String password, String accessLevel) {

        this.adminId = adminId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.accessLevel = accessLevel;
    }

    // Getters and Setters

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    // Check whether Admin is a Super Admin
    public boolean isSuperAdmin() {
        return "SUPER_ADMIN".equalsIgnoreCase(accessLevel);
    }

    // Check whether Admin is a Normal Admin
    public boolean isNormalAdmin() {
        return "ADMIN".equalsIgnoreCase(accessLevel);
    }
}
