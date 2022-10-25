package org.ardvark.ast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.antlr.v4.runtime.tree.ParseTree;
import org.ardvark.Python3Parser;

import java.util.ArrayList;
import java.util.List;

import static org.ardvark.ast.NodeType.*;

@AllArgsConstructor
@Getter
public class PythonCSTDictOrSetMakerParser {

  private final CstPanic panic;
  private final TokenTypeIdentifier tokenTypeIdentifier = new TokenTypeIdentifier();
  private final AstBuilderVisitor visitor;

  /*
/// expr_stmt: testlist_star_expr (augassign (yield_expr|testlist) |
///                      ('=' (yield_expr|testlist_star_expr))*)
   */
  public AstNode visitDictorsetmaker(Python3Parser.DictorsetmakerContext ctx) {
    StringBuilder errBuf = new StringBuilder();
    errBuf.append("Error Recognising Dictorsetmaker \n");
    AstNode astNode = null;
    if (ctx.getChildCount() > 1) {
      astNode = parseDictorsetmaker(ctx, errBuf);
    } else if (ctx.getChildCount() == 1) {
      astNode = visitor.visitChildren(ctx);
    }
    if (astNode != null) {
      return astNode;
    }
    errBuf.append("unknown 2 \n");
    return panic.panic(ctx, errBuf);
  }

  public AstNode parseDictorsetmaker(Python3Parser.DictorsetmakerContext ctx, StringBuilder errBuf) {
    errBuf.append("parseDictorsetmaker \n");
    List<AstNode> reducedChildren = parseDictOrSetItems(ctx);
    if(reducedChildren.size()>0){
      NodeType nodeType = STMT_LIST;
      return AstNode.builder()
          .nodeType(nodeType)
          .text(nodeType.getText())
          .children(reducedChildren)
          .build();
    } else {
      return panic.panic(ctx, errBuf);
    }
  }

  public List<AstNode> parseDictOrSetItems(Python3Parser.DictorsetmakerContext ctx) {
    boolean isDict = isDictionary(ctx.children);
    AstNode astNode = visitor.visitChildren(ctx);
    List<AstNode> children = new ArrayList<>();
    if (astNode == null || astNode.childCount() == 0) {
      return children;
    }
    if (isDict) {
      children.addAll(reduceDictItems(astNode));
    } else {
      children.addAll(astNode.getChildren());
    }
    return children;
  }

  public List<AstNode> reduceDictItems(AstNode astNode) {
    List<AstNode> origChildren = astNode.getChildren();
    List<AstNode> outChildren = new ArrayList<>();
    int leftPos = 0;
    int rightPos = 1;
    while (rightPos < origChildren.size()) {
      AstNode leftNode = origChildren.get(leftPos);
      AstNode rightNode = origChildren.get(rightPos);
      outChildren.add(AstNode.builder()
          .nodeType(COLON)
          .text(COLON.getText())
          .children(List.of(leftNode, rightNode))
          .build());
      leftPos += 2;
      rightPos += 2;
      if (leftPos < origChildren.size() &&
          COMMA.equals(origChildren.get(leftPos).getNodeType())) {
        leftPos += 1;
        rightPos += 1;
      }
    }
    return outChildren;
  }

  public boolean isDictionary(List<ParseTree> origChildren) {
    if (origChildren != null && origChildren.size() > 1) {
      return tokenTypeIdentifier.isColon(origChildren.get(1));
    }
    return false;
  }

}
