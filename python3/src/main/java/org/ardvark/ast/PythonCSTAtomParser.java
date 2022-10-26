package org.ardvark.ast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.antlr.v4.runtime.tree.ParseTree;
import org.ardvark.Python3Parser;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class PythonCSTAtomParser {

  private final CstPanic panic;
  private final StringUtils stringUtils = new StringUtils();
  private final AstBuilderVisitor visitor;

  /*
    atom
     : '(' ( yield_expr | testlist_comp )? ')'
     | '[' testlist_comp? ']'
     | '{' dictorsetmaker? '}'
     | NAME
     | number
     | str+
     | '...'
     | NONE
     | TRUE
     | FALSE
     ;
   */
  public AstNode visitAtom(Python3Parser.AtomContext ctx) {
    StringBuilder errBuf = new StringBuilder();
    errBuf.append("Error Recognising atom \n");
    int childCount = ctx.getChildCount();
    if (childCount > 0) {
      AstNode astNode;
      ParseTree child0 = ctx.getChild(0);
      String text = child0.getText();
      char ch = text.charAt(0);
      if ('(' == ch) {
        // Implement
        errBuf.append("'(' ( yield_expr | testlist_comp )? ')' \n");
        return panic.panic(child0, errBuf);
      } else if ('[' == ch) {
        // Implement
        errBuf.append("'[' testlist_comp? ']' \n");
        return panic.panic(child0, errBuf);
      } else if ('{' == ch) {
        astNode = visitor.visitChildren(ctx);
      } else if (Character.isDigit(ch)) {
        astNode = parseNumber(child0, errBuf);
      } else if ("None" .equals(text)) {
        astNode = parseLiteral(child0, errBuf, "None");
      } else if ("True" .equals(text)) {
        astNode = parseLiteral(child0, errBuf, "True");
      } else if ("False" .equals(text)) {
        astNode = parseLiteral(child0, errBuf, "False");
      } else if ("..." .equals(text)) {
        astNode = parseEllipse(child0, errBuf);
      } else if ('"' == ch) {
        astNode = parseString(ctx, errBuf);
      } else if (Character.isAlphabetic(ch)) {
        astNode = parseName(child0, errBuf);
      } else {
        errBuf.append("unknown 1 \n");
        return panic.panic(child0, errBuf);
      }
      return astNode;
    }
    errBuf.append("unknown 2 \n");
    return panic.panic(ctx, errBuf);
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
    char ch = tree.getText().charAt(0);
    if (Character.isAlphabetic(ch)) {
      return AstNode.builder()
          .nodeType(NodeType.NAME)
          .text(tree.getText())
          .build();
    } else {
      return panic.panic(tree, errBuf);
    }
  }

}
