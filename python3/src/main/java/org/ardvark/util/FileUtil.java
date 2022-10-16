package org.ardvark.util;

import java.io.IOException;
import java.io.InputStream;

public class FileUtil {

  public InputStream fromResource(String filename) {
    return getClass()
        .getClassLoader()
        .getResourceAsStream(filename);
  }

  public String fromResourceAsString(String filename) {
    int pos = 0;
    try (InputStream is = fromResource(filename)) {
      if(is == null){
        throw new RuntimeException(String.format(
            "File not found: %s", filename));
      }
      StringBuilder buf = new StringBuilder();
      int ch = 0;
      do {
        ch = is.read();
        if (ch != -1) {
          pos++;
          buf.append((char) ch);
        }
      } while (ch != -1);
      return buf.toString();
    } catch (IOException e) {
      throw new RuntimeException(String
          .format("Error at pos: %d", pos), e);
    }
  }
}
