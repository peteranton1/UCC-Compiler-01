package org.ardvark._00_manual.man2;

public class Parser {

  protected Lexer input;
  protected Token[] lookahead;
  protected int k;
  protected int p = 0;

  public Parser(Lexer input, int k) {
    this.input = input;
    this.k = k;
    this.lookahead = new Token[k];
    for (int i=1;i<= k; i++) consume();
  }

  public Token LT(int i) {
    return lookahead[(p+i-1) % k];
  }

  public int LA(int i) {
    return LT(i).type;
  }

  public void match(int type) {
    if (LA(1) == type) consume();
    else throw new Error(String.format("expecting %s; found %s",
        input.getTokenName(type), LA(1)));
  }

  public void consume(){
    lookahead[p] = input.nextToken();
    p = (p+1) % k;
  }
}
