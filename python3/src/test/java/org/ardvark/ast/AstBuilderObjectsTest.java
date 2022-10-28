package org.ardvark.ast;

import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
class AstBuilderObjectsTest extends AstTestBase  {

  @Test
  void importDottedNameWith1Stmt() {
    String input = """
      from demog.Style import *
      """;
    ParseTree tree = parseSource(input);
    //printTree(input);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           '- Stmt
              '- ImportFrom
                 '- DottedName
                    |- demog
                    '- Style
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }

  @Test
  void importDottedNameWith2Stmt() {
    String input = """
      
      from aaa.Style import *
      
      from bbb.Style import *
      
      """;
    ParseTree tree = parseSource(input);
    //printTree(input);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           |- Stmt
           |  '- ImportFrom
           |     '- DottedName
           |        |- aaa
           |        '- Style
           '- Stmt
              '- ImportFrom
                 '- DottedName
                    |- bbb
                    '- Style
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }

  @Test
  void assignNameToDictOrSetWith0Items() {
    String input = """
      x = {}
      """;
    ParseTree tree = parseSource(input);
    //printTree(input);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           '- Stmt
              '- =
                 |- x
                 '- {}
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }

  @Test
  void assignNameToDictOrSetWith3Items() {
    String input = """
      x = {a:b,c:d}
      """;
    ParseTree tree = parseSource(input);
    //printTree(input);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           '- Stmt
              '- =
                 |- x
                 '- {}
                    |- :
                    |  |- a
                    |  '- b
                    '- :
                       |- c
                       '- d
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }

  @Test
  void assignNameToListWith0Items() {
    String input = """
      x = []
      """;
    ParseTree tree = parseSource(input);
    //printTree(input);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           '- Stmt
              '- =
                 |- x
                 '- []
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }

  @Test
  void assignNameToListWith3Numbers() {
    String input = """
      x = [1,2,3]
      """;
    ParseTree tree = parseSource(input);
    //printTree(input);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           '- Stmt
              '- =
                 |- x
                 '- []
                    |- 1
                    |- 2
                    '- 3
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }

  @Test
  void assignNameToStringWith1Stmt() {
    String input = """
      x = "Yes"
      """;
    ParseTree tree = parseSource(input);
    //printTree(input);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           '- Stmt
              '- =
                 |- x
                 '- "Yes"
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }

  @Test
  void assignNameToStringWith2StmtSameLine() {
    String input = """
      x = "Yes"; n = "No";
      """;
    ParseTree tree = parseSource(input);
    //printTree(input);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           '- Stmt
              |- =
              |  |- x
              |  '- "Yes"
              '- =
                 |- n
                 '- "No"
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }

  @Test
  void assignNameToStringWith2Stmt() {
    String input = """
      x = "Yes"
      y = "No"
      """;
    ParseTree tree = parseSource(input);
    //printTree(input);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           |- Stmt
           |  '- =
           |     |- x
           |     '- "Yes"
           '- Stmt
              '- =
                 |- y
                 '- "No"
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }

  @Test
  void assignNameToDictOrSetWith1Set() {
    String input = """
      survey = {
        "yes": { "Y": "Yes" }
        }
      """;
    ParseTree tree = parseSource(input);
    //printTree(input);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           '- Stmt
              '- =
                 |- survey
                 '- {}
                    '- :
                       |- "yes"
                       '- {}
                          '- :
                             |- "Y"
                             '- "Yes"
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }

  @Test
  void assignNameToDictOrSetWith2Sets() {
    String input = """
      survey = {
        "yes": { "Y": "Yes" },
        "no": { "N": "No" }
        }
      """;
    ParseTree tree = parseSource(input);
    //printTree(input);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           '- Stmt
              '- =
                 |- survey
                 '- {}
                    |- :
                    |  |- "yes"
                    |  '- {}
                    |     '- :
                    |        |- "Y"
                    |        '- "Yes"
                    '- :
                       |- "no"
                       '- {}
                          '- :
                             |- "N"
                             '- "No"
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }

  @Test
  void assignNameToDictOrSetWith3Sets() {
    String input = """
      survey = {
        "yes": { "Y": "Yes" },
        "no": 1 + 2,
        maybe : \"\"\"
        Hello World
        \"\"\"
        }
      """;
    ParseTree tree = parseSource(input);
    //printTree(input);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           '- Stmt
              '- =
                 |- survey
                 '- {}
                    |- :
                    |  |- "yes"
                    |  '- {}
                    |     '- :
                    |        |- "Y"
                    |        '- "Yes"
                    |- :
                    |  |- "no"
                    |  '- +
                    |     |- 1
                    |     '- 2
                    '- :
                       |- maybe
                       '- ""\"\\n  Hello World\\n  ""\"
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }


}