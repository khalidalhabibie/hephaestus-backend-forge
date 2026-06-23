package com.fif.exercise2.util;

/**
 * Utility untuk menyembunyikan data sensitif (PII) sebelum masuk ke log.
 *
 * Kenapa mask, bukan skip?
 *   - NIK/phone yang di-mask (misal 3273****8901) masih bisa dipakai debugging
 *     ("kira-kira customer dari wilayah mana?") tanpa membocorkan data lengkap.
 *   - Password dan token di-skip total karena tidak ada nilai debug-nya di log.
 *
 * Kenapa bukan hash?
 *   - Hash (SHA/MD5) berguna jika perlu korelasi antar sistem,
 *     tapi untuk log aplikasi, mask sudah cukup dan lebih mudah dibaca.
 */
public final class PiiMaskingUtil {

    private PiiMaskingUtil() {
        // utility class — tidak boleh di-instantiate
    }

    /**
     * Mask NIK: tampilkan 4 karakter pertama dan 4 terakhir.
     * Contoh: 3273012345678901 → 3273********8901
     */
    public static String maskNik(String nik) {
        if (nik == null || nik.length() < 8) return "****";
        return nik.substring(0, 4) + "********" + nik.substring(nik.length() - 4);
    }

    /**
     * Mask nomor telepon: tampilkan 4 karakter pertama dan 2 terakhir.
     * Contoh: 081234567890 → 0812****90
     */
    public static String maskPhone(String phone) {
        if (phone == null || phone.length() < 6) return "****";
        return phone.substring(0, 4) + "****" + phone.substring(phone.length() - 2);
    }

    /**
     * Mask email: sembunyikan bagian sebelum '@', kecuali karakter pertama.
     * Contoh: user@example.com → u***@example.com
     */
    public static String maskEmail(String email) {
        if (email == null || !email.contains("@")) return "****";
        int atIndex = email.indexOf('@');
        if (atIndex <= 1) return "***" + email.substring(atIndex);
        return email.charAt(0) + "***" + email.substring(atIndex);
    }
}