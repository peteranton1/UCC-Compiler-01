package org.ardvark.ast;

import lombok.Getter;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.List;

import static org.ardvark.ast.NodeType.*;

@Getter
public class PythonCSTIfStmtParser {

  private final AstBuilderVisitor visitor;
  private final PythonCSTBaseParser baseParser;

  public PythonCSTIfStmtParser(AstBuilderVisitor visitor) {
    this.visitor = visitor;
    this.baseParser = visitor.baseParser;
  }

  /*
    /// pass_stmt: 'pass'
     */
  public AstNode visitPass_stmt(ParserRuleContext ctx) {
    return parsePass(ctx, baseParser.errBuf("Pass"));
  }

  /*
  /// comp_op: '<'|'>'|'=='|'>='|'<='|'<>'|'!='|'in'|'not' 'in'|'is'|'is' 'not'
   */
  public AstNode visitComp_op(ParserRuleContext ctx) {
    return parseCompOp(ctx, baseParser.errBuf("Comp_op"));
  }

  /*
  /// comp_if: 'if' test_nocond [comp_iter]
   */
  public AstNode visitComp_if(ParserRuleContext ctx) {
    return parseCompIf(ctx, baseParser.errBuf("Comp_if"));
  }

  /*
  /// if_stmt: 'if' test ':' suite ('elif' test ':' suite)* ['else' ':' suite]
   */
  public AstNode visitIf_stmt(ParserRuleContext ctx) {
    return parseIfStmt(ctx, baseParser.errBuf("If_stmt"));
  }

  /*
  /// or_test: and_test ('or' and_test)*
   */
  public AstNode visitOr_test(ParserRuleContext ctx) {
    return parseTestOrAnd(ctx, baseParser.errBuf("Or_test"), OP_OR);
  }

  /*
  /// and_test: not_test ('and' not_test)*
   */
  public AstNode visitAnd_test(ParserRuleContext ctx) {
    return parseTestOrAnd(ctx, baseParser.errBuf("And_test"), OP_AND);
  }

  /*
  /// not_test: 'not' not_test | comparison
   */
  public AstNode visitNot_test(ParserRuleContext ctx) {
    return parseTestNot(ctx, baseParser.errBuf("Not"));
  }

  public AstNode parsePass(ParserRuleContext ctx,
                           StringBuilder errBuf) {
    errBuf.append("parsePass \n");
    errBuf.append(ctx.getChildCount());

    return AstNode.builder()
        .nodeType(PASS)
        .text(PASS.getText())
        .build();
  }

  public AstNode parseIfStmt(ParserRuleContext ctx, StringBuilder errBuf) {
    errBuf.append("parseIfStmt \n");
    return parseIfBlock(ctx, errBuf);
  }

  public AstNode parseCompIf(ParserRuleContext ctx, StringBuilder errBuf) {
    errBuf.append("parseCompIf \n");
    return parseIfBlock(ctx, errBuf);
  }

  public AstNode parseIfBlock(ParserRuleContext ctx, StringBuilder errBuf) {
    int childCount = ctx.getChildCount();
    baseParser.checkCount(ctx, errBuf, 0, childCount);
    List<AstNode> ifChildren = new ArrayList<>();

    int pos = 0;
    while (pos +2 < ctx.getChildCount()) {
      AstNode anIf = findIfElifElseNode(ctx, errBuf, pos);
      ifChildren.add(anIf);
      pos += 4;
    }

    return AstNode.builder()
        .nodeType(OP_IF_BLOCK)
        .text(OP_IF_BLOCK.getText())
        .children(ifChildren)
        .build();
  }

  /*
    /// if_stmt: 'if' test ':' suite ('elif' test ':' suite)* ['else' ':' suite]
   */
  public AstNode findIfElifElseNode(ParserRuleContext ctx,
                                    StringBuilder errBuf,
                                    int pos) {
    int childCount = ctx.getChildCount();
    baseParser.checkCount(ctx, errBuf, pos + 2, childCount);

    String kwd = ctx.getChild(pos).getText();
    baseParser.checkKwd(ctx, errBuf, kwd);

    List<AstNode> children;
    NodeType nodeType = NodeType.textValueOf(kwd);
    if (OP_IF.equals(nodeType) || OP_ELIF.equals(nodeType)) {
      baseParser.checkCount(ctx, errBuf, pos + 3, childCount);
      String colon = ctx.getChild(pos + 2).getText();
      baseParser.checkKwd(ctx, errBuf, colon);
      AstNode testNode = visitDeaggregate(ctx.getChild(pos + 1), OP_CONDITION);
      AstNode suiteNode = visitDeaggregate(ctx.getChild(pos + 3), OP_SUITE);
      children = List.of(testNode, suiteNode);
    } else {
      String colon = ctx.getChild(pos + 1).getText();
      baseParser.checkKwd(ctx, errBuf, colon);
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

  public AstNode parseTestNot(ParserRuleContext ctx,
                              StringBuilder errBuf) {
    errBuf.append("parseTestNot \n");

    int childCount = ctx.getChildCount();
    if (childCount <= 0 || childCount > 2) {
      errBuf.append("parseTestNot : wrong childCount : ")
          .append(childCount);
      return baseParser.panic.panic(ctx, errBuf);
    }
    AstNode astNode = visitor.visitChildren(ctx);
    if (childCount == 1) {
      return astNode;
    }
    return AstNode.builder()
        .nodeType(OP_NOT)
        .text(OP_NOT.getText())
        .children(List.of(astNode))
        .build();
  }

  public AstNode parseTestOrAnd(ParserRuleContext ctx,
                                StringBuilder errBuf,
                                NodeType nodeType) {
    errBuf.append("parseTestOrAnd ")
        .append(nodeType).append(" \n");

    int childCount = ctx.getChildCount();
    if (childCount <= 0) {
      errBuf.append("parseTestOrAnd : wrong childCount : ")
          .append(childCount);
      return baseParser.panic.panic(ctx, errBuf);
    }
    AstNode astNode = visitor.visitChildren(ctx);
    if (childCount == 1) {
      return astNode;
    }
    return AstNode.builder()
        .nodeType(nodeType)
        .text(nodeType.getText())
        .children(astNode.getChildren())
        .build();
  }

  public AstNode parseCompOp(ParserRuleContext ctx,
                             StringBuilder errBuf) {
    errBuf.append("parseCompOp \n");

    int childCount = ctx.getChildCount();
    if (childCount < 1) {
      errBuf.append("Comp_op has wrong child count: < 1");
      return baseParser.panic.panic(ctx, errBuf);
    }
    String text = ctx.getChild(0).getText();
    if (childCount > 1) {
      text += " ";
      text += ctx.getChild(1).getText();
    }

    return AstNode.builder()
        .nodeType(OP_COMPARE)
        .text(text)
        .build();
  }

}
