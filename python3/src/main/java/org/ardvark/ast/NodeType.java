package org.ardvark.ast;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum NodeType {
  UNKNOWN("Unknown"),
  IGNORE("Ignore"),
  ROOT("Root"),
  STMT("Stmt"),
  IMPORT("Import"),
  DOTTED_NAME("DottedName"),
  EQUALS("="),
  DICT_OR_SET("DictOrSet"),
  COLON(":"),
  BLOCK("{}"),
  MULTIPLY("*"),
  DIVIDE("/"),
  PLUS("+"),
  SUBTRACT("-"),
  NAME("Name"),
  NUMBER("Number"),
  STRING("String"),
  LIST("List"),
  FUNC_DEF("FuncDef"),
  PARAMS("Params"),
  CALL("Call"),
  IF_EXPR("IfExpr"),
  COND("Cond"),
  END_OF_FILE("Eof")
  ;

  private final String text;
}
