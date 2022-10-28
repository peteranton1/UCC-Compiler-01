package org.ardvark.ast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.ardvark.python3.Builder;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class PythonCSTArithParser {

  private final CstPanic panic;
  private final StringUtils stringUtils = new StringUtils();
  private final AstBuilderVisitor visitor;

  /*
    arith_expr
       : term ( '+' term
              | '-' term
              )*
       ;
   */
  public AstNode visitArith(ParserRuleContext ctx) {
    StringBuilder errBuf = new StringBuilder();
    errBuf.append("Error Recognising Arith \n");
    int childCount = ctx.getChildCount();
    if (childCount == 1) {
      return visitor.visitChildren(ctx);
    } else if (childCount > 2){
      return parseArith(ctx, errBuf);
    }
    errBuf.append("unknown 1 \n");
    String ctx1 = new Builder.Tree("").toStringASCII(ctx);
    return panic.panic(ctx1, errBuf);
  }

  public AstNode parseArith(ParserRuleContext ctx,
                            StringBuilder errBuf) {
    errBuf.append("parseArith \n");
    List<ParseTree> origChildren = ctx.children;
    AstNode aggNode = visitor.visitChildren(ctx);
    List<AstNode> aggChildren = new ArrayList<>(aggNode.getChildren());
    List<AstNode> reduceChildren;
    List<AstNode> tempChildren;
    int operPos = 1;
    ParseTree child1;
    AstNode astNode;
    while(operPos + 1 < origChildren.size()) {
      child1 = origChildren.get(operPos);
      operPos += 2;
      reduceChildren = new ArrayList<>();
      reduceChildren.add(aggChildren.get(0));
      reduceChildren.add(aggChildren.get(1));
      NodeType nodeType = NodeType.textValueOf(child1.getText());
      astNode = AstNode.builder()
          .nodeType(nodeType)
          .text(nodeType.getText())
          .children(reduceChildren)
          .build();
      int origSize = aggChildren.size();
      tempChildren = new ArrayList<>();
      tempChildren.add(astNode);
      for(int i=2;i<origSize; i++) {
        tempChildren.add(aggChildren.get(i));
      }
      aggChildren = tempChildren;
    }
    // check we got one node at the end
    if(aggChildren.size() == 1){
      return aggChildren.get(0);
    }
    errBuf.append(String.format(
        "unexpected size: %d \n", aggChildren.size()));
    return panic.panic(ctx, errBuf);
  }

}