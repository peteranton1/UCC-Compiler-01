package org.basel;

import org.ardvark.PythonArdvarkBaseVisitor;
import org.ardvark.PythonArdvarkParser;

public class PythonArdvarkPrintVisitor
    extends PythonArdvarkBaseVisitor<String> {

  public String println(String s){
    System.out.println(s);
    return s;
  }

  @Override
  public String visitSingle_input(PythonArdvarkParser.Single_inputContext ctx) {
    return super.visitSingle_input(ctx);
  }

  @Override
  public String visitFile_input(PythonArdvarkParser.File_inputContext ctx) {
    return super.visitFile_input(ctx);
  }

  @Override
  public String visitEval_input(PythonArdvarkParser.Eval_inputContext ctx) {
    return super.visitEval_input(ctx);
  }

  @Override
  public String visitDecorator(PythonArdvarkParser.DecoratorContext ctx) {
    return super.visitDecorator(ctx);
  }

  @Override
  public String visitDecorators(PythonArdvarkParser.DecoratorsContext ctx) {
    return super.visitDecorators(ctx);
  }

  @Override
  public String visitDecorated(PythonArdvarkParser.DecoratedContext ctx) {
    return super.visitDecorated(ctx);
  }

  @Override
  public String visitFuncdef(PythonArdvarkParser.FuncdefContext ctx) {
    return super.visitFuncdef(ctx);
  }

  @Override
  public String visitParameters(PythonArdvarkParser.ParametersContext ctx) {
    return super.visitParameters(ctx);
  }

  @Override
  public String visitTypedargslist(PythonArdvarkParser.TypedargslistContext ctx) {
    return super.visitTypedargslist(ctx);
  }

  @Override
  public String visitTfpdef(PythonArdvarkParser.TfpdefContext ctx) {
    return super.visitTfpdef(ctx);
  }

  @Override
  public String visitVarargslist(PythonArdvarkParser.VarargslistContext ctx) {
    return super.visitVarargslist(ctx);
  }

  @Override
  public String visitVfpdef(PythonArdvarkParser.VfpdefContext ctx) {
    return super.visitVfpdef(ctx);
  }

  @Override
  public String visitStmt(PythonArdvarkParser.StmtContext ctx) {
    return super.visitStmt(ctx);
  }

  @Override
  public String visitSimple_stmt(PythonArdvarkParser.Simple_stmtContext ctx) {
    return super.visitSimple_stmt(ctx);
  }

  @Override
  public String visitSmall_stmt(PythonArdvarkParser.Small_stmtContext ctx) {
    return super.visitSmall_stmt(ctx);
  }

  @Override
  public String visitExpr_stmt(PythonArdvarkParser.Expr_stmtContext ctx) {
    String msg = "";
    if(ctx.ASSIGN()!= null){
      String node1 = "";
      if(ctx.testlist_star_expr().size()>1) {
        node1 = ctx.testlist_star_expr(1).getText();
      }
      msg = ctx.testlist_star_expr(0).getText() +
          "=" + node1;
    }
    return msg + super.visitExpr_stmt(ctx);
  }

  @Override
  public String visitTestlist_star_expr(PythonArdvarkParser.Testlist_star_exprContext ctx) {
    return super.visitTestlist_star_expr(ctx);
  }

  @Override
  public String visitAugassign(PythonArdvarkParser.AugassignContext ctx) {
    return super.visitAugassign(ctx);
  }

  @Override
  public String visitDel_stmt(PythonArdvarkParser.Del_stmtContext ctx) {
    return super.visitDel_stmt(ctx);
  }

  @Override
  public String visitPass_stmt(PythonArdvarkParser.Pass_stmtContext ctx) {
    return super.visitPass_stmt(ctx);
  }

  @Override
  public String visitFlow_stmt(PythonArdvarkParser.Flow_stmtContext ctx) {
    return super.visitFlow_stmt(ctx);
  }

  @Override
  public String visitBreak_stmt(PythonArdvarkParser.Break_stmtContext ctx) {
    return super.visitBreak_stmt(ctx);
  }

  @Override
  public String visitContinue_stmt(PythonArdvarkParser.Continue_stmtContext ctx) {
    return super.visitContinue_stmt(ctx);
  }

  @Override
  public String visitReturn_stmt(PythonArdvarkParser.Return_stmtContext ctx) {
    return super.visitReturn_stmt(ctx);
  }

  @Override
  public String visitYield_stmt(PythonArdvarkParser.Yield_stmtContext ctx) {
    return super.visitYield_stmt(ctx);
  }

  @Override
  public String visitRaise_stmt(PythonArdvarkParser.Raise_stmtContext ctx) {
    return super.visitRaise_stmt(ctx);
  }

  @Override
  public String visitImport_stmt(PythonArdvarkParser.Import_stmtContext ctx) {
    return super.visitImport_stmt(ctx);
  }

  @Override
  public String visitImport_name(PythonArdvarkParser.Import_nameContext ctx) {
    return super.visitImport_name(ctx);
  }

  @Override
  public String visitImport_from(PythonArdvarkParser.Import_fromContext ctx) {
    return super.visitImport_from(ctx);
  }

  @Override
  public String visitImport_as_name(PythonArdvarkParser.Import_as_nameContext ctx) {
    return super.visitImport_as_name(ctx);
  }

  @Override
  public String visitDotted_as_name(PythonArdvarkParser.Dotted_as_nameContext ctx) {
    return super.visitDotted_as_name(ctx);
  }

  @Override
  public String visitImport_as_names(PythonArdvarkParser.Import_as_namesContext ctx) {
    return super.visitImport_as_names(ctx);
  }

  @Override
  public String visitDotted_as_names(PythonArdvarkParser.Dotted_as_namesContext ctx) {
    return super.visitDotted_as_names(ctx);
  }

  @Override
  public String visitDotted_name(PythonArdvarkParser.Dotted_nameContext ctx) {
    return super.visitDotted_name(ctx);
  }

  @Override
  public String visitGlobal_stmt(PythonArdvarkParser.Global_stmtContext ctx) {
    return super.visitGlobal_stmt(ctx);
  }

  @Override
  public String visitNonlocal_stmt(PythonArdvarkParser.Nonlocal_stmtContext ctx) {
    return super.visitNonlocal_stmt(ctx);
  }

  @Override
  public String visitAssert_stmt(PythonArdvarkParser.Assert_stmtContext ctx) {
    return super.visitAssert_stmt(ctx);
  }

  @Override
  public String visitCompound_stmt(PythonArdvarkParser.Compound_stmtContext ctx) {
    return super.visitCompound_stmt(ctx);
  }

  @Override
  public String visitIf_stmt(PythonArdvarkParser.If_stmtContext ctx) {
    return super.visitIf_stmt(ctx);
  }

  @Override
  public String visitWhile_stmt(PythonArdvarkParser.While_stmtContext ctx) {
    return super.visitWhile_stmt(ctx);
  }

  @Override
  public String visitFor_stmt(PythonArdvarkParser.For_stmtContext ctx) {
    return super.visitFor_stmt(ctx);
  }

  @Override
  public String visitTry_stmt(PythonArdvarkParser.Try_stmtContext ctx) {
    return super.visitTry_stmt(ctx);
  }

  @Override
  public String visitWith_stmt(PythonArdvarkParser.With_stmtContext ctx) {
    return super.visitWith_stmt(ctx);
  }

  @Override
  public String visitWith_item(PythonArdvarkParser.With_itemContext ctx) {
    return super.visitWith_item(ctx);
  }

  @Override
  public String visitExcept_clause(PythonArdvarkParser.Except_clauseContext ctx) {
    return super.visitExcept_clause(ctx);
  }

  @Override
  public String visitSuite(PythonArdvarkParser.SuiteContext ctx) {
    return super.visitSuite(ctx);
  }

  @Override
  public String visitTest(PythonArdvarkParser.TestContext ctx) {
    return super.visitTest(ctx);
  }

  @Override
  public String visitTest_nocond(PythonArdvarkParser.Test_nocondContext ctx) {
    return super.visitTest_nocond(ctx);
  }

  @Override
  public String visitLambdef(PythonArdvarkParser.LambdefContext ctx) {
    return super.visitLambdef(ctx);
  }

  @Override
  public String visitLambdef_nocond(PythonArdvarkParser.Lambdef_nocondContext ctx) {
    return super.visitLambdef_nocond(ctx);
  }

  @Override
  public String visitOr_test(PythonArdvarkParser.Or_testContext ctx) {
    return super.visitOr_test(ctx);
  }

  @Override
  public String visitAnd_test(PythonArdvarkParser.And_testContext ctx) {
    return super.visitAnd_test(ctx);
  }

  @Override
  public String visitNot_test(PythonArdvarkParser.Not_testContext ctx) {
    return super.visitNot_test(ctx);
  }

  @Override
  public String visitComparison(PythonArdvarkParser.ComparisonContext ctx) {
    return super.visitComparison(ctx);
  }

  @Override
  public String visitComp_op(PythonArdvarkParser.Comp_opContext ctx) {
    return super.visitComp_op(ctx);
  }

  @Override
  public String visitStar_expr(PythonArdvarkParser.Star_exprContext ctx) {
    return super.visitStar_expr(ctx);
  }

  @Override
  public String visitExpr(PythonArdvarkParser.ExprContext ctx) {
    return super.visitExpr(ctx);
  }

  @Override
  public String visitXor_expr(PythonArdvarkParser.Xor_exprContext ctx) {
    return super.visitXor_expr(ctx);
  }

  @Override
  public String visitAnd_expr(PythonArdvarkParser.And_exprContext ctx) {
    return super.visitAnd_expr(ctx);
  }

  @Override
  public String visitShift_expr(PythonArdvarkParser.Shift_exprContext ctx) {
    return super.visitShift_expr(ctx);
  }

  @Override
  public String visitArith_expr(PythonArdvarkParser.Arith_exprContext ctx) {
    return super.visitArith_expr(ctx);
  }

  @Override
  public String visitTerm(PythonArdvarkParser.TermContext ctx) {
    return super.visitTerm(ctx);
  }

  @Override
  public String visitFactor(PythonArdvarkParser.FactorContext ctx) {
    return super.visitFactor(ctx);
  }

  @Override
  public String visitPower(PythonArdvarkParser.PowerContext ctx) {
    return super.visitPower(ctx);
  }

  public String s_S(String k, String v){
    return String.format("(%S: %s)", k, v);
  }

  @Override
  public String visitAtom(PythonArdvarkParser.AtomContext ctx) {
    String msg = "";
    if(ctx.NAME()!= null){
      msg = s_S("NAME", ctx.NAME().getText());
    } else if(ctx.NONE()!= null){
      msg = s_S("NONE", ctx.NONE().getText());
    } else if(ctx.TRUE()!= null){
      msg = s_S("TRUE", ctx.TRUE().getText());
    } else if(ctx.FALSE()!= null){
      msg = s_S("FALSE", ctx.FALSE().getText());
    } else if(ctx.ELLIPSIS()!= null){
      msg = s_S("ELLIPSIS", ctx.ELLIPSIS().getText());
    } else if(ctx.str()!= null){
      StringBuilder buf = new StringBuilder();
      for(int i=0; i<ctx.str().size(); i++) {
        buf.append(" (").append(i).append(")");
        buf.append(ctx.str(i).getText());
      }
      msg += s_S("STR", buf.toString());
    } else if(ctx.number()!= null){
      msg += s_S("NUM", ctx.number().getText());
    } else if(ctx.OPEN_PAREN()!= null){
      msg += s_S("()", super.visitAtom(ctx) );
    } else if(ctx.OPEN_BRACK()!= null){
      msg += s_S("[]", super.visitAtom(ctx) );
    } else if(ctx.OPEN_BRACE()!= null){
      msg += s_S("{}", super.visitAtom(ctx) );
    }
    return println(msg);
  }

  @Override
  public String visitTestlist_comp(PythonArdvarkParser.Testlist_compContext ctx) {
    return super.visitTestlist_comp(ctx);
  }

  @Override
  public String visitTrailer(PythonArdvarkParser.TrailerContext ctx) {
    return super.visitTrailer(ctx);
  }

  @Override
  public String visitSubscriptlist(PythonArdvarkParser.SubscriptlistContext ctx) {
    return super.visitSubscriptlist(ctx);
  }

  @Override
  public String visitSubscript(PythonArdvarkParser.SubscriptContext ctx) {
    return super.visitSubscript(ctx);
  }

  @Override
  public String visitSliceop(PythonArdvarkParser.SliceopContext ctx) {
    return super.visitSliceop(ctx);
  }

  @Override
  public String visitExprlist(PythonArdvarkParser.ExprlistContext ctx) {
    return super.visitExprlist(ctx);
  }

  @Override
  public String visitTestlist(PythonArdvarkParser.TestlistContext ctx) {
    return super.visitTestlist(ctx);
  }

  @Override
  public String visitDictorsetmaker(PythonArdvarkParser.DictorsetmakerContext ctx) {
    return super.visitDictorsetmaker(ctx);
  }

  @Override
  public String visitClassdef(PythonArdvarkParser.ClassdefContext ctx) {
    return super.visitClassdef(ctx);
  }

  @Override
  public String visitArglist(PythonArdvarkParser.ArglistContext ctx) {
    return super.visitArglist(ctx);
  }

  @Override
  public String visitArgument(PythonArdvarkParser.ArgumentContext ctx) {
    return super.visitArgument(ctx);
  }

  @Override
  public String visitComp_iter(PythonArdvarkParser.Comp_iterContext ctx) {
    return super.visitComp_iter(ctx);
  }

  @Override
  public String visitComp_for(PythonArdvarkParser.Comp_forContext ctx) {
    return super.visitComp_for(ctx);
  }

  @Override
  public String visitComp_if(PythonArdvarkParser.Comp_ifContext ctx) {
    return super.visitComp_if(ctx);
  }

  @Override
  public String visitYield_expr(PythonArdvarkParser.Yield_exprContext ctx) {
    return super.visitYield_expr(ctx);
  }

  @Override
  public String visitYield_arg(PythonArdvarkParser.Yield_argContext ctx) {
    return super.visitYield_arg(ctx);
  }

  @Override
  public String visitStr(PythonArdvarkParser.StrContext ctx) {
    return super.visitStr(ctx);
  }

  @Override
  public String visitNumber(PythonArdvarkParser.NumberContext ctx) {
    return super.visitNumber(ctx);
  }

  @Override
  public String visitInteger(PythonArdvarkParser.IntegerContext ctx) {
    return super.visitInteger(ctx);
  }
}
