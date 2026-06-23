package com.example.spring_boot_database.util;

public class LogSanitizerUtil {

    private LogSanitizerUtil() {}

    public static String maskNik(String nik) {
        if (nik == null || nik.length() < 4) return "****";
        return "****" + nik.substring(nik.length() - 4);
    }

    public static String maskEmail(String email) {
        if (email == null || !email.contains("@")) return "****";

        String[] parts = email.split("@");
        String name = parts[0];
        String domain = parts[1];

        if (name.length() <= 2) return "***@" + domain;

        return name.substring(0, 2) + "***@" + domain;
    }

    public static String maskPhone(String phone) {
        if (phone == null || phone.length() < 4) return "****";
        return "****" + phone.substring(phone.length() - 4);
    }

    public static String maskName(String name) {
        if (name == null || name.length() < 2) return "*";
        return name.charAt(0) + "***";
    }

    // ===== Generic Mask (keep last n chars) =====
    public static String maskKeepLast(String value, int visibleChars) {
        if (value == null || value.length() <= visibleChars) return "****";

        String visiblePart = value.substring(value.length() - visibleChars);
        return "****" + visiblePart;
    }
}
