package org.ardvark.ast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.List;

import static org.ardvark.ast.NodeType.*;

@AllArgsConstructor
@Getter
public class PythonCSTIfStmtParser {

  private final CstPanic panic;
  private final StringUtils stringUtils = new StringUtils();
  private final AstBuilderVisitor visitor;

  /*
  /// pass_stmt: 'pass'
   */
  public AstNode visitPass_stmt(ParserRuleContext ctx) {
    StringBuilder errBuf = new StringBuilder();
    errBuf.append("Error Recognising Pass \n");
    return parsePass(ctx, errBuf);
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

  /*
  /// comp_op: '<'|'>'|'=='|'>='|'<='|'<>'|'!='|'in'|'not' 'in'|'is'|'is' 'not'
   */
  public AstNode visitComp_op(ParserRuleContext ctx) {
    StringBuilder errBuf = new StringBuilder();
    errBuf.append("Error Recognising Comp_op \n");
    return parseCompOp(ctx, errBuf);
  }

  /*
  /// or_test: and_test ('or' and_test)*
   */
  public AstNode visitOr_test(ParserRuleContext ctx) {
    StringBuilder errBuf = new StringBuilder();
    errBuf.append("Error Recognising Or_test \n");
    return parseTestOrAnd(ctx, errBuf, OP_OR);
  }

  /*
  /// and_test: not_test ('and' not_test)*
   */
  public AstNode visitAnd_test(ParserRuleContext ctx) {
    StringBuilder errBuf = new StringBuilder();
    errBuf.append("Error Recognising And_test \n");
    return parseTestOrAnd(ctx, errBuf, OP_AND);
  }

  /*
  /// not_test: 'not' not_test | comparison
   */
  public AstNode visitNot_test(ParserRuleContext ctx) {
    StringBuilder errBuf = new StringBuilder();
    errBuf.append("Error Recognising Not_test \n");
    return parseTestNot(ctx, errBuf);
  }

  public AstNode parseTestNot(ParserRuleContext ctx,
                              StringBuilder errBuf) {
    errBuf.append("parseTestNot \n");

    int childCount = ctx.getChildCount();
    if (childCount <= 0 || childCount > 2) {
      errBuf.append("parseTestNot : wrong childCount : ")
          .append(childCount);
      return panic.panic(ctx, errBuf);
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
      errBuf.append("wrong childCount : ")
          .append(childCount);
      return panic.panic(ctx, errBuf);
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
      return panic.panic(ctx, errBuf);
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
