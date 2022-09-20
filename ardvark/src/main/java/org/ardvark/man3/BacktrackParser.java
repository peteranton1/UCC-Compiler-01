package org.ardvark.man3;

import org.ardvark.man2.LookaheadLexer;

public class BacktrackParser extends Parser {

  public BacktrackParser(Lexer input) {
    super(input);
  }

  public void stat() {
    if (speculate_stat_alt1()) {
      list();
      match(Lexer.EOF_TYPE);
    } else if (speculate_stat_alt2()) {
      assign();
      match(Lexer.EOF_TYPE);
    }
  }

  public void list() {
    match(LookaheadLexer.LBRACK);
    elements();
    match(LookaheadLexer.RBRACK);
  }

  public void assign() {
    list();
    match(LookaheadLexer.EQUALS);
    list();
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

  public boolean speculate_stat_alt1() {
    boolean success = true;
    mark();
    try {
      list();
      match(Lexer.EOF_TYPE);
    }catch(RecognitionException e) {
      success = false;
    }
    release();
    return success;
  }

  public boolean speculate_stat_alt2() {
    boolean success = true;
    mark();
    try {
      assign();
      match(Lexer.EOF_TYPE);
    }catch(RecognitionException e) {
      success = false;
    }
    release();
    return success;
  }
}
