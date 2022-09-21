package org.ardvark._00_manual.man1;

public class Parser {

  protected Lexer input;
  protected Token lookahead;

  public Parser(Lexer input) {
    this.input = input;
    this.lookahead = input.nextToken();
  }

  public void match(int type) {
    if (lookahead.type == type) consume();
    else throw new Error(String.format("expecting %s found %s",
        input.getTokenName(type), lookahead));
  }

  public void consume(){
    lookahead=input.nextToken();
  }
}
