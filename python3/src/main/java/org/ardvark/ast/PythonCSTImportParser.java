package org.ardvark.ast;

import lombok.Getter;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.RuleNode;

import java.util.List;

import static org.ardvark.ast.NodeType.AGG;
import static org.ardvark.ast.NodeType.IMPORT_FROM;

@Getter
public class PythonCSTImportParser {

  private final AstBuilderVisitor visitor;
  private final PythonCSTBaseParser baseParser;

  public PythonCSTImportParser(AstBuilderVisitor visitor) {
    this.visitor = visitor;
    this.baseParser = visitor.baseParser;
  }

  /*
    /// simple_stmt: small_stmt (';' small_stmt)* [';'] NEWLINE
     */
  public AstNode visitImport_from(ParserRuleContext ctx) {
    StringBuilder errBuf = new StringBuilder();
    errBuf.append("Error Recognising Import_from \n");
    return parseImportFrom(ctx, errBuf);
  }

  public AstNode parseImportFrom(ParserRuleContext ctx,
                                 StringBuilder errBuf) {
    errBuf.append("parseImportFrom \n");
    AstNode aggNode = visitor.visitChildren(ctx);
    if(aggNode == null){
      aggNode = visitor.visitChildren((RuleNode)ctx.getChild(1));
    }
    NodeType nodeType = aggNode.getNodeType();
    List<AstNode> outChildren;
    if(AGG.equals(nodeType)) {
      outChildren = aggNode.getChildren();
    } else {
      outChildren = List.of(aggNode);
    }
    return AstNode.builder()
        .nodeType(IMPORT_FROM)
        .text(IMPORT_FROM.getText())
        .children(outChildren)
        .build();
  }
}
