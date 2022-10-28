package org.ardvark.ast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.List;

import static org.ardvark.ast.NodeType.DICT_OR_SET;

@AllArgsConstructor
@Getter
public class PythonCSTDictOrSetParser {

  private final CstPanic panic;
  private final AstBuilderVisitor visitor;

  /*
/// expr_stmt: testlist_star_expr (augassign (yield_expr|testlist) |
///                      ('=' (yield_expr|testlist_star_expr))*)
   */
  public AstNode visitDictOrSet(ParserRuleContext ctx) {
    StringBuilder errBuf = new StringBuilder();
    errBuf.append("Error Recognising DictOrSet \n");
    int childCount = ctx.getChildCount();
    if (childCount == 1) {
      return visitor.visitChildren(ctx);
    }
    return parseDictOrSet(ctx, errBuf);
  }

  public AstNode parseDictOrSet(ParserRuleContext ctx,
                                StringBuilder errBuf) {
    errBuf.append("parseDictOrSet \n");
    List<ParseTree> origChildren = ctx.children;
    AstNode aggNode = visitor.visitChildren(ctx);
    List<AstNode> aggChildren = new ArrayList<>(aggNode.getChildren());
    List<AstNode> reduceChildren;
    List<AstNode> outChildren = new ArrayList<>();
    int operPos = 1;
    int pairPos = 0;
    ParseTree child1;
    AstNode astNode;
    while(operPos + 1 < origChildren.size()) {
      child1 = origChildren.get(operPos);
      operPos += 4;
      reduceChildren = new ArrayList<>();
      reduceChildren.add(aggChildren.get(pairPos));
      reduceChildren.add(aggChildren.get(pairPos + 1));
      pairPos += 2;
      NodeType nodeType = NodeType.textValueOf(child1.getText());
      astNode = AstNode.builder()
          .nodeType(nodeType)
          .text(nodeType.getText())
          .children(reduceChildren)
          .build();
      outChildren.add(astNode);
    }
    return AstNode.builder()
        .nodeType(DICT_OR_SET)
        .text(DICT_OR_SET.getText())
        .children(outChildren)
        .build();
  }
}