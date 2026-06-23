package com.fif.loanapplication.common.log;

public class LogMaskingUtil {

    private LogMaskingUtil() {
    }

    public static String maskNik(String nik) {
        if (nik == null || nik.isBlank()) {
            return "****";
        }

        if (nik.length() <= 4) {
            return "****";
        }

        return "************" + nik.substring(nik.length() - 4);
    }

    public static String maskEmail(String email) {
        if (email == null || email.isBlank()) {
            return "****";
        }

        int atIndex = email.indexOf("@");

        if (atIndex <= 1) {
            return "****";
        }

        return email.substring(0, 1) + "***" + email.substring(atIndex);
    }

    public static String maskPhone(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            return "****";
        }

        if (phoneNumber.length() <= 4) {
            return "****";
        }

        return "********" + phoneNumber.substring(phoneNumber.length() - 4);
    }
}