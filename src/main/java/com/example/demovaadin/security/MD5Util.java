package com.example.demovaadin.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import com.example.demovaadin.util.Utility;

public class MD5Util {

    public static byte[] digest(String data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        return md.digest(data.getBytes(StandardCharsets.UTF_8));
    }

    // return hash in hex format
    public static String hash(String data) throws NoSuchAlgorithmException {
        // 1.decrypt token sha256 here
        byte[] bytes = digest(data);
        // 2.then compare with requestBody if match
        return bytesToHex(bytes);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static void validateSignature(String signature, String md5Body)
            throws NoSuchAlgorithmException, ResponseStatusException {
        if (Utility.isEmpty(signature))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid signature");

        String hex = hash(md5Body);
        boolean isCorrect = hex.equals(signature);

        if (!isCorrect) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Signature mismatch");
        }

    }
}
