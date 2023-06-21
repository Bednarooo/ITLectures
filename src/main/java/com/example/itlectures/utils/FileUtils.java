package com.example.itlectures.utils;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class FileUtils {
  public static void saveNotificationToFile(String path, String notification) throws FileNotFoundException {
    try (PrintWriter printWriter = new PrintWriter(path)) {
      printWriter.println(notification);
    }
  }
}
