package org.ardvark.ast;

import lombok.Getter;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.List;

import static org.ardvark.ast.NodeType.*;

@Getter
public class PythonCSTWhileParser {

  private final AstBuilderVisitor visitor;
  private final PythonCSTBaseParser baseParser;

  public PythonCSTWhileParser(AstBuilderVisitor visitor) {
    this.visitor = visitor;
    this.baseParser = visitor.baseParser;
  }

  /*
  /// while_stmt: 'while' test ':' suite ['else' ':' suite]
   */
  public AstNode visitWhile_stmt(ParserRuleContext ctx) {
    return parseWhileStmt(ctx, baseParser.errBuf("While_stmt"));
  }

  public AstNode parseWhileStmt(ParserRuleContext ctx, StringBuilder errBuf) {
    errBuf.append("parseWhileStmt \n");
    return parseWhileBlock(ctx, errBuf);
  }

  public AstNode parseWhileBlock(ParserRuleContext ctx, StringBuilder errBuf) {
    int childCount = ctx.getChildCount();
    checkCount(ctx, errBuf, 0, childCount);
    List<AstNode> children = new ArrayList<>();

    int pos = 0;
    while (pos +2 < ctx.getChildCount()) {
      AstNode aNode = findWhileElseNode(ctx, errBuf, pos);
      children.add(aNode);
      pos += 4;
    }

    return AstNode.builder()
        .nodeType(OP_WHILE_BLOCK)
        .text(OP_WHILE_BLOCK.getText())
        .children(children)
        .build();
  }

  public AstNode findWhileElseNode(ParserRuleContext ctx,
                                    StringBuilder errBuf,
                                    int pos) {
    int childCount = ctx.getChildCount();
    checkCount(ctx, errBuf, pos + 2, childCount);

    String kwd = ctx.getChild(pos).getText();
    checkKwd(ctx, errBuf, kwd);

    List<AstNode> children;
    NodeType nodeType = NodeType.textValueOf(kwd);
    if (OP_WHILE.equals(nodeType)) {
      checkCount(ctx, errBuf, pos + 3, childCount);
      String colon = ctx.getChild(pos + 2).getText();
      checkKwd(ctx, errBuf, colon);
      AstNode testNode = visitDeaggregate(ctx.getChild(pos + 1), OP_CONDITION);
      AstNode suiteNode = visitDeaggregate(ctx.getChild(pos + 3), OP_SUITE);
      children = List.of(testNode, suiteNode);
    } else {
      String colon = ctx.getChild(pos + 1).getText();
      checkKwd(ctx, errBuf, colon);
      AstNode suiteNode = visitDeaggregate(ctx.getChild(pos + 2), OP_SUITE);
      children = List.of(suiteNode);
    }
    return AstNode.builder()
        .nodeType(nodeType)
        .text(nodeType.getText())
        .children(children)
        .build();
  }

  public AstNode visitDeaggregate(ParseTree child,
                                  NodeType nodeType) {
    AstNode astNode = visitor.visit(child);
    List<AstNode> children;
    if (AGG.equals(astNode.getNodeType())) {
      children = astNode.getChildren();
    } else {
      children = List.of(astNode);
    }
    return AstNode.builder()
        .nodeType(nodeType)
        .text(nodeType.getText())
        .children(children)
        .build();
  }

  public void checkCount(ParserRuleContext ctx,
                         StringBuilder errBuf,
                         int expectedCount,
                         int childCount) {
    if (expectedCount >= childCount) {
      errBuf
          .append("checkCount : unexpected childCount : ")
          .append(childCount)
          .append(" : expected : ")
          .append(expectedCount)
      ;
      baseParser.panic.panic(ctx, errBuf);
    }
  }

  public void checkKwd(ParserRuleContext ctx,
                       StringBuilder errBuf,
                       String kwd) {
    NodeType nodeType = NodeType.textValueOf(kwd);
    if (UNKNOWN.equals(nodeType)) {
      errBuf.append("checkKwd : unexpected : ").append(kwd);
      baseParser.panic.panic(ctx, errBuf);
    }
  }

}
