package org.ardvark._00_manual.man3;

import java.util.ArrayList;
import java.util.List;

public class Parser {

  protected Lexer input;
  protected List<Integer> markers;
  protected List<Token> lookahead;
  protected int p = 0;

  public Parser(Lexer input) {
    this.input = input;
    this.markers = new ArrayList<>();
    this.lookahead = new ArrayList<>();
  }

  public Token LT(int i) {
    sync(i);
    return lookahead.get(p+i-1);
  }

  public int LA(int i) {
    return LT(i).type;
  }

  public void match(int type) {
    if (LA(1) == type) consume();
    else throw new MismatchedTokenException(
        String.format("expecting %s; found %s",
        input.getTokenName(type), LA(1)));
  }

  private void consume() {
    p++;
    // have we hit end of buffer when not backtracking
    if (p == lookahead.size() && !isSpeculating()) {
      p = 0;
      lookahead.clear();
    }
    sync(1);
  }

  public int mark() {
    markers.add(p);
    return p;
  }
  public void release() {
    int marker = markers.get(markers.size()-1);
    markers.remove(markers.size()-1);
    seek(marker);
  }

  public void seek(int index) {
    p = index;
  }

  public boolean isSpeculating() {
    return markers.size() > 0;
  }

  public void sync(int i) {
    if(p+i-1 > lookahead.size()-1) {
      int n = (p+i-1) - (lookahead.size() -1);
      fill(n);
    }
  }

  public void fill(int n){
    for (int i=1; i<=n; i++){
      lookahead.add(input.nextToken());
    }
  }

}
