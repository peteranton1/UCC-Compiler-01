package org.ardvark.ast;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum NodeType {
  UNKNOWN("Unknown"),
  IGNORE("Ignore"),
  // Atoms,
  AGG("Agg"),
  NAME("Name"),
  NUMBER("Number"),
  STRING("String"),
  ELLIPSE("..."),
  LITERAL("Literal"),
  DICT_OR_SET("DictOrSet"),
  STMT("Stmt"),
  IMPORT("Import"),
  DOTTED_NAME("DottedName"),
  // INFIX
  EQUALS("="),
  COLON(":"),
  COMMA(","),
  BLOCK("{}"),
  MUL("*"),
  DIV("/"),
  ADD("+"),
  SUB("-"),
  PCENT("%"),
  DBLSLASH("//"),
  AT("@"),
  LIST("List"),
  FUNC_DEF("FuncDef"),
  PARAMS("Params"),
  CALL("Call"),
  IF_EXPR("IfExpr"),
  COND("Cond")
  ;

  private final String text;

  public static NodeType textValueOf(String text) {
    for(NodeType nodeType: values()) {
      if(nodeType.getText().equals(text)){
        return nodeType;
      }
    }
    throw new AstRecogniserException(
        "Unrecognised NodeType text: " + text);
  }
}
