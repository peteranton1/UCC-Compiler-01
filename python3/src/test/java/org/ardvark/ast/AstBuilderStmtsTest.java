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
           |  |- data
           |  '- .
           |     '- Q1Yes
           '- Stmt
              |- perform
              '- ()
                 |- data
                 '- .
                    '- Q1Yes
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }

  @Test
  void shouldIfConditionWithOr() {
    String input = """
        if test(data.Q1, ["no" or "dkna"]):
          compute(data.Q1Yes, "no")
        """;
    ParseTree tree = parseSource(input);
    //printTree(input);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           '- Stmt
              |- test
              |- ()
              |  |- data
              |  |- .
              |  |  '- Q1
              |  '- []
              |     |- "no"
              |     '- "dkna"
              '- Stmt
                 |- compute
                 '- ()
                    |- data
                    |- .
                    |  '- Q1Yes
                    '- "no"
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }

}
