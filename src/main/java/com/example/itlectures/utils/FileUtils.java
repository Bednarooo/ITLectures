package com.example.itlectures.utils;

import com.example.itlectures.model.Lecture;
import com.example.itlectures.model.User;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class FileUtils {
  public static void saveNotificationToFile(String path, Lecture savedLecture, User user) throws FileNotFoundException {
    String startTimeMinutes = String.format("%02d", savedLecture.getStartTime().getMinute());
    LocalDateTime sendDateTime = LocalDateTime.now();
    String sendTimeMinutes = String.format("%02d", sendDateTime.getMinute());
    String notification =
        "Data wysłania: " + sendDateTime.getDayOfMonth()
            + "." + sendDateTime.getMonth() + "." + sendDateTime.getYear() + "r. "
            + sendDateTime.getHour() + ":" + sendTimeMinutes
            + "\nGratulacje " + user.getLogin() + "!"
            + "\nUdało Ci się zapisać na poniższy wykład:"
            + "\nTytuł: " + savedLecture.getTitle()
            + "\nData rozpoczęcia: " + savedLecture.getStartTime().getDayOfMonth()
            + "." + savedLecture.getStartTime().getMonth() + "." + savedLecture.getStartTime().getYear() + "r. "
            + savedLecture.getStartTime().getHour() + ":" + startTimeMinutes
            + "\nData zakończenia: " + savedLecture.getEndTime().getDayOfMonth()
            + "." + savedLecture.getEndTime().getMonth() + "." + savedLecture.getEndTime().getYear() + "r. "
            + savedLecture.getEndTime().getHour() + ":" + savedLecture.getEndTime().getMinute();
    try (PrintWriter printWriter = new PrintWriter(path)) {
      printWriter.println(notification);
    }
  }
}
