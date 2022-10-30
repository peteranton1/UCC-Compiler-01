package org.ardvark.ast;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AstBuilderWhileTest extends AstTestBase  {

  @Test
  void shouldWhileWithCounter() {
    String input = """
        a = 0
        while(a < 5):
          a = a + 1
        """;
    ParseTree tree = parseSource(input);
    //printTree(input);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           |- Stmt
           |  '- =
           |     |- a
           |     '- 0
           '- Stmt
              '- WhileBlock
                 '- while
                    |- Condition
                    |  '- []
                    |     |- a
                    |     |- <
                    |     '- 5
                    '- Suite
                       '- Stmt
                          '- =
                             |- a
                             '- +
                                |- a
                                '- 1
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }

  @Test
  void shouldWhileWithElse() {
    String input = """
        a = 0
        while(a < 5):
          a = a + 1
        else:
          a = -1
        """;
    ParseTree tree = parseSource(input);
    //printTree(input);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           |- Stmt
           |  '- =
           |     |- a
           |     '- 0
           '- Stmt
              '- WhileBlock
                 |- while
                 |  |- Condition
                 |  |  '- []
                 |  |     |- a
                 |  |     |- <
                 |  |     '- 5
                 |  '- Suite
                 |     '- Stmt
                 |        '- =
                 |           |- a
                 |           '- +
                 |              |- a
                 |              '- 1
                 '- else
                    '- Suite
                       '- Stmt
                          '- =
                             |- a
                             '- -
                                '- 1
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }
}
