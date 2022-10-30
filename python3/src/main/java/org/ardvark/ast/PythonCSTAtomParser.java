package org.ardvark.ast;

import lombok.Getter;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.List;

import static org.ardvark.ast.NodeType.*;

@Getter
public class PythonCSTAtomParser {
  private final AstBuilderVisitor visitor;
  private final PythonCSTBaseParser baseParser;

  public PythonCSTAtomParser(AstBuilderVisitor visitor) {
    this.visitor = visitor;
    this.baseParser = visitor.baseParser;
  }

  public AstNode visitDotted_name(ParserRuleContext ctx) {
    return parseDottedName(ctx, baseParser.errBuf("DottedName"));
  }

  public AstNode parseDottedName(ParserRuleContext ctx,
                                 StringBuilder errBuf) {
    errBuf.append("visitDottedName \n");
    List<AstNode> aggChildren = new ArrayList<>();
    AstNode astNode;

    for (int pos = 0; pos < ctx.getChildCount(); pos++) {

      ParseTree child = ctx.getChild(pos);
      String text = child.getText();
      if(!".".equals(text)) {
        astNode = baseParser.parseName(child, errBuf);
        aggChildren.add(astNode);
      }
    }
    return AstNode.builder()
        .nodeType(DOTTED_NAME)
        .text(DOTTED_NAME.getText())
        .children(aggChildren)
        .build();
  }

  public AstNode visitAtom(ParserRuleContext ctx) {
    StringBuilder errBuf = new StringBuilder();
    errBuf.append("Error Recognising atom \n");
    int childCount = ctx.getChildCount();
    if (childCount > 0) {
      AstNode astNode;
      ParseTree child0 = ctx.getChild(0);
      String text = child0.getText();
      char ch = text.charAt(0);
      if ('(' == ch) {
        astNode = visitor.visitChildren(ctx);
        if (astNode == null) {
          return emptyNode(CALL_ARG);
        }
      } else if ('[' == ch) {
        astNode = visitor.visitChildren(ctx);
        if (astNode == null) {
          return emptyNode(LIST);
        }
      } else if ('{' == ch) {
        astNode = visitor.visitChildren(ctx);
        if (astNode == null) {
          return emptyNode(DICT_OR_SET);
        }
      } else if (Character.isDigit(ch)) {
        astNode = baseParser.parseNumber(child0, errBuf);
      } else if ("None" .equals(text)) {
        astNode = baseParser.parseLiteral(child0, errBuf, "None");
      } else if ("True" .equals(text)) {
        astNode = baseParser.parseLiteral(child0, errBuf, "True");
      } else if ("False" .equals(text)) {
        astNode = baseParser.parseLiteral(child0, errBuf, "False");
      } else if ("..." .equals(text)) {
        astNode = baseParser.parseEllipse(child0, errBuf);
      } else if ('"' == ch) {
        astNode = baseParser.parseString(ctx, errBuf);
      } else if (Character.isAlphabetic(ch)) {
        astNode = baseParser.parseName(child0, errBuf);
      } else {
        errBuf.append("unknown 1 \n");
        return baseParser.panic.panic(child0, errBuf);
      }
      return astNode;
    }
    errBuf.append("unknown 2 \n");
    return baseParser.panic.panic(ctx, errBuf);
  }

  private AstNode emptyNode(NodeType nodeType) {
    return AstNode.builder()
        .nodeType(nodeType)
        .text(nodeType.getText())
        .build();
  }

  public AstNode visitPower(ParserRuleContext ctx) {
    StringBuilder errBuf = new StringBuilder();
    errBuf.append("Error Recognising Power \n");
    AstNode astNode = visitor.visitChildren(ctx);
    if (!AGG.equals(astNode.getNodeType())) {
      return astNode;
    }
    if (astNode.childCount() == 0) {
      errBuf.append("Agg with zero children \n");
      return baseParser.panic.panic(ctx, errBuf);
    }
    return AstNode.builder()
        .nodeType(ATOM_PLUS)
        .text(ATOM_PLUS.getText())
        .children(astNode.getChildren())
        .build();
  }
}
