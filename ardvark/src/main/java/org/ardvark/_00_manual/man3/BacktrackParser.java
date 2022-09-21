package org.ardvark._00_manual.man3;

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
    match(BacktrackLexer.LBRACK);
    elements();
    match(BacktrackLexer.RBRACK);
  }

  public void assign() {
    list();
    match(BacktrackLexer.EQUALS);
    list();
  }

  public void elements() {
    element();
    while (LA(1) == BacktrackLexer.COMMA) {
      match(BacktrackLexer.COMMA);
      element();
    }
  }

  public void element() {
    if (LA(1) == BacktrackLexer.NAME &&
        LA(2) == BacktrackLexer.EQUALS) {
      match(BacktrackLexer.NAME);
      match(BacktrackLexer.EQUALS);
      match(BacktrackLexer.NUMBER);
    }
    else if (LA(1) == BacktrackLexer.NAME)
      match(BacktrackLexer.NAME);
    else if (LA(1) == BacktrackLexer.NUMBER)
      match(BacktrackLexer.NUMBER);
    else if (LA(1) == BacktrackLexer.LBRACK)
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
