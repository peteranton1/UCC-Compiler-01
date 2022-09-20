package org.ardvark.man2;

public class LookaheadParser extends Parser {

  public LookaheadParser(Lexer input, int k) {
    super(input, k);
  }

  public void list() {
    match(LookaheadLexer.LBRACK);
    elements();
    match(LookaheadLexer.RBRACK);
  }

  public void elements() {
    element();
    while (LA(1) == LookaheadLexer.COMMA) {
      match(LookaheadLexer.COMMA);
      element();
    }
  }

  public void element() {
    if (LA(1) == LookaheadLexer.NAME &&
      LA(2) == LookaheadLexer.EQUALS) {
      match(LookaheadLexer.NAME);
      match(LookaheadLexer.EQUALS);
      match(LookaheadLexer.NUMBER);
    }
    else if (LA(1) == LookaheadLexer.NAME)
      match(LookaheadLexer.NAME);
    else if (LA(1) == LookaheadLexer.NUMBER)
      match(LookaheadLexer.NUMBER);
    else if (LA(1) == LookaheadLexer.LBRACK)
      list();
    else
      throw new Error("expecting name, name=number, " +
          "number or list; found = " + LT(1));
  }
}
