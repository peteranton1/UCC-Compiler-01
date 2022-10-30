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
                    |- Condition
                    |  '- []
                    |     '- false
                    '- Suite
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
                      |- Condition
                      |  '- []
                      |     |- x
                      |     |- %s
                      |     '- x
                      '- Suite
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
                    |- Condition
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
                    '- Suite
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
                      |- Condition
                      |  '- []
                      |     '- %s
                      |        |- x
                      |        '- y
                      '- Suite
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
                      |- Condition
                      |  '- []
                      |     '- %s
                      |        |- a
                      |        |- b
                      |        |- c
                      |        '- d
                      '- Suite
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
                    |- Condition
                    |  '- []
                    |     '- or
                    |        |- not
                    |        |  '- x
                    |        '- and
                    |           |- y
                    |           '- z
                    '- Suite
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
                    |- Condition
                    |  '- []
                    |     '- not
                    |        '- x
                    '- Suite
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
                    |- Condition
                    |  '- true
                    '- Suite
                       '- Stmt
                          '- Pass
        """,
        """
        '- {}
           '- Stmt
              '- IfBlock
                 '- if
                    |- Condition
                    |  '- []
                    |     '- true
                    '- Suite
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
                 |  |- Condition
                 |  |  '- []
                 |  |     '- true
                 |  '- Suite
                 |     '- Stmt
                 |        '- Pass
                 '- else
                    '- Suite
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
                 |  |- Condition
                 |  |  '- []
                 |  |     '- a
                 |  '- Suite
                 |     '- Stmt
                 |        '- Pass
                 '- elif
                    |- Condition
                    |  '- []
                    |     '- b
                    '- Suite
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
                 |  |- Condition
                 |  |  '- []
                 |  |     '- a
                 |  '- Suite
                 |     '- Stmt
                 |        '- Pass
                 |- elif
                 |  |- Condition
                 |  |  '- []
                 |  |     '- b
                 |  '- Suite
                 |     '- Stmt
                 |        '- Pass
                 '- elif
                    |- Condition
                    |  '- []
                    |     '- c
                    '- Suite
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
                 |  |- Condition
                 |  |  '- []
                 |  |     '- a
                 |  '- Suite
                 |     '- Stmt
                 |        '- Pass
                 |- elif
                 |  |- Condition
                 |  |  '- []
                 |  |     '- b
                 |  '- Suite
                 |     '- Stmt
                 |        '- Pass
                 '- else
                    '- Suite
                       '- Stmt
                          '- Pass
        """,
        """
        '- {}
           '- Stmt
              '- IfBlock
                 |- if
                 |  |- Condition
                 |  |  '- []
                 |  |     '- a
                 |  '- Suite
                 |     '- Stmt
                 |        '- Pass
                 |- elif
                 |  |- Condition
                 |  |  '- []
                 |  |     '- b
                 |  '- Suite
                 |     '- Stmt
                 |        '- Pass
                 |- elif
                 |  |- Condition
                 |  |  '- []
                 |  |     '- c
                 |  '- Suite
                 |     '- Stmt
                 |        '- Pass
                 '- else
                    '- Suite
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
