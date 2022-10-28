package org.ardvark.python3;

import org.antlr.v4.runtime.tree.ParseTree;
import org.ardvark.ast.AstBuilder;
import org.ardvark.ast.AstNode;
import org.ardvark.ast.AstTestBase;
import org.ardvark.testutil.TestFileUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleTreeTest extends AstTestBase {

  private final TestFileUtil fileUtil = new TestFileUtil();

  @Test
  public void shouldCSTWhenValid() {
    Python3Utils utils = new Python3Utils();
    String source = inputString();
    String actual = utils.treeString(source);
    String expected = expectedCST();
    assertEquals(expected, actual);
  }

  @Test
  public void shouldASTWhenValid() {
    String source = inputString();
    ParseTree tree = parseSource(source);
    //printTree(source);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = expectedAST();
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }

  private String inputString() {
    String filename = "python3/simple.py";
    return fileUtil.fromResourceAsString(filename);
  }

  private String expectedCST() {
    String filename = "python3/simple.tree.txt";
    return fileUtil.fromResourceAsString(filename);
  }

  private String expectedAST() {
    String filename = "python3/simple.AST.txt";
    return fileUtil.fromResourceAsString(filename);
  }
}
