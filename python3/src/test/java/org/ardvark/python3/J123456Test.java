package org.ardvark.python3;

import org.ardvark.testutil.TestFileUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class J123456Test {

  private final TestFileUtil fileUtil = new TestFileUtil();

  @Test
  public void shouldJ123456WhenValid() {
    Python3Utils utils = new Python3Utils();
    String source = inputString();
    String actual = utils.treeString(source);
    String expected = expectedAssignmentWhenValid();
    assertEquals(expected, actual);
  }

  private String inputString() {
    String filename = "python3/J123456test.py";
    return fileUtil.fromResourceAsString(filename);
  }

  private String expectedAssignmentWhenValid() {
    String filename = "python3/J123456test.tree.txt";
    return fileUtil.fromResourceAsString(filename);
  }
}
