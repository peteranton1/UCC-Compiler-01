package org.ardvark.ast;

import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.tree.ParseTree;
import org.ardvark.python3.Builder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
class AstBuilderTerminalsTest extends AstTestBase {

  @Test
  void assignStringAsParseTree() {
    String source = "survey = \"J123456 Survey\"\n";
    String expected = "survey=\"J123456 Survey\"\n<EOF>";
    parseSource(source, expected);
  }

  @Test
  void assignStringAsAstNode() {
    String source = "survey = \"J123456 Survey\"\n";
    ParseTree tree = parseSource(source);
    //printTree(source);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- Root
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
        '- Root
           '- =
              |- survey
              '- 5
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
        '- Root
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
    ParseTree tree = parseSource(source);
    //printTree(source);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- Root
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
        '- Root
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
        '- Root
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