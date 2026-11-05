package com.realestate.realestateportal.repository;

import com.realestate.realestateportal.model.Admin;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class AdminRepository {

    private static final String FILE_PATH = "data/admins.txt";

    // Create file if it does not exist
    private void createFile() throws IOException {

        File file = new File(FILE_PATH);

        File parent = file.getParentFile();

        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        if (!file.exists()) {
            file.createNewFile();
        }
    }

    // Save Admin
    public boolean saveAdmin(Admin admin) {

        if (findByEmail(admin.getEmail()) != null) {
            return false;
        }

        try {
            createFile();

            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(FILE_PATH, true),
                            StandardCharsets.UTF_8
                    )
            );

            writer.write(convertToLine(admin));
            writer.newLine();
            writer.close();

            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get All Admins
    public List<Admin> getAllAdmins() {

        List<Admin> admins = new ArrayList<>();

        try {
            createFile();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(FILE_PATH),
                            StandardCharsets.UTF_8
                    )
            );

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split("\\|", -1);

                if (data.length == 5) {

                    Admin admin = new Admin(
                            data[0],
                            data[1],
                            data[2],
                            data[3],
                            data[4]
                    );

                    admins.add(admin);
                }
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return admins;
    }

    // Find Admin By Email
    public Admin findByEmail(String email) {

        for (Admin admin : getAllAdmins()) {

            if (admin.getEmail().equalsIgnoreCase(email)) {
                return admin;
            }
        }

        return null;
    }

    // Update Admin
    public boolean updateAdmin(Admin updatedAdmin) {

        List<Admin> admins = getAllAdmins();

        boolean found = false;

        for (int i = 0; i < admins.size(); i++) {

            if (admins.get(i).getAdminId()
                    .equals(updatedAdmin.getAdminId())) {

                admins.set(i, updatedAdmin);
                found = true;
                break;
            }
        }

        if (!found) {
            return false;
        }

        return rewriteFile(admins);
    }

    // Delete Admin
    public boolean deleteAdmin(String adminId) {

        List<Admin> admins = getAllAdmins();

        boolean removed = admins.removeIf(
                admin -> admin.getAdminId().equals(adminId)
        );

        if (!removed) {
            return false;
        }

        return rewriteFile(admins);
    }

    // Rewrite File
    private boolean rewriteFile(List<Admin> admins) {

        try {
            createFile();

            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(FILE_PATH),
                            StandardCharsets.UTF_8
                    )
            );

            for (Admin admin : admins) {
                writer.write(convertToLine(admin));
                writer.newLine();
            }

            writer.close();

            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Convert Admin Object Into Text
    private String convertToLine(Admin admin) {

        return admin.getAdminId() + "|" +
                admin.getName() + "|" +
                admin.getEmail() + "|" +
                admin.getPassword() + "|" +
                admin.getAccessLevel();
    }
}
