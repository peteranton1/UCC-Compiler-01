package org.ardvark.ast;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AstBuilderIfsTest extends AstTestBase  {

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
              '- IfBlock
                 '- if
                    |- IfCondition
                    |  '- []
                    |     '- false
                    '- IfSuite
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
                '- IfBlock
                   '- if
                      |- IfCondition
                      |  '- []
                      |     |- x
                      |     |- %s
                      |     '- x
                      '- IfSuite
                         '- Stmt
                            '- Pass
          """;
      String expected = String.format(expectedTemplate, compOp);
      String actual = nodeToString(actualNode);
      assertEquals(expected, actual);
    }
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
              '- IfBlock
                 '- if
                    |- IfCondition
                    |  '- AtomPlus
                    |     |- test
                    |     '- ()
                    |        |- AtomPlus
                    |        |  |- data
                    |        |  '- .
                    |        |     '- Q1
                    |        '- []
                    |           '- or
                    |              |- "no"
                    |              '- "dkna"
                    '- IfSuite
                       '- Stmt
                          '- AtomPlus
                             |- compute
                             '- ()
                                |- AtomPlus
                                |  |- data
                                |  '- .
                                |     '- Q1Yes
                                '- "no"
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
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
                '- IfBlock
                   '- if
                      |- IfCondition
                      |  '- []
                      |     '- %s
                      |        |- x
                      |        '- y
                      '- IfSuite
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
                '- IfBlock
                   '- if
                      |- IfCondition
                      |  '- []
                      |     '- %s
                      |        |- a
                      |        |- b
                      |        |- c
                      |        '- d
                      '- IfSuite
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
              '- IfBlock
                 '- if
                    |- IfCondition
                    |  '- []
                    |     '- or
                    |        |- not
                    |        |  '- x
                    |        '- and
                    |           |- y
                    |           '- z
                    '- IfSuite
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
              '- IfBlock
                 '- if
                    |- IfCondition
                    |  '- []
                    |     '- not
                    |        '- x
                    '- IfSuite
                       '- Stmt
                          '- Pass
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }

  @Test
  void shouldIfWith1Literal() {
    String[] inputs = {
        """
        if true:
          pass
        """,
        """
        if (true):
          pass
        """,
    };
    String[] expecteds = {
        """
        '- {}
           '- Stmt
              '- IfBlock
                 '- if
                    |- IfCondition
                    |  '- true
                    '- IfSuite
                       '- Stmt
                          '- Pass
        """,
        """
        '- {}
           '- Stmt
              '- IfBlock
                 '- if
                    |- IfCondition
                    |  '- []
                    |     '- true
                    '- IfSuite
                       '- Stmt
                          '- Pass
        """
    };
    testInput(inputs, expecteds);
  }

  @Test
  void shouldIfElseWith1Literal() {
    String[] inputs = {
        """
        if (true):
          pass
        else:
          pass
        """,
    };
    String[] expecteds = {
        """
        '- {}
           '- Stmt
              '- IfBlock
                 |- if
                 |  |- IfCondition
                 |  |  '- []
                 |  |     '- true
                 |  '- IfSuite
                 |     '- Stmt
                 |        '- Pass
                 '- else
                    '- IfSuite
                       '- Stmt
                          '- Pass
        """
    };
    testInput(inputs, expecteds);
  }

  @Test
  void shouldIfElifWith1Literal() {
    String[] inputs = {
        """
        if (a):
          pass
        elif (b):
          pass
          pass
        """,
        """
        if (a):
          pass
        elif (b):
          pass
        elif (c):
          pass
        """,
    };
    String[] expecteds = {
        """
        '- {}
           '- Stmt
              '- IfBlock
                 |- if
                 |  |- IfCondition
                 |  |  '- []
                 |  |     '- a
                 |  '- IfSuite
                 |     '- Stmt
                 |        '- Pass
                 '- elif
                    |- IfCondition
                    |  '- []
                    |     '- b
                    '- IfSuite
                       |- Stmt
                       |  '- Pass
                       '- Stmt
                          '- Pass
        """,
        """
        '- {}
           '- Stmt
              '- IfBlock
                 |- if
                 |  |- IfCondition
                 |  |  '- []
                 |  |     '- a
                 |  '- IfSuite
                 |     '- Stmt
                 |        '- Pass
                 |- elif
                 |  |- IfCondition
                 |  |  '- []
                 |  |     '- b
                 |  '- IfSuite
                 |     '- Stmt
                 |        '- Pass
                 '- elif
                    |- IfCondition
                    |  '- []
                    |     '- c
                    '- IfSuite
                       '- Stmt
                          '- Pass
        """
    };
    testInput(inputs, expecteds);
  }

  @Test
  void shouldIfElifElseWith1Literal() {
    String[] inputs = {
        """
        if (a):
          pass
        elif (b):
          pass
        else:
          pass
        """,
        """
        if (a):
          pass
        elif (b):
          pass
        elif (c):
          pass
        else:
          pass
        """,
    };
    String[] expecteds = {
        """
        '- {}
           '- Stmt
              '- IfBlock
                 |- if
                 |  |- IfCondition
                 |  |  '- []
                 |  |     '- a
                 |  '- IfSuite
                 |     '- Stmt
                 |        '- Pass
                 |- elif
                 |  |- IfCondition
                 |  |  '- []
                 |  |     '- b
                 |  '- IfSuite
                 |     '- Stmt
                 |        '- Pass
                 '- else
                    '- IfSuite
                       '- Stmt
                          '- Pass
        """,
        """
        '- {}
           '- Stmt
              '- IfBlock
                 |- if
                 |  |- IfCondition
                 |  |  '- []
                 |  |     '- a
                 |  '- IfSuite
                 |     '- Stmt
                 |        '- Pass
                 |- elif
                 |  |- IfCondition
                 |  |  '- []
                 |  |     '- b
                 |  '- IfSuite
                 |     '- Stmt
                 |        '- Pass
                 |- elif
                 |  |- IfCondition
                 |  |  '- []
                 |  |     '- c
                 |  '- IfSuite
                 |     '- Stmt
                 |        '- Pass
                 '- else
                    '- IfSuite
                       '- Stmt
                          '- Pass
        """
    };
    testInput(inputs, expecteds);
  }

  private void testInput(String[] inputs, String[] expecteds) {
    for(int i = 0; i< inputs.length; i++) {
      String input = inputs[i];
      String expected = expecteds[i];
      ParseTree tree = parseSource(input);
      //printTree(input);
      AstNode actualNode = new AstBuilder().toAST(tree);
      String actual = nodeToString(actualNode);
      assertEquals(expected, actual);
    }
  }

}
