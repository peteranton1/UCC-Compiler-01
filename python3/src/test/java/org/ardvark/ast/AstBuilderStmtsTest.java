package org.ardvark.ast;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AstBuilderStmtsTest  extends AstTestBase  {

  @Test
  void shouldDoCallWith0Args() {
    String input = """
        compute()
        """;
    ParseTree tree = parseSource(input);
    //printTree(input);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           '- Stmt
              '- AtomPlus
                 |- compute
                 '- ()
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }

  @Test
  void shouldDoCallWith1Args() {
    String input = """
        compute(23)
        """;
    ParseTree tree = parseSource(input);
    //printTree(input);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           '- Stmt
              '- AtomPlus
                 |- compute
                 '- ()
                    '- 23
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }

  @Test
  void shouldDoCallWith3Args() {
    String input = """
        compute(a,23,"b")
        """;
    ParseTree tree = parseSource(input);
    //printTree(input);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           '- Stmt
              '- AtomPlus
                 |- compute
                 '- ()
                    |- a
                    |- 23
                    '- "b"
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }

  @Test
  void shouldCallWithDottedName() {
    String input = """
        data.Q1Yes
        perform(data.Q1Yes)
        """;
    ParseTree tree = parseSource(input);
    //printTree(input);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           |- Stmt
           |  '- AtomPlus
           |     |- data
           |     '- .
           |        '- Q1Yes
           '- Stmt
              '- AtomPlus
                 |- perform
                 '- ()
                    '- AtomPlus
                       |- data
                       '- .
                          '- Q1Yes
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }

}
