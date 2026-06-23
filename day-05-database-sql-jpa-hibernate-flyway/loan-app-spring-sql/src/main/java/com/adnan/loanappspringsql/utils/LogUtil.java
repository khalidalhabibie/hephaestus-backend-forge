package com.adnan.loanappspringsql.utils;

import java.util.StringJoiner;

public class LogUtil {
  public static String format(String event) {
    return "event=" + event;
  }

  public static String format(String event, Object... keyValues) {
    StringJoiner joiner = new StringJoiner(" ");
    joiner.add("event=" + event);
    for (int i = 0; i < keyValues.length; i += 2) {
      joiner.add(keyValues[i] + "=" + keyValues[i + 1]);
    }
    return joiner.toString();
  }
}