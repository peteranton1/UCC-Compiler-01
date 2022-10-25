package org.ardvark.ast;

import org.antlr.v4.runtime.tree.ParseTree;

import static org.ardvark.ast.NodeType.*;

public class TokenTypeIdentifier {


  public boolean isInfix(NodeType nodeType) {
    return switch (nodeType) {
      case EQUALS,
          COLON,
          COMMA,
          MULTIPLY,
          PLUS,
          SUBTRACT,
          DIVIDE -> true;
      default -> false;
    };
  }

  public boolean isColon(ParseTree tree) {
    return COLON.equals(identify(tree));
  }

  public NodeType identify(ParseTree tree) {
    String text = tree.getText();
    char ch = text != null && text.length()>0 ? text.charAt(0) : '\0';
    if('=' == ch){
      return EQUALS;
    } else if(':' == ch){
      return COLON;
    } else if(',' == ch){
      return COMMA;
    } else if('*' == ch){
      return MULTIPLY;
    } else if('+' == ch){
      return PLUS;
    } else if('-' == ch){
      return SUBTRACT;
    } else if('/' == ch){
      return DIVIDE;
    } else if('"' == ch){
      return STRING;
    } else if(Character.isDigit(ch)){
      return NUMBER;
    } else if("None".equals(text)){
      return LITERAL;
    } else if("False".equals(text)){
      return LITERAL;
    } else if("True".equals(text)){
      return LITERAL;
    } else if(Character.isAlphabetic(ch)){
      return NAME;
    } else {
      return UNKNOWN;
    }
  }
}
