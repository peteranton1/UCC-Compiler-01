package org.ardvark.ast;

import org.antlr.v4.runtime.tree.ParseTree;
import org.ardvark.python3.Builder;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AstTestBase {

  public ParseTree parseSource(String source, String expected) {
    ParseTree parseTree = new Builder.Tree(source).toTree();
    if (expected != null) {
      String actual = parseTree.getText();
      assertEquals(expected, actual);
    }
    return parseTree;
  }

  public ParseTree parseSource(String source) {
    return parseSource(source, null);
  }

  public void printTree(String source) {
    String s = new Builder.Tree(source).toStringASCII();
    System.out.println(s);
  }

  public String nodeToString(AstNode astNode) {
    StringBuilder buf = new StringBuilder();
    new AstBuilder().walk(astNode, buf);
    return buf.toString();
  }
}
