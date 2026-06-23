package com.fif.finance_training.web;

import org.slf4j.MDC;

public class CorrelationIdContext {

    public static String get() {
        String id = MDC.get("correlationId");
        return id != null ? id : "unknown";
    }

    public static void set(String correlationId) {
        MDC.put("correlationId", correlationId);
    }

    public static void clear() {
        MDC.remove("correlationId");
    }
}