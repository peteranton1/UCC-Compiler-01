package org.ardvark._01_manual;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.ardvark.ArithmeticBaseVisitor;
import org.ardvark.ArithmeticParser;

public class ArithmeticPrintVisitor extends ArithmeticBaseVisitor<String> {

  public static final String PAD = "->";
  public static final int L0 = 0;
  public static final int L1 = 1;
  public static final int L2 = 2;
  public static final int L3 = 3;
  public static final int L4 = 4;
  public static final int L5 = 5;
  public static final int L6 = 6;
  public static final int L7 = 7;

  public String indent(int level) {
    return "\\n" + PAD.repeat(Math.max(0, level));
  }

  public void printNode(ParserRuleContext ctx, int level, TerminalNode node) {
    if(null != node) {
      String msg = String.format("%s : '%s' ", indent(level), getText(node));
      System.out.println(msg);
    }
  }

  public String getText(TerminalNode node){
    return (node != null ? node.getText() : "");
  }


  @Override
  public String visitFile_(ArithmeticParser.File_Context ctx) {
    return super.visitFile_(ctx);
  }

  @Override
  public String visitEquation(ArithmeticParser.EquationContext ctx) {
    System.out.println("visitEquation");
    return super.visitEquation(ctx);
  }

  @Override
  public String visitAssignment(ArithmeticParser.AssignmentContext ctx) {
    System.out.println("visitAssignment");
    return super.visitAssignment(ctx);
  }

  @Override
  public String visitExpression(ArithmeticParser.ExpressionContext ctx) {
    System.out.println("visitExpression");
    return super.visitExpression(ctx);
  }

  @Override
  public String visitPow(ArithmeticParser.PowContext ctx) {
    printNode(ctx, L5, ctx.POW());
    return super.visitPow(ctx);
  }

  @Override
  public String visitMuldiv(ArithmeticParser.MuldivContext ctx) {
    printNode(ctx, L5, ctx.TIMES());
    printNode(ctx, L5, ctx.DIV());
    return super.visitMuldiv(ctx);
  }

  @Override
  public String visitPlusminus(ArithmeticParser.PlusminusContext ctx) {
    printNode(ctx, L5, ctx.PLUS());
    printNode(ctx, L5, ctx.MINUS());
    return super.visitPlusminus(ctx);
  }

  @Override
  public String visitUnary(ArithmeticParser.UnaryContext ctx) {
    printNode(ctx, L5, ctx.PLUS());
    printNode(ctx, L5, ctx.MINUS());
    return super.visitUnary(ctx);
  }

  @Override
  public String visitLparen(ArithmeticParser.LparenContext ctx) {
    printNode(ctx, L5, ctx.LPAREN());
    return super.visitLparen(ctx);
  }

  @Override
  public String visitRparen(ArithmeticParser.RparenContext ctx) {
    printNode(ctx, L5, ctx.RPAREN());
    return super.visitRparen(ctx);
  }

  @Override
  public String visitAtom(ArithmeticParser.AtomContext ctx) {
    return super.visitAtom(ctx);
  }

  @Override
  public String visitScientific(ArithmeticParser.ScientificContext ctx) {
    printNode(ctx, L5, ctx.SCIENTIFIC_NUMBER());
    return super.visitScientific(ctx);
  }

  @Override
  public String visitVariable(ArithmeticParser.VariableContext ctx) {
    printNode(ctx, L5, ctx.VARIABLE());
    return super.visitVariable(ctx);
  }

  @Override
  public String visitRelop(ArithmeticParser.RelopContext ctx) {
    System.out.println("visitRelop");
    printNode(ctx, L5, ctx.EQ());
    printNode(ctx, L5, ctx.GT());
    printNode(ctx, L5, ctx.LT());
    return super.visitRelop(ctx);
  }
}
