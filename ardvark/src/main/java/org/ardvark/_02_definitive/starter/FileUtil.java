package org.ardvark._02_definitive.starter;


import java.io.InputStream;

public class FileUtil {
  public InputStream fromResource(String filename) {
    return getClass()
        .getClassLoader()
        .getResourceAsStream(filename);
  }
}
