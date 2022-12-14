package org.ardvark.ast;

import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.List;

import static org.ardvark.ast.NodeType.AGG;
import static org.ardvark.ast.NodeType.DICT_OR_SET;

@Slf4j
public class AstBuilder {

  public AstNode toAST(ParseTree tree) {

    AstBuilderVisitor visitor = new AstBuilderVisitor();
    AstNode aggNode = visitor.visit(tree);
    List<AstNode> children;
    if(AGG.equals(aggNode.getNodeType())){
      children = aggNode.getChildren();
    } else {
      children = List.of(aggNode);
    }
    return AstNode.builder()
          .nodeType(DICT_OR_SET)
          .text(DICT_OR_SET.getText())
          .children(children)
          .build();
  }

  public void walk(AstNode astNode, StringBuilder builder) {

    List<AstNode> firstStack = new ArrayList<>();
    firstStack.add(astNode);

    List<List<AstNode>> childListStack = new ArrayList<>();
    childListStack.add(firstStack);

    while (!childListStack.isEmpty()) {

      List<AstNode> childStack = childListStack.get(childListStack.size() - 1);

      if (childStack.isEmpty()) {
        childListStack.remove(childListStack.size() - 1);
      } else {
        astNode = childStack.remove(0);

        String node = astNode.getClass()
            .getSimpleName()
            .replace("Context", "");
        node = Character.toLowerCase(node.charAt(0)) + node.substring(1);

        String indent = "";

        for (int i = 0; i < childListStack.size() - 1; i++) {
          indent += (childListStack.get(i).size() > 0) ? "|  " : "   ";
        }

        String text;

        if (astNode.getText().trim().isEmpty()) {
          text = "<" + astNode.getNodeType() + ">";
        } else {
          text = astNode.getText()
              .replace("\r", "")
              .replace("\n", "\\n");
        }

        builder.append(indent)
            .append(childStack.isEmpty() ? "'- " : "|- ")
            .append(text.trim().length() > 0 ? text : node)
            .append("\n");

        if (astNode.childCount() > 0) {
          List<AstNode> children = new ArrayList<>(astNode.getChildren());
          childListStack.add(children);
        }
      }
    }
  }

}
