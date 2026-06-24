package com.adnan.loanappspringsql.utils;

public class SensitiveDataLogUtil {
    public static String maskEmail(String email) {
        if (email == null || !email.contains("@")) {
            return "***";
        }
        String[] parts = email.split("@");
        String name = parts[0];
        if (name.length() <= 2) {
            return "***@" + parts[1];
        }
        return name.substring(0, 2) + "***@" + parts[1];
    }

    public static String maskNik(String nik) {
        if (nik == null || nik.length() < 4) {
            return "****";
        }
        return "************" + nik.substring(nik.length() - 4);
    }

    public static String maskPhone(String phone) {
        if (phone == null || phone.length() < 4) {
            return "****";
        }
        return "********" + phone.substring(phone.length() - 4);
    }

}
