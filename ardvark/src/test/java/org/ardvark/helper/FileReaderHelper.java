package org.ardvark.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.stream.Collectors;

public class FileReaderHelper {

  public String fileAsString(String filepath) {
    try (BufferedReader br = new BufferedReader(
        new FileReader(new File(filepath)))) {
      return br.lines().collect(Collectors.joining());
    } catch (Exception e){
      throw new RuntimeException(String
          .format("IO Error: %s", filepath),e);
    }
  }
}
