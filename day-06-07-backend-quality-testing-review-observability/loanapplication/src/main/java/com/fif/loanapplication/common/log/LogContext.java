package com.fif.loanapplication.common.log;

import org.slf4j.MDC;

public class LogContext {

    private LogContext() {
    }

    public static String getCorrelationId() {
        return MDC.get("correlation_id");
    }
}