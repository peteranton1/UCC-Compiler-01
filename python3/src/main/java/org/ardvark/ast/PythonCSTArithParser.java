package org.ardvark.ast;

import lombok.Getter;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.ardvark.python3.Builder;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PythonCSTArithParser {

  private final AstBuilderVisitor visitor;
  private final PythonCSTBaseParser baseParser;

  public PythonCSTArithParser(AstBuilderVisitor visitor) {
    this.visitor = visitor;
    this.baseParser = visitor.baseParser;
  }

  public AstNode visitSmall_stmt(ParserRuleContext ctx) {
    return parseArith(ctx, baseParser.errBuf("Small_stmt"));
  }

  public AstNode visitExpr_stmt(ParserRuleContext ctx) {
    return parseArith(ctx, baseParser.errBuf("Expr_stmt"));
  }

  /*
  /// arith_expr: term (('+'|'-') term)*
   */
  public AstNode visitArith(ParserRuleContext ctx) {
    return parseArith(ctx, baseParser.errBuf("Arith"));
  }

  /*
  /// term: factor (('*'|'/'|'%'|'//') factor)*
   */
  public AstNode visitTerm(ParserRuleContext ctx) {
    return parseArith(ctx, baseParser.errBuf("Term"));
  }

  public AstNode parseArith(ParserRuleContext ctx,
                            StringBuilder errBuf) {
    errBuf.append("parseArith \n");
    int childCount = ctx.getChildCount();
    if (childCount == 0) {
      errBuf.append("unknown 1 \n");
      String ctx1 = new Builder.Tree("").toStringASCII(ctx);
      return baseParser.panic.panic(ctx1, errBuf);
    } else if (childCount == 1){
      return visitor.visitChildren(ctx);
    }
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
    return baseParser.panic.panic(ctx, errBuf);
  }
}
