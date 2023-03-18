package com.example.userservice.auth;

import java.util.Base64;

public class StringToBase64Encoder {

    public static String encode(String plainText) {
        return Base64.getEncoder().encodeToString(plainText.getBytes());
    }
}
