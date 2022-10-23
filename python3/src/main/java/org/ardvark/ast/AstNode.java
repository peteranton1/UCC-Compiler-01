package org.ardvark.ast;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
@EqualsAndHashCode
public class AstNode {
  private NodeType nodeType;
  private String text;
  private List<AstNode> children;
}
