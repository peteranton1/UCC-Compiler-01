package org.ardvark.ast;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AstBuilderStmtsTest  extends AstTestBase  {

  @Test
  void shouldDoIfWith1Literal() {
    String input = """
        if(false):
          pass
        """;
    ParseTree tree = parseSource(input);
    //printTree(input);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           '- Stmt
              |- []
              |  '- false
              '- Stmt
                 '- Pass
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }

  @Test
  void shouldDoIfWithEquivExpr() {
    String[] compOps = {
        "<",
        ">",
        "==",
        ">=",
        "<=",
        "<>",
        "!=",
        "in",
        "not in",
        "is",
        "is not"
    };
    String inputTemplate = """
        if(x %s x):
          pass
        """;
    for (String compOp : compOps){
      String input = String.format(inputTemplate, compOp);
      ParseTree tree = parseSource(input);
      //printTree(input);
      AstNode actualNode = new AstBuilder().toAST(tree);
      String expectedTemplate = """
          '- {}
             '- Stmt
                |- []
                |  |- x
                |  |- %s
                |  '- x
                '- Stmt
                   '- Pass
          """;
      String expected = String.format(expectedTemplate, compOp);
      String actual = nodeToString(actualNode);
      assertEquals(expected, actual);
    }
  }

  @Test
  void shouldDoIfWithOrAndExpr1Pair() {
    String[] compOps = {
        "or",
        "and",
    };
    String inputTemplate = """
        if(x %s y):
          pass
        """;
    for (String compOp : compOps){
      String input = String.format(inputTemplate, compOp);
      ParseTree tree = parseSource(input);
      //printTree(input);
      AstNode actualNode = new AstBuilder().toAST(tree);
      String expectedTemplate = """
          '- {}
             '- Stmt
                |- []
                |  '- %s
                |     |- x
                |     '- y
                '- Stmt
                   '- Pass
          """;
      String expected = String.format(expectedTemplate, compOp);
      String actual = nodeToString(actualNode);
      assertEquals(expected, actual);
    }
  }

  @Test
  void shouldDoIfWithOrAndExpr3Pair() {
    String[] combineOps = {
        "or",
        "and",
    };
    String inputTemplate = """
        if(a %s b %s c %s d):
          pass
        """;
    for (String combineOp : combineOps){
      String input = String.format(inputTemplate, combineOp, combineOp, combineOp);
      ParseTree tree = parseSource(input);
      //printTree(input);
      AstNode actualNode = new AstBuilder().toAST(tree);
      String expectedTemplate = """
          '- {}
             '- Stmt
                |- []
                |  '- %s
                |     |- a
                |     |- b
                |     |- c
                |     '- d
                '- Stmt
                   '- Pass
          """;
      String expected = String.format(expectedTemplate, combineOp);
      String actual = nodeToString(actualNode);
      assertEquals(expected, actual);
    }
  }

  @Test
  void shouldDoIfWithOrAndNot() {
    String input = """
        if(not x or y and z):
          pass
        """;
    ParseTree tree = parseSource(input);
    //printTree(input);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           '- Stmt
              |- []
              |  '- or
              |     |- not
              |     |  '- x
              |     '- and
              |        |- y
              |        '- z
              '- Stmt
                 '- Pass
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }

  @Test
  void shouldDoIfWithNotExpr() {
    String input = """
        if(not x):
          pass
        """;
    ParseTree tree = parseSource(input);
    //printTree(input);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           '- Stmt
              |- []
              |  '- not
              |     '- x
              '- Stmt
                 '- Pass
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }

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
              |     '- or
              |        |- "no"
              |        '- "dkna"
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
