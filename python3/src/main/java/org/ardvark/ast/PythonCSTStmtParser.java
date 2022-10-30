package org.ardvark.ast;

import lombok.Getter;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.List;

import static org.ardvark.ast.NodeType.AGG;
import static org.ardvark.ast.NodeType.STMT;

@Getter
public class PythonCSTStmtParser {

  private final AstBuilderVisitor visitor;
  private final PythonCSTBaseParser baseParser;

  public PythonCSTStmtParser(AstBuilderVisitor visitor) {
    this.visitor = visitor;
    this.baseParser = visitor.baseParser;
  }



  /*
    /// stmt: simple_stmt | compound_stmt
     */
  public AstNode visitStmt(ParserRuleContext ctx) {
    return parseSimpleStmt(ctx, baseParser.errBuf("Stmt"));
  }

  public AstNode parseSimpleStmt(ParserRuleContext ctx,
                                 StringBuilder errBuf) {
    errBuf.append("parseSimpleStmt \n");
    AstNode aggNode = visitor.visitChildren(ctx);
    if(aggNode == null){
      throw new AstRecogniserException(
          "parseSimpleStmt: agg node was null");
    }
    NodeType nodeType = aggNode.getNodeType();
    List<AstNode> outChildren;
    if(AGG.equals(nodeType)) {
      outChildren = aggNode.getChildren();
    } else {
      outChildren = List.of(aggNode);
    }
    return AstNode.builder()
        .nodeType(NodeType.STMT)
        .text(STMT.getText())
        .children(outChildren)
        .build();
  }
}
