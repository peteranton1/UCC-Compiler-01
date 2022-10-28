package org.ardvark.ast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.List;

import static org.ardvark.ast.NodeType.AGG;
import static org.ardvark.ast.NodeType.LIST;

@AllArgsConstructor
@Getter
public class PythonCSTCompParser {

  private final CstPanic panic;
  private final StringUtils stringUtils = new StringUtils();
  private final AstBuilderVisitor visitor;

  /*
  /// testlist_comp: test ( comp_for | (',' test)* [','] )
   */
  public AstNode visitTestlist_comp(ParserRuleContext ctx) {
    StringBuilder errBuf = new StringBuilder();
    errBuf.append("Error Recognising Testlist_comp \n");
    return parseComp(ctx, errBuf);
  }

  public AstNode parseComp(ParserRuleContext ctx,
                            StringBuilder errBuf) {
    errBuf.append("parseComp \n");
    AstNode aggNode = visitor.visitChildren(ctx);
    List<AstNode> children;
    if(aggNode == null){
      children = List.of();
    } else if(AGG.equals(aggNode.getNodeType())){
      children = aggNode.getChildren();
    } else {
      children = List.of(aggNode);
    }
    return AstNode.builder()
        .nodeType(LIST)
        .text(LIST.getText())
        .children(children)
        .build();
  }

}