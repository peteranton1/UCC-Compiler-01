package org.ardvark.ast;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum NodeType {
  UNKNOWN("Unknown"),
  ADD("+"),
  ATOM_PLUS("AtomPlus"),
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
  OP_COMPARE("Op_Compare"),
  OP_AND("and"),
  OP_OR("or"),
  OP_NOT("not"),
  PASS("Pass"),
  STMT("Stmt"),
  STRING("String"),
  SUB("-"),
  CALL_ARG("()"),
  CALL_SUB("[]"),
  CALL_DOT("."),
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
