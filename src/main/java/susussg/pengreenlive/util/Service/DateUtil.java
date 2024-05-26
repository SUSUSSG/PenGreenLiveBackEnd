package susussg.pengreenlive.util.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {
  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");

  public static LocalDateTime parseToLocalDateTime(String dateStr) {
    try {
      LocalDate localDate = LocalDate.parse(dateStr, DATE_FORMATTER);
      return localDate.atStartOfDay(); // LocalDateTime으로 변환
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException("Invalid date format: " + dateStr);
    }
  }
}
