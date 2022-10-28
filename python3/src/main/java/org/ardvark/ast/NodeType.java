package org.ardvark.ast;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum NodeType {
  UNKNOWN("Unknown"),
  ADD("+"),
  AGG("Agg"),
  COLON(":"),
  DOTTED_NAME("DottedName"),
  DICT_OR_SET("{}"),
  DIV("/"),
  ELLIPSE("..."),
  EQUALS("="),
  IMPORT_FROM("ImportFrom"),
  LITERAL("Literal"),
  MUL("*"),
  NAME("Name"),
  NUMBER("Number"),
  STMT("Stmt"),
  STRING("String"),
  SUB("-"),
  LIST("[]");

  private final String text;

  public static NodeType textValueOf(String text) {
    for(NodeType nodeType: values()) {
      if(nodeType.getText().equals(text)){
        return nodeType;
      }
    }
    return UNKNOWN;
  }
}
