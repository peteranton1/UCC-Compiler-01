package org.ardvark.ast;

import lombok.Getter;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.List;

import static org.ardvark.ast.NodeType.*;

@Getter
public class PythonCSTCallParser {

  private final AstBuilderVisitor visitor;
  private final PythonCSTBaseParser baseParser;

  public PythonCSTCallParser(AstBuilderVisitor visitor) {
    this.visitor = visitor;
    this.baseParser = visitor.baseParser;
  }

  /*
    /// trailer: '(' [arglist] ')' | '[' subscriptlist ']' | '.' NAME
     */
  public AstNode visitTrailer(ParserRuleContext ctx) {
    return parseTrailer(ctx, baseParser.errBuf("Trailer"));
  }

  public AstNode parseTrailer(ParserRuleContext ctx,
                              StringBuilder errBuf) {
    errBuf.append("parseTrailer \n");

    NodeType nodeType = getCallType(ctx, errBuf);
    AstNode subNode = null;
    if (CALL_DOT.equals(nodeType)) {
      subNode = parseName(ctx.getChild(1), errBuf);
    }
    AstNode aggNode = visitor.visitChildren(ctx);
    List<AstNode> children = new ArrayList<>();
    if (aggNode != null && AGG.equals(aggNode.getNodeType())) {
      children.addAll(aggNode.getChildren());
    } else if (aggNode != null) {
      children.add(aggNode);
    }
    if (subNode != null) {
      children.add(subNode);
    }
    return AstNode.builder()
        .nodeType(nodeType)
        .text(nodeType.getText())
        .children(children)
        .build();
  }

  private NodeType getCallType(ParserRuleContext ctx,
                               StringBuilder errBuf) {
    if (ctx.getChildCount() == 0) {
      errBuf.append("getCallType no children \n");
      baseParser.panic.panic(ctx, errBuf);
    }
    String text = ctx.getChild(0).getText();
    char ch = text.charAt(0);
    if ('(' == ch) {
      return CALL_ARG;
    } else if ('[' == ch) {
      return CALL_SUB;
    } else if ('.' == ch) {
      return CALL_DOT;
    } else {
      return UNKNOWN;
    }
  }

  public AstNode parseName(ParseTree tree, StringBuilder errBuf) {
    errBuf.append("parseName \n");
    String text = tree.getText();
    char ch = text.charAt(0);
    if (Character.isAlphabetic(ch) || '.' == ch) {
      return AstNode.builder()
          .nodeType(NAME)
          .text(text)
          .build();
    } else {
      return baseParser.panic.panic(tree, errBuf);
    }
  }
}
