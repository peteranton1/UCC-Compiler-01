package org.ardvark.ast;

import org.antlr.v4.runtime.Token;
import org.ardvark.Python3Parser;

public class TokenTypeIdentifier {
  public NodeType identify(Token token) {
    int tokenType = token.getType();
    return switch (tokenType) {
      case Python3Parser.ASSIGN -> NodeType.EQUALS;
      case Python3Parser.COLON -> NodeType.COLON;
      case Python3Parser.OPEN_BRACE -> NodeType.BLOCK;
      case Python3Parser.STAR -> NodeType.MULTIPLY;
      case Python3Parser.ADD -> NodeType.PLUS;
      case Python3Parser.MINUS -> NodeType.SUBTRACT;
      case Python3Parser.DIV -> NodeType.DIVIDE;
      case Python3Parser.STRING_LITERAL -> NodeType.STRING;
      case Python3Parser.NAME -> NodeType.NAME;
      case Python3Parser.DECIMAL_INTEGER -> NodeType.NUMBER;
      case Python3Parser.NEWLINE,
          Python3Parser.EOF -> NodeType.IGNORE;
      default -> NodeType.UNKNOWN;
    };
  }
}
