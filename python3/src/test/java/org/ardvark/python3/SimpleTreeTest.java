package org.ardvark.python3;

import org.ardvark.testutil.TestFileUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleTreeTest {

  private final TestFileUtil fileUtil = new TestFileUtil();

  @Test
  public void shouldTreeWhenValid() {
    Python3Utils utils = new Python3Utils();
    String source = inputString();
    String actual = utils.treeString(source);
    String expected = expectedAssignmentWhenValid();
    assertEquals(expected, actual);
  }

  private String inputString() {
    String filename = "python3/simple.py";
    return fileUtil.fromResourceAsString(filename);
  }

  private String expectedAssignmentWhenValid() {
    String filename = "python3/simple.tree.txt";
    return fileUtil.fromResourceAsString(filename);
  }
}
