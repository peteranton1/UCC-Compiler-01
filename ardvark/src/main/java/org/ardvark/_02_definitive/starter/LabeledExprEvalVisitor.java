package org.ardvark._02_definitive.starter;

import org.antlr.v4.runtime.tree.TerminalNode;
import org.ardvark.LabeledExprBaseVisitor;
import org.ardvark.LabeledExprParser;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class LabeledExprEvalVisitor
    extends LabeledExprBaseVisitor<BigDecimal> {

  /**
   * "memory" for our calculator; variable/value pairs go here
   */
  Map<String, BigDecimal> memory = new HashMap<>();

  /**
   * ID '=' expr NEWLINE
   */
  @Override
  public BigDecimal visitAssign(LabeledExprParser.AssignContext ctx) {
    // id is left-hand side of '='
    String id = ctx.id().ID().getText();
    LabeledExprParser.ExprContext expr = ctx.expr();
    if (expr != null) {
      BigDecimal value = visit(expr);
      // compute value of expression on right
      if (value != null) {
        // store it in our memory
        memory.put(id, value);
        return value;
      }
    }
    return BigDecimal.ZERO;
  }

  /**
   * expr NEWLINE
   */
  @Override
  public BigDecimal visitPrintExpr(LabeledExprParser.PrintExprContext ctx) {
    BigDecimal value = visit(ctx.expr()); // evaluate the expr child
    System.out.println(value);
    // print the result
    return BigDecimal.ZERO;
    // return dummy value
  }

  /**
   * INT
   */
  @Override
  public BigDecimal visitNumber(LabeledExprParser.NumberContext ctx) {
    String numberStr = ctx.INT(0).getText();
    if (ctx.INT().size() == 2) {
      numberStr += "." + ctx.INT(1).getText();
    }
    return new BigDecimal(numberStr);
  }

  /**
   * ID
   */
  @Override
  public BigDecimal visitId(LabeledExprParser.IdContext ctx) {
    String id = ctx.ID().getText();
    if (memory.containsKey(id)) return memory.get(id);
    return BigDecimal.ZERO;
  }

  /**
   * product: factor (opMD factor)* ;
   */
  @Override
  public BigDecimal visitProduct(LabeledExprParser.ProductContext ctx) {
    BigDecimal left = visit(ctx.term(0)); // get value of left subexpression
    for (int i = 1; i < ctx.term().size(); i++) {
      BigDecimal right = visit(ctx.term(i)); // get value of right subexpression
      TerminalNode mul = ctx.opMD().get(i - 1).MUL();
      if (mul != null) left = left.multiply(right);
      else left = left.divide(right, RoundingMode.HALF_UP); // must be DIV
    }
    return left;
  }

  /**
   * term:   int
   * |   id
   * |   parens
   * ;
   */
  @Override
  public BigDecimal visitTerm(LabeledExprParser.TermContext ctx) {
    if (ctx.number() != null) return visit(ctx.number());
    else if (ctx.id() != null) return visit(ctx.id());
    else return visit(ctx.parens());
  }

  /**
   * parens:     LPAREN expr RPAREN ;
   */
  @Override
  public BigDecimal visitParens(LabeledExprParser.ParensContext ctx) {
    return visit(ctx.expr()); // return child expr's value
  }
}

