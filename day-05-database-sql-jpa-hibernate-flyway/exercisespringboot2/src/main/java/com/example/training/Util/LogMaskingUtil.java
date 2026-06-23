package com.example.training.Util;

public final class LogMaskingUtil {

    private LogMaskingUtil() {}

    public static String maskNik(String nik) {
        if (nik == null || nik.length() < 4) {
            return "****";
        }
        return "****" + nik.substring(nik.length() - 4);
    }

    public static String maskEmail(String email) {
        if (email == null || !email.contains("@")) {
            return "****";
        }
        String[] parts = email.split("@");
        if (parts[0].length() <= 1) {
            return "*@" + parts[1];
        }
        return parts[0].charAt(0) + "***@" + parts[1];
    }

    public static String maskPhone(String phone) {
        if (phone == null || phone.length() < 4) {
            return "****";
        }
        return "****" + phone.substring(phone.length() - 4);
    }

    public static String maskName(String fullName) {
        if (fullName == null || fullName.isBlank()) {
            return "****";
        }
        String[] parts = fullName.trim().split("\\s+");
        if (parts.length == 1) {
            return parts[0].charAt(0) + "***";
        }
        return parts[0].charAt(0) + "*** " + parts[parts.length - 1];
    }
}