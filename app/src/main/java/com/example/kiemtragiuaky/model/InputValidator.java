package com.example.kiemtragiuaky.model;

import android.util.Patterns;

public class

InputValidator {

    // Phương thức kiểm tra xem một chuỗi có rỗng không
    public static boolean isNullOrEmpty(String input) {
        return input == null || input.trim().isEmpty();
    }

    // Mật khẩu chưa ít nhất 8 kí tự
    public static boolean isValidPassword(String password) {
        return password.length() >= 8;
    }

    // Phương thức kiểm tra xem một chuỗi có là email hợp lệ không
    public static boolean isValidEmail(String email) {
        return !isNullOrEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Phương thức kiểm tra xem một chuỗi có là số điện thoại hợp lệ không
    public static boolean isValidPhoneNumber(String phoneNumber) {
        return !isNullOrEmpty(phoneNumber) && Patterns.PHONE.matcher(phoneNumber).matches();
    }
}