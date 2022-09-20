package org.ardvark.man3;

public class BacktrackLexer extends Lexer {

  public static int NAME = 2;
  public static int NUMBER = 3;
  public static final int EQUALS = 4;
  public static int COMMA = 5;
  public static int LBRACK = 6;
  public static int RBRACK = 7;
  public static String[] tokenNames =
      {"n/a", "<EOF>", "NAME", "NUMBER", "EQUALS", "COMMA", "LBRACK", "RBRACK"};

  public BacktrackLexer(String input) {
    super(input);
  }

  @Override
  public String getTokenName(int tokenType) {
    return tokenNames[tokenType];
  }

  public boolean isLETTER() {
    return Character.isLetter(c);
  }

  public boolean isNUMBER() {
    return Character.isDigit(c);
  }

  @Override
  public Token nextToken() {
    while (c != EOF) {
      switch (c) {
        case ' ', '\t', '\n', '\r' -> WS();
        case ',' -> {
          consume();
          return new Token(COMMA, ",");
        }
        case '=' -> {
          consume();
          return new Token(EQUALS, "=");
        }
        case '[' -> {
          consume();
          return new Token(LBRACK, "[");
        }
        case ']' -> {
          consume();
          return new Token(RBRACK, "]");
        }
        default -> {
          if (isLETTER()) return NAME();
          else if (isNUMBER()) return NUMBER();
          throw new Error("Invalid character: " + c);
        }
      }
    }
    return new Token(EOF_TYPE, "<EOF>");
  }

  private Token NAME() {
    StringBuilder buf = new StringBuilder();
    do {
      buf.append(c);
      consume();
    } while (isLETTER());
    return new Token(NAME, buf.toString());
  }

  private Token NUMBER() {
    StringBuilder buf = new StringBuilder();
    do {
      buf.append(c);
      consume();
    } while (isNUMBER());
    return new Token(NUMBER, buf.toString());
  }

  private void WS() {
    while (c == ' ' ||
        c == '\t' ||
        c == '\n' ||
        c == '\r')
      consume();
  }
}
