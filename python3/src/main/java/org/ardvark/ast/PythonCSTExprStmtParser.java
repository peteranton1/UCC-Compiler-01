package org.ardvark.ast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.ardvark.Python3Parser;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class PythonCSTExprStmtParser {

  private final CstPanic panic;
  private final TokenTypeIdentifier tokenTypeIdentifier = new TokenTypeIdentifier();
  private final AstBuilderVisitor visitor;

  /*
/// expr_stmt: testlist_star_expr (augassign (yield_expr|testlist) |
///                      ('=' (yield_expr|testlist_star_expr))*)
   */
  public AstNode visitExpr_Stmt(Python3Parser.Expr_stmtContext ctx) {
    StringBuilder errBuf = new StringBuilder();
    errBuf.append("Error Recognising expr_stmt \n");
    AstNode astNode = null;
    if (ctx.getChildCount() > 1) {
      NodeType nodeType = tokenTypeIdentifier.identify(ctx.getChild(1));
      if (tokenTypeIdentifier.isInfix(nodeType)) {
        astNode = parseExpr_stmt(ctx, errBuf);
      } else {
        errBuf.append("unknown 1 \n");
        return panic.panic(ctx, errBuf);
      }
    } else if (ctx.getChildCount() == 1) {
      astNode = visitor.visitChildren(ctx);
    }
    if(astNode != null) {
      return astNode;
    }
    errBuf.append("unknown 2 \n");
    return panic.panic(ctx, errBuf);
  }

  public AstNode parseExpr_stmt(Python3Parser.Expr_stmtContext ctx, StringBuilder errBuf) {
    errBuf.append("parseExpr_stmt \n");
    NodeType nodeType = tokenTypeIdentifier.identify(ctx.getChild(1));
    if (tokenTypeIdentifier.isInfix(nodeType)) {
      AstNode astNode = visitor.visitChildren(ctx);
      List<AstNode> children = new ArrayList<>();
      if(astNode != null &&
          NodeType.AGG.equals(astNode.getNodeType())){
          children = astNode.getChildren();
      }

      return AstNode.builder()
          .nodeType(nodeType)
          .text(nodeType.getText())
          .children(children)
          .build();
    } else {
      return panic.panic(ctx, errBuf);
    }
  }

}
