package com.project.dao;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/*
 * Amit Kumar Modak 
 */
public class PasswordProtect {

    public String encryptPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            // Convert byte array to hexadecimal representation
            StringBuilder hexString = new StringBuilder(2 * encodedHash.length);
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            // Return a shorter representation of the hash (e.g., first 16 characters)
            return hexString.toString().substring(0, 16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            // Handle exception
            return null;
        }
    }
    
    boolean checkPassword(String inputPassword, String encryptedPassword) {
        // Encrypt the input password and compare it with the stored encrypted password
        String encryptedInputPassword = encryptPassword(inputPassword);
        return encryptedInputPassword.equals(encryptedPassword);
    }

//    public static void main(String[] args) {
//        String originalPassword = "mySecretPassword";
//
//        // Encrypt the password
//        PasswordProtect pp =new PasswordProtect();
//        String encryptedPassword = pp.encryptPassword(originalPassword);
//        System.out.println(pp.checkPassword("mySecret Password", encryptedPassword));
//        System.out.println("Encrypted Password: " + encryptedPassword);
//    }
}
