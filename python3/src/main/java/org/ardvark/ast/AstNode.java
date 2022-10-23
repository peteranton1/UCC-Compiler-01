package org.ardvark.ast;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@EqualsAndHashCode
public class AstNode {
  private NodeType nodeType;
  private String text;
  private List<AstNode> children;
  public int childCount() {
    return children != null? children.size(): 0;
  }

  @Override
  public String toString() {
    return "AstNode{" +
        "nodeType=" + nodeType +
        ", text='" + text + '\'' +
        ", children=" + children +
        '}';
  }
}
