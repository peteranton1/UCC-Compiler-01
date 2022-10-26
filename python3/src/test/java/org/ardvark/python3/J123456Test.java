package org.ardvark.python3;

import org.antlr.v4.runtime.tree.ParseTree;
import org.ardvark.ast.AstBuilder;
import org.ardvark.ast.AstNode;
import org.ardvark.ast.AstTestBase;
import org.ardvark.testutil.TestFileUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class J123456Test extends AstTestBase {

  private final TestFileUtil fileUtil = new TestFileUtil();

  @Test
  public void shouldJ123456WhenValidToCST() {
    Python3Utils utils = new Python3Utils();
    String source = inputString();
    String actual = utils.treeString(source);
    String expected = expectedCST();
    assertEquals(expected, actual);
  }

  @Test
  public void shouldJ123456WhenValidToAST() {
    Python3Utils utils = new Python3Utils();
    String source = inputString();
    ParseTree tree = parseSource(source);
    //printTree(input);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = expectedAST();
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }

  private String inputString() {
    String filename = "python3/J123456test.py";
    return fileUtil.fromResourceAsString(filename);
  }

  private String expectedCST() {
    String filename = "python3/J123456test.tree.txt";
    return fileUtil.fromResourceAsString(filename);
  }

  private String expectedAST() {
    String filename = "python3/J123456test.AST.txt";
    return fileUtil.fromResourceAsString(filename);
  }
}
