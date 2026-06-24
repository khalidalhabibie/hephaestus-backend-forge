package com.adnan.loanappspringsql.utils;

import java.util.StringJoiner;

public class LogUtil {
  public static String format(String event) {
    return "event_name=" + event;
  }

  public static String format(String event, Object... keyValues) {
    StringJoiner joiner = new StringJoiner(" ");
    joiner.add("event_name=" + event);
    for (int i = 0; i < keyValues.length; i += 2) {
      joiner.add(keyValues[i] + "=" + keyValues[i + 1]);
    }
    return joiner.toString();
  }
}