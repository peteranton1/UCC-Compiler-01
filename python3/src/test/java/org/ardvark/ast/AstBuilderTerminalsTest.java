package org.ardvark.ast;

import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
class AstBuilderTerminalsTest extends AstTestBase {

  @Test
  void parseParseTree() {
    String source = "survey = \"J123456 Survey\"\n";
    String expected = "survey=\"J123456 Survey\"\n<EOF>";
    parseSource(source, expected);
  }

  @Test
  void stringAsAstNode() {
    String source = "\"J123456 Survey\"\n";
    String expected0 = "\"J123456 Survey\"\n<EOF>";
    ParseTree tree = parseSource(source, expected0);
    //printTree(source);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           '- "J123456 Survey"
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }

  @Test
  void stringsAsAstNode() {
    String source = "\"Aaa\" \"Bbb\" \"Ccc\" \n";
    String expected0 = "\"Aaa\"\"Bbb\"\"Ccc\"\n<EOF>";
    ParseTree tree = parseSource(source, expected0);
    //printTree(source);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           '- "AaaBbbCcc"
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }

  @Test
  void nameAsAstNode() {
    String source = "survey\n";
    String expected0 = "survey\n<EOF>";
    ParseTree tree = parseSource(source, expected0);
    //printTree(source);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           '- survey
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }

  @Test
  void numberAsAstNode() {
    String source = "1234\n";
    String expected0 = "1234\n<EOF>";
    ParseTree tree = parseSource(source, expected0);
    //printTree(source);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           '- 1234
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }
  @Test
  void literalTrueAsAstNode() {
    String source = "True\n";
    String expected0 = "True\n<EOF>";
    ParseTree tree = parseSource(source, expected0);
    //printTree(source);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           '- True
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }

  @Test
  void literalFalseAsAstNode() {
    String source = "False\n";
    String expected0 = "False\n<EOF>";
    ParseTree tree = parseSource(source, expected0);
    //printTree(source);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           '- False
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }

  @Test
  void literalNoneAsAstNode() {
    String source = "None\n";
    String expected0 = "None\n<EOF>";
    ParseTree tree = parseSource(source, expected0);
    //printTree(source);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           '- None
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }

  @Test
  void ellipseAsAstNode() {
    String source = "...\n";
    String expected0 = "...\n<EOF>";
    ParseTree tree = parseSource(source, expected0);
    //printTree(source);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           '- ...
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }

  @Test
  void assignStringAsAstNode() {
    String source = "survey = \"J123456 Survey\"\n";
    ParseTree tree = parseSource(source);
    //printTree(source);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           '- =
              |- survey
              '- "J123456 Survey"
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }

  @Test
  void assignIntExpr() {
    String source = "survey = 5\n";
    ParseTree tree = parseSource(source);
    //printTree(source);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           '- =
              |- survey
              '- 5
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }

  @Test
  void colonNameStrExpr() {
    String source = "survey = { a : \"A\" }\n";
    String expected0 = "survey={a:\"A\"}\n" +
        "<EOF>";
    ParseTree tree = parseSource(source, expected0);
    //printTree(source);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           '- =
              |- survey
              '- DictOrSet
                 '- StmtList
                    '- :
                       |- a
                       '- "A"
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }

  @Test
  void colonCommaNameStrExpr() {
    String source = "survey = { a : \"A\", b : \"B\"  }\n";
    String expected0 = "survey={a:\"A\",b:\"B\"}\n" +
        "<EOF>";
    ParseTree tree = parseSource(source, expected0);
    //printTree(source);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           '- =
              |- survey
              '- DictOrSet
                 '- StmtList
                    |- :
                    |  |- a
                    |  '- "A"
                    '- :
                       |- b
                       '- "B"
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }

  @Test
  void nocolonCommaNameStrExpr() {
    String source = "survey = { \"A\", b   }\n";
    String expected0 = "survey={\"A\",b}\n" +
        "<EOF>";
    ParseTree tree = parseSource(source, expected0);
    //printTree(source);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           '- =
              |- survey
              '- DictOrSet
                 '- StmtList
                    |- "A"
                    '- b
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }

  @Test
  void assignMultiplyExpr() {
    String source = "survey = 5 * 4\n";
    ParseTree tree = parseSource(source);
    //printTree(source);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           '- =
              |- survey
              '- *
                 |- 5
                 '- 4
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }

  @Test
  void assignPlusExpr() {
    String source = "survey = 5 + 4\n";
    String expected0 = "survey=5+4\n" +
        "<EOF>";
    ParseTree tree = parseSource(source, expected0);
    //printTree(source);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           '- =
              |- survey
              '- +
                 |- 5
                 '- 4
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }

  @Test
  void assignMinusExpr() {
    String source = "survey = 5 - 4\n";
    ParseTree tree = parseSource(source);
    //printTree(source);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           '- =
              |- survey
              '- -
                 |- 5
                 '- 4
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }

  @Test
  void assignDivideExpr() {
    String source = "survey = 5 / 4\n";
    ParseTree tree = parseSource(source);
    //printTree(source);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           '- =
              |- survey
              '- /
                 |- 5
                 '- 4
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }

}