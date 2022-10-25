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
  STMT_LIST("StmtList"),
  STMT("Stmt"),
  IMPORT("Import"),
  DOTTED_NAME("DottedName"),
  // INFIX
  EQUALS("="),
  COLON(":"),
  COMMA(","),
  BLOCK("{}"),
  MULTIPLY("*"),
  DIVIDE("/"),
  PLUS("+"),
  SUBTRACT("-"),
  LIST("List"),
  FUNC_DEF("FuncDef"),
  PARAMS("Params"),
  CALL("Call"),
  IF_EXPR("IfExpr"),
  COND("Cond")
  ;

  private final String text;
}
