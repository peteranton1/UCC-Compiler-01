package org.ardvark.ast;

import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.tree.ParseTree;
import org.ardvark.python3.Builder;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.ardvark.ast.NodeType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
class AstBuilderTerminalsTest {

  ParseTree parseSource(String source, String expected) {
    ParseTree parseTree = new Builder.Tree(source).toTree();
    if (expected != null) {
      String actual = parseTree.getText();
      assertEquals(expected, actual);
    }
    return parseTree;
  }

  ParseTree parseSource(String source) {
    return parseSource(source, null);
  }

  public void printTree(String source) {
    String s = new Builder.Tree(source).toStringASCII();
    System.out.println(s);
  }

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
    AstNode expectedNode = buildNode(
        ROOT, List.of(buildNode(
            EQUALS, Arrays.asList(
                terminal(NAME, "survey"),
                terminal(STRING, "\"J123456 Survey\"")))));
    AstNode actual = new AstBuilder().toAST(tree);
    assertEquals(expectedNode, actual);
  }

  @Test
  void assignIntExpr() {
    String source = "survey = 5\n";
    ParseTree tree = parseSource(source);
    //printTree(source);
    AstNode expectedNode = buildNode(
        ROOT, List.of(buildNode(
            EQUALS, Arrays.asList(
                terminal(NAME, "survey"),
                terminal(NUMBER, "5")))));
    AstNode actual = new AstBuilder().toAST(tree);
    assertEquals(expectedNode, actual);
  }

  @Test
  void assignMultiplyExpr() {
    String source = "survey = 5 * 4\n";
    ParseTree tree = parseSource(source);
    //printTree(source);
    AstNode expectedNode = buildNode(
        ROOT, List.of(buildNode(
            EQUALS, Arrays.asList(
                terminal(NAME, "survey"),
                buildNode(
                    MULTIPLY, Arrays.asList(
                        terminal(NUMBER, "5"),
                        terminal(NUMBER, "4")))))));
    AstNode actual = new AstBuilder().toAST(tree);
    assertEquals(expectedNode, actual);
  }

  @Test
  void assignPlusExpr() {
    String source = "survey = 5 + 4\n";
    ParseTree tree = parseSource(source);
    //printTree(source);
    AstNode expectedNode = buildNode(
        ROOT, List.of(buildNode(
            EQUALS, Arrays.asList(
                terminal(NAME, "survey"),
                buildNode(
                    PLUS, Arrays.asList(
                        terminal(NUMBER, "5"),
                        terminal(NUMBER, "4")))))));
    AstNode actual = new AstBuilder().toAST(tree);
    assertEquals(expectedNode, actual);
  }

  @Test
  void assignMinusExpr() {
    String source = "survey = 5 - 4\n";
    ParseTree tree = parseSource(source);
    //printTree(source);
    AstNode expectedNode = buildNode(
        ROOT, List.of(buildNode(
            EQUALS, Arrays.asList(
                terminal(NAME, "survey"),
                buildNode(
                    SUBTRACT, Arrays.asList(
                        terminal(NUMBER, "5"),
                        terminal(NUMBER, "4")))))));
    AstNode actual = new AstBuilder().toAST(tree);
    assertEquals(expectedNode, actual);
  }

  @Test
  void assignDivideExpr() {
    String source = "survey = 5 / 4\n";
    ParseTree tree = parseSource(source);
    //printTree(source);
    AstNode expectedNode = buildNode(
        ROOT, List.of(buildNode(
            EQUALS, Arrays.asList(
                terminal(NAME, "survey"),
                buildNode(
                    DIVIDE, Arrays.asList(
                        terminal(NUMBER, "5"),
                        terminal(NUMBER, "4")))))));
    AstNode actual = new AstBuilder().toAST(tree);
    assertEquals(expectedNode, actual);
  }


  private AstNode buildNode(NodeType nodeType,
                            List<AstNode> children) {
    return AstNode.builder()
        .nodeType(nodeType)
        .text(nodeType.getText())
        .children(children)
        .build();
  }

  private AstNode terminal(NodeType nodeType, String text) {
    return AstNode.builder()
        .nodeType(nodeType)
        .text(text)
        .build();
  }

}