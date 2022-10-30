package org.ardvark.ast;

import lombok.AllArgsConstructor;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.List;

import static org.ardvark.ast.NodeType.*;

@AllArgsConstructor
public class PythonCSTBaseParser {

  public final CstPanic panic;
  private final StringUtils stringUtils = new StringUtils();

  public StringBuilder errBuf(String name) {
    StringBuilder errBuf = new StringBuilder();
    errBuf.append("Error Recognising ").append(name).append(" \n");
    return errBuf;
  }

  public AstNode parseNumber(ParseTree tree, StringBuilder errBuf) {
    errBuf.append("parseNumber \n");
    char ch = tree.getText().charAt(0);
    if (Character.isDigit(ch)) {
      return AstNode.builder()
          .nodeType(NodeType.NUMBER)
          .text(tree.getText())
          .build();
    } else {
      return panic.panic(tree, errBuf);
    }
  }

  public AstNode parseLiteral(ParseTree tree, StringBuilder errBuf, String literal) {
    errBuf.append("parseLiteral \n");
    String text = tree.getText();
    if (literal.equals(text)) {
      return AstNode.builder()
          .nodeType(NodeType.LITERAL)
          .text(tree.getText())
          .build();
    } else {
      return panic.panic(tree, errBuf);
    }
  }

  public AstNode parseEllipse(ParseTree tree, StringBuilder errBuf) {
    errBuf.append("parseEllipse \n");
    String text = tree.getText();
    if ("..." .equals(text)) {
      return AstNode.builder()
          .nodeType(NodeType.ELLIPSE)
          .text(tree.getText())
          .build();
    } else {
      return panic.panic(tree, errBuf);
    }
  }

  public AstNode parseString(ParseTree tree, StringBuilder errBuf) {

    List<String> children = new ArrayList<>();
    AstNode astNode;
    ParseTree childTree;
    errBuf.append("parseString \n");
    if (tree.getChildCount() == 0 ||
        '"' != tree.getText().charAt(0)) {
      return panic.panic(tree, errBuf);
    }
    for (int childRow = 0; childRow < tree.getChildCount(); childRow++) {
      childTree = tree.getChild(childRow);
      children.add(childTree.getText());
    }
    String text = stringUtils.concat(children);
    astNode = AstNode.builder()
        .nodeType(NodeType.STRING)
        .text(text)
        .build();
    return astNode;
  }

  public AstNode parseName(ParseTree tree, StringBuilder errBuf) {
    errBuf.append("parseName \n");
    String text = tree.getText();
    char ch = text.charAt(0);
    if (Character.isAlphabetic(ch)) {
      return AstNode.builder()
          .nodeType(NAME)
          .text(text)
          .build();
    } else {
      return panic.panic(tree, errBuf);
    }
  }
}
