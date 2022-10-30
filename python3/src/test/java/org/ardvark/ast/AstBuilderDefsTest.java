package org.ardvark.ast;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AstBuilderDefsTest extends AstTestBase  {

  @Test
  void shouldDefWith0Args() {
    String input = """
        def script():
          pass
        """;
    ParseTree tree = parseSource(input);
    //printTree(input);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           '- Stmt
              '- def
                 |- params
                 '- Stmt
                    '- Pass
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }

  @Test
  void shouldDefWith1Args() {
    String input = """
        def script(a):
          pass
        """;
    ParseTree tree = parseSource(input);
    //printTree(input);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           '- Stmt
              '- def
                 |- params
                 |  '- param
                 |     '- a
                 '- Stmt
                    '- Pass
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }

  @Test
  void shouldDefWith2Args() {
    String input = """
        def script(a,b):
          pass
        """;
    ParseTree tree = parseSource(input);
    //printTree(input);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           '- Stmt
              '- def
                 |- params
                 |  |- param
                 |  |  '- a
                 |  '- param
                 |     '- b
                 '- Stmt
                    '- Pass
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }


}
