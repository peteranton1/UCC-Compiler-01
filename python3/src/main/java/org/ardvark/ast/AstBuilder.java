package org.ardvark.ast;

import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;

import static org.ardvark.ast.NodeType.*;

@Slf4j
public class AstBuilder {

  private final TokenTypeIdentifier tokenIdentifier = new TokenTypeIdentifier();

  public static List<AstNode> recognise(ParseTree tree,
                                        List<AstNode> astList) {
    if (astList.size() >= 3) {
      NodeType nodeType = astList.get(1).getNodeType();
      if (EQUALS.equals(nodeType) ||
          MULTIPLY.equals(nodeType) ||
          DIVIDE.equals(nodeType) ||
          PLUS.equals(nodeType) ||
          SUBTRACT.equals(nodeType)) {
        List<AstNode> children = astList.stream()
            .filter(i -> !nodeType.equals(i.getNodeType()))
            .toList();
        return List.of(AstNode.builder()
            .nodeType(nodeType)
            .text(nodeType.getText())
            .children(children)
            .build());
      }
    }
    return astList;
  }

  public AstNode toAST(ParseTree tree) {

    List<AstNode> astNodes = dfs(tree);
    return AstNode.builder()
        .nodeType(ROOT)
        .text(ROOT.getText())
        .children(astNodes)
        .build();
  }

  public List<AstNode> dfs(ParseTree tree) {
    if (tree != null) {
      List<AstNode> astList = new ArrayList<>();
      for (int i = 0; i < tree.getChildCount(); i++) {
        List<AstNode> astNodes = dfs(tree.getChild(i));
        if (astNodes.size() > 0) {
          astList.addAll(astNodes.stream()
              .filter(n -> !IGNORE.equals(n.getNodeType()))
              .toList());
        }
      }
      return visitNode(tree, astList);
    }
    return List.of();
  }

  public List<AstNode> visitNode(ParseTree tree,
                                 List<AstNode> astList) {
    // Implement
    if (tree instanceof TerminalNode) {
      Token token = ((TerminalNode) tree).getSymbol();
      NodeType nodeType = tokenIdentifier.identify(token);
      return List.of(AstNode.builder()
          .nodeType(nodeType)
          .text(tree.getText())
          .build());
    } else if (astList.size() >= 3) {
      return recognise(tree, astList);
    } else if (astList.size() >= 1) {
      return astList;
    }
    log.info("Unrecognised(1) {}", tree);
    return astList;
  }
}
