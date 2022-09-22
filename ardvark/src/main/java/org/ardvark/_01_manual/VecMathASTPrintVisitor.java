package org.ardvark._01_manual;

import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.ardvark.VecMathASTBaseVisitor;
import org.ardvark.VecMathASTParser;

@Slf4j
public class VecMathASTPrintVisitor extends VecMathASTBaseVisitor<String> {
  @Override
  public String visitStatlist(VecMathASTParser.StatlistContext ctx) {
    String nodeTree = ctx.toStringTree();
    nodeTree = ctx.getText();
    log.info("visitStatlist(nodeTree) = {}", nodeTree);
    return super.visitStatlist(ctx);
  }

  @Override
  public String visitStat(VecMathASTParser.StatContext ctx) {
    String nodeTree = ctx.toStringTree();
    nodeTree = ctx.getText();
    log.info("visitStat(nodeTree) = {}", nodeTree);
    return super.visitStat(ctx);
  }

  @Override
  public String visitExpr(VecMathASTParser.ExprContext ctx) {
    String nodeTree = ctx.toStringTree();
    nodeTree = ctx.getText();
    log.info("visitExpr(nodeTree) = {}", nodeTree);
    return super.visitExpr(ctx);
  }

  @Override
  public String visitMultExpr(VecMathASTParser.MultExprContext ctx) {
    String nodeTree = ctx.toStringTree();
    nodeTree = ctx.getText();
    log.info("visitMultExpr(nodeTree) = {}", nodeTree);
    return super.visitMultExpr(ctx);
  }

  @Override
  public String visitPrimary(VecMathASTParser.PrimaryContext ctx) {
    String nodeTree = ctx.toStringTree();
    nodeTree = ctx.getText();
    TerminalNode nodeID = ctx.ID();
    TerminalNode nodeINT = ctx.INT();

    if(nodeID != null) {
      log.info("visitPrimary(ID) = {}", nodeID.getText());
    }
    if(nodeINT != null) {
      log.info("visitPrimary(INT) = {}", nodeINT.getText());
    }
    log.info("visitPrimary(nodeTree) = {}", nodeTree);

    return super.visitPrimary(ctx);
  }
}
