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
  void listWith0Items() {
    String source = "[]\n";
    String expected0 = "[]\n<EOF>";
    ParseTree tree = parseSource(source, expected0);
    //printTree(source);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           '- Stmt
              '- []
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }

  @Test
  void listWith1ItemsString() {
    String source = "[\"a\"]\n";
    String expected0 = "[\"a\"]\n<EOF>";
    ParseTree tree = parseSource(source, expected0);
    //printTree(source);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           '- Stmt
              '- []
                 '- "a"
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }

  @Test
  void listWith1ItemsName() {
    String source = "[a]\n";
    String expected0 = "[a]\n<EOF>";
    ParseTree tree = parseSource(source, expected0);
    //printTree(source);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           '- Stmt
              '- []
                 '- a
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }

  @Test
  void listWith2ItemsName() {
    String source = "[a,b]\n";
    String expected0 = "[a,b]\n<EOF>";
    ParseTree tree = parseSource(source, expected0);
    //printTree(source);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           '- Stmt
              '- []
                 |- a
                 '- b
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
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
           '- Stmt
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
           '- Stmt
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
           '- Stmt
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
           '- Stmt
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
           '- Stmt
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
           '- Stmt
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
           '- Stmt
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
           '- Stmt
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
           '- Stmt
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
           '- Stmt
              '- =
                 |- survey
                 '- 5
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }

  @Test
  void assignDottedNameExpr() {
    String source = "survey = aaa.bbb.ccc\n";
    String expected0 = "survey=aaa.bbb.ccc\n" +
        "<EOF>";
    ParseTree tree = parseSource(source, expected0);
    //printTree(source);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           '- Stmt
              '- =
                 |- Atom
                 |  '- survey
                 '- Atom
                    '- aaa
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
           '- Stmt
              '- =
                 |- survey
                 '- {}
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
           '- Stmt
              '- =
                 |- survey
                 '- {}
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
           '- Stmt
              '- =
                 |- survey
                 '- {}
                    '- Unknown
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
           '- Stmt
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
           '- Stmt
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
           '- Stmt
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
  void assignMinusAddExpr() {
    String source = "survey = 5 - 4 + 3 - 2 - 1\n";
    ParseTree tree = parseSource(source);
    //printTree(source);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           '- Stmt
              '- =
                 |- survey
                 '- -
                    |- -
                    |  |- +
                    |  |  |- -
                    |  |  |  |- 5
                    |  |  |  '- 4
                    |  |  '- 3
                    |  '- 2
                    '- 1
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }

  @Test
  void assignAddMulExpr() {
    String source = "survey = 5 + 4 * 3 + 2 * 1\n";
    ParseTree tree = parseSource(source);
    //printTree(source);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           '- Stmt
              '- =
                 |- survey
                 '- +
                    |- +
                    |  |- 5
                    |  '- *
                    |     |- 4
                    |     '- 3
                    '- *
                       |- 2
                       '- 1
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
           '- Stmt
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