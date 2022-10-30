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
  CALL_ARG("()"),
  CALL_SUB("[]"),
  CALL_DOT("."),
  DOTTED_NAME("DottedName"),
  DICT_OR_SET("{}"),
  DIV("/"),
  ELLIPSE("..."),
  EQUALS("="),
  FUNCDEF("def"),
  IMPORT_FROM("ImportFrom"),
  LIST("[]"),
  LITERAL("Literal"),
  MUL("*"),
  NAME("Name"),
  NUMBER("Number"),
  OP_COMPARE("Op_Compare"),
  OP_AND("and"),
  OP_OR("or"),
  OP_NOT("not"),
  OP_IF_BLOCK("IfBlock"),
  OP_IF_SUITE("IfSuite"),
  OP_IF_CONDITION("IfCondition"),
  OP_IF("if"),
  OP_ELIF("elif"),
  OP_ELSE("else"),
  PARAMS("params"),
  PARAM("param"),
  PASS("Pass"),
  STMT("Stmt"),
  STRING("String"),
  SUB("-")
  ;

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
