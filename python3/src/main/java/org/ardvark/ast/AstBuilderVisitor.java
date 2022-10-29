package org.ardvark.ast;

import org.ardvark.Python3BaseVisitor;
import org.ardvark.Python3Parser;

import java.util.ArrayList;
import java.util.List;

import static org.ardvark.ast.NodeType.AGG;

public class AstBuilderVisitor extends Python3BaseVisitor<AstNode> {

  private final PythonCSTAtomParser cstAtomParser;
  private final PythonCSTCompParser cstCompParser;
  private final PythonCSTCallParser cstCallParser;
  private final PythonCSTDictOrSetParser cstDictOrSetMakerParser;
  private final PythonCSTIfStmtParser cstIfStmtParser;
  private final PythonCSTImportParser cstImportParser;
  private final PythonCSTStmtParser cstStmtParser;
  private final PythonCSTArithParser cstArithParser;

  public AstBuilderVisitor() {
    CstPanic cstPanic = new CstPanic();
    cstAtomParser = new PythonCSTAtomParser(cstPanic, this);
    cstCompParser = new PythonCSTCompParser(cstPanic, this);
    cstCallParser = new PythonCSTCallParser(cstPanic, this);
    cstDictOrSetMakerParser = new PythonCSTDictOrSetParser(cstPanic, this);
    cstImportParser = new PythonCSTImportParser(cstPanic, this);
    cstIfStmtParser = new PythonCSTIfStmtParser(cstPanic, this);
    cstStmtParser = new PythonCSTStmtParser(cstPanic, this);
    cstArithParser = new PythonCSTArithParser(cstPanic, this);
  }

  @Override
  protected AstNode aggregateResult(AstNode aggregate, AstNode nextResult) {
    if(aggregate == null){
      return nextResult;
    } else if(nextResult == null){
      return aggregate;
    }
    List<AstNode> children = new ArrayList<>(deaggregate(aggregate));
    children.addAll(deaggregate(nextResult));
    NodeType aggType = AGG;
    return AstNode.builder()
        .nodeType(aggType)
        .text(aggType.getText())
        .children(children)
        .build();
  }

  private List<AstNode> deaggregate(AstNode astNode) {
    if(AGG.equals(astNode.getNodeType())){
      return astNode.getChildren();
    }
    return List.of(astNode);
  }

  @Override
  public AstNode visitSingle_input(Python3Parser.Single_inputContext ctx) {
    return super.visitSingle_input(ctx);
  }

  @Override
  public AstNode visitFile_input(Python3Parser.File_inputContext ctx) {
    return super.visitFile_input(ctx);
  }

  @Override
  public AstNode visitEval_input(Python3Parser.Eval_inputContext ctx) {
    return super.visitEval_input(ctx);
  }

  @Override
  public AstNode visitDecorator(Python3Parser.DecoratorContext ctx) {
    return super.visitDecorator(ctx);
  }

  @Override
  public AstNode visitDecorators(Python3Parser.DecoratorsContext ctx) {
    return super.visitDecorators(ctx);
  }

  @Override
  public AstNode visitDecorated(Python3Parser.DecoratedContext ctx) {
    return super.visitDecorated(ctx);
  }

  @Override
  public AstNode visitFuncdef(Python3Parser.FuncdefContext ctx) {
    return super.visitFuncdef(ctx);
  }

  @Override
  public AstNode visitParameters(Python3Parser.ParametersContext ctx) {
    return super.visitParameters(ctx);
  }

  @Override
  public AstNode visitTypedargslist(Python3Parser.TypedargslistContext ctx) {
    return super.visitTypedargslist(ctx);
  }

  @Override
  public AstNode visitTfpdef(Python3Parser.TfpdefContext ctx) {
    return super.visitTfpdef(ctx);
  }

  @Override
  public AstNode visitVarargslist(Python3Parser.VarargslistContext ctx) {
    return super.visitVarargslist(ctx);
  }

  @Override
  public AstNode visitVfpdef(Python3Parser.VfpdefContext ctx) {
    return super.visitVfpdef(ctx);
  }

  @Override
  public AstNode visitStmt(Python3Parser.StmtContext ctx) {
    return cstStmtParser.visitStmt(ctx);
  }

  @Override
  public AstNode visitSimple_stmt(Python3Parser.Simple_stmtContext ctx) {
    return super.visitSimple_stmt(ctx);
  }

  @Override
  public AstNode visitSmall_stmt(Python3Parser.Small_stmtContext ctx) {
    return cstArithParser.visitArith(ctx);
//    return super.visitSmall_stmt(ctx);
  }

  @Override
  public AstNode visitExpr_stmt(Python3Parser.Expr_stmtContext ctx) {
    return cstArithParser.visitArith(ctx);
  }

  @Override
  public AstNode visitTestlist_star_expr(Python3Parser.Testlist_star_exprContext ctx) {
    return super.visitTestlist_star_expr(ctx);
  }

  @Override
  public AstNode visitAugassign(Python3Parser.AugassignContext ctx) {
    return super.visitAugassign(ctx);
  }

  @Override
  public AstNode visitDel_stmt(Python3Parser.Del_stmtContext ctx) {
    return super.visitDel_stmt(ctx);
  }

  @Override
  public AstNode visitPass_stmt(Python3Parser.Pass_stmtContext ctx) {
    return cstIfStmtParser.visitPass_stmt(ctx);
  }

  @Override
  public AstNode visitFlow_stmt(Python3Parser.Flow_stmtContext ctx) {
    return super.visitFlow_stmt(ctx);
  }

  @Override
  public AstNode visitBreak_stmt(Python3Parser.Break_stmtContext ctx) {
    return super.visitBreak_stmt(ctx);
  }

  @Override
  public AstNode visitContinue_stmt(Python3Parser.Continue_stmtContext ctx) {
    return super.visitContinue_stmt(ctx);
  }

  @Override
  public AstNode visitReturn_stmt(Python3Parser.Return_stmtContext ctx) {
    return super.visitReturn_stmt(ctx);
  }

  @Override
  public AstNode visitYield_stmt(Python3Parser.Yield_stmtContext ctx) {
    return super.visitYield_stmt(ctx);
  }

  @Override
  public AstNode visitRaise_stmt(Python3Parser.Raise_stmtContext ctx) {
    return super.visitRaise_stmt(ctx);
  }

  @Override
  public AstNode visitImport_stmt(Python3Parser.Import_stmtContext ctx) {
    return super.visitImport_stmt(ctx);
  }

  @Override
  public AstNode visitImport_name(Python3Parser.Import_nameContext ctx) {
    return super.visitImport_name(ctx);
  }

  @Override
  public AstNode visitImport_from(Python3Parser.Import_fromContext ctx) {
    return cstImportParser.visitImport_from(ctx);
  }

  @Override
  public AstNode visitImport_as_name(Python3Parser.Import_as_nameContext ctx) {
    return super.visitImport_as_name(ctx);
  }

  @Override
  public AstNode visitDotted_as_name(Python3Parser.Dotted_as_nameContext ctx) {
    return super.visitDotted_as_name(ctx);
  }

  @Override
  public AstNode visitImport_as_names(Python3Parser.Import_as_namesContext ctx) {
    return super.visitImport_as_names(ctx);
  }

  @Override
  public AstNode visitDotted_as_names(Python3Parser.Dotted_as_namesContext ctx) {
    return super.visitDotted_as_names(ctx);
  }

  @Override
  public AstNode visitDotted_name(Python3Parser.Dotted_nameContext ctx) {
    return cstAtomParser.visitDotted_name(ctx);
  }

  @Override
  public AstNode visitGlobal_stmt(Python3Parser.Global_stmtContext ctx) {
    return super.visitGlobal_stmt(ctx);
  }

  @Override
  public AstNode visitNonlocal_stmt(Python3Parser.Nonlocal_stmtContext ctx) {
    return super.visitNonlocal_stmt(ctx);
  }

  @Override
  public AstNode visitAssert_stmt(Python3Parser.Assert_stmtContext ctx) {
    return super.visitAssert_stmt(ctx);
  }

  @Override
  public AstNode visitCompound_stmt(Python3Parser.Compound_stmtContext ctx) {
    return super.visitCompound_stmt(ctx);
  }

  @Override
  public AstNode visitIf_stmt(Python3Parser.If_stmtContext ctx) {
    return super.visitIf_stmt(ctx);
  }

  @Override
  public AstNode visitWhile_stmt(Python3Parser.While_stmtContext ctx) {
    return super.visitWhile_stmt(ctx);
  }

  @Override
  public AstNode visitFor_stmt(Python3Parser.For_stmtContext ctx) {
    return super.visitFor_stmt(ctx);
  }

  @Override
  public AstNode visitTry_stmt(Python3Parser.Try_stmtContext ctx) {
    return super.visitTry_stmt(ctx);
  }

  @Override
  public AstNode visitWith_stmt(Python3Parser.With_stmtContext ctx) {
    return super.visitWith_stmt(ctx);
  }

  @Override
  public AstNode visitWith_item(Python3Parser.With_itemContext ctx) {
    return super.visitWith_item(ctx);
  }

  @Override
  public AstNode visitExcept_clause(Python3Parser.Except_clauseContext ctx) {
    return super.visitExcept_clause(ctx);
  }

  @Override
  public AstNode visitSuite(Python3Parser.SuiteContext ctx) {
    return super.visitSuite(ctx);
  }

  @Override
  public AstNode visitTest(Python3Parser.TestContext ctx) {
    return super.visitTest(ctx);
  }

  @Override
  public AstNode visitTest_nocond(Python3Parser.Test_nocondContext ctx) {
    return super.visitTest_nocond(ctx);
  }

  @Override
  public AstNode visitLambdef(Python3Parser.LambdefContext ctx) {
    return super.visitLambdef(ctx);
  }

  @Override
  public AstNode visitLambdef_nocond(Python3Parser.Lambdef_nocondContext ctx) {
    return super.visitLambdef_nocond(ctx);
  }

  @Override
  public AstNode visitOr_test(Python3Parser.Or_testContext ctx) {
    return cstIfStmtParser.visitOr_test(ctx);
  }

  @Override
  public AstNode visitAnd_test(Python3Parser.And_testContext ctx) {
    return cstIfStmtParser.visitAnd_test(ctx);
  }

  @Override
  public AstNode visitNot_test(Python3Parser.Not_testContext ctx) {
    return cstIfStmtParser.visitNot_test(ctx);
  }

  @Override
  public AstNode visitComparison(Python3Parser.ComparisonContext ctx) {
    return super.visitComparison(ctx);
  }

  @Override
  public AstNode visitComp_op(Python3Parser.Comp_opContext ctx) {
    return cstIfStmtParser.visitComp_op(ctx);
  }

  @Override
  public AstNode visitStar_expr(Python3Parser.Star_exprContext ctx) {
    return super.visitStar_expr(ctx);
  }

  @Override
  public AstNode visitExpr(Python3Parser.ExprContext ctx) {
    return super.visitExpr(ctx);
  }

  @Override
  public AstNode visitXor_expr(Python3Parser.Xor_exprContext ctx) {
    return super.visitXor_expr(ctx);
  }

  @Override
  public AstNode visitAnd_expr(Python3Parser.And_exprContext ctx) {
    return super.visitAnd_expr(ctx);
  }

  @Override
  public AstNode visitShift_expr(Python3Parser.Shift_exprContext ctx) {
    return super.visitShift_expr(ctx);
  }

  @Override
  public AstNode visitArith_expr(Python3Parser.Arith_exprContext ctx) {
    return cstArithParser.visitArith(ctx);
  }

  @Override
  public AstNode visitTerm(Python3Parser.TermContext ctx) {
    return cstArithParser.visitArith(ctx);
  }

  @Override
  public AstNode visitFactor(Python3Parser.FactorContext ctx) {
    return super.visitFactor(ctx);
  }

  @Override
  public AstNode visitPower(Python3Parser.PowerContext ctx) {
    return cstAtomParser.visitPower(ctx);
  }

  @Override
  public AstNode visitAtom(Python3Parser.AtomContext ctx) {
    return cstAtomParser.visitAtom(ctx);
  }

  @Override
  public AstNode visitTestlist_comp(Python3Parser.Testlist_compContext ctx) {
    return cstCompParser.visitTestlist_comp(ctx);
  }

  @Override
  public AstNode visitTrailer(Python3Parser.TrailerContext ctx) {
    return cstCallParser.visitTrailer(ctx);
  }

  @Override
  public AstNode visitSubscriptlist(Python3Parser.SubscriptlistContext ctx) {
    return super.visitSubscriptlist(ctx);
  }

  @Override
  public AstNode visitSubscript(Python3Parser.SubscriptContext ctx) {
    return super.visitSubscript(ctx);
  }

  @Override
  public AstNode visitSliceop(Python3Parser.SliceopContext ctx) {
    return super.visitSliceop(ctx);
  }

  @Override
  public AstNode visitExprlist(Python3Parser.ExprlistContext ctx) {
    return super.visitExprlist(ctx);
  }

  @Override
  public AstNode visitTestlist(Python3Parser.TestlistContext ctx) {
    return super.visitTestlist(ctx);
  }

  @Override
  public AstNode visitDictorsetmaker(Python3Parser.DictorsetmakerContext ctx) {
    return cstDictOrSetMakerParser.visitDictOrSet(ctx);
  }

  @Override
  public AstNode visitClassdef(Python3Parser.ClassdefContext ctx) {
    return super.visitClassdef(ctx);
  }

  @Override
  public AstNode visitArglist(Python3Parser.ArglistContext ctx) {
    return super.visitArglist(ctx);
  }

  @Override
  public AstNode visitArgument(Python3Parser.ArgumentContext ctx) {
    return super.visitArgument(ctx);
  }

  @Override
  public AstNode visitComp_iter(Python3Parser.Comp_iterContext ctx) {
    return super.visitComp_iter(ctx);
  }

  @Override
  public AstNode visitComp_for(Python3Parser.Comp_forContext ctx) {
    return super.visitComp_for(ctx);
  }

  @Override
  public AstNode visitComp_if(Python3Parser.Comp_ifContext ctx) {
    return super.visitComp_if(ctx);
  }

  @Override
  public AstNode visitYield_expr(Python3Parser.Yield_exprContext ctx) {
    return super.visitYield_expr(ctx);
  }

  @Override
  public AstNode visitYield_arg(Python3Parser.Yield_argContext ctx) {
    return super.visitYield_arg(ctx);
  }

  @Override
  public AstNode visitStr(Python3Parser.StrContext ctx) {
    return AstNode.builder()
        .nodeType(NodeType.STRING)
        .text(ctx.getText())
        .build();
  }

  @Override
  public AstNode visitNumber(Python3Parser.NumberContext ctx) {
    return AstNode.builder()
        .nodeType(NodeType.NUMBER)
        .text(ctx.getText())
        .build();
  }

  @Override
  public AstNode visitInteger(Python3Parser.IntegerContext ctx) {
    return AstNode.builder()
        .nodeType(NodeType.NUMBER)
        .text(ctx.getText())
        .build();
  }
}
