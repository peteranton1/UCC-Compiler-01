package org.ardvark.man1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ListLexerTest {

  @Test
  public void shouldLexInputOk() {
    final String input = "[a, 123, bread]";
    ListLexer lexer = new ListLexer(input);
    Token t = lexer.nextToken();
    while(t.type != Lexer.EOF_TYPE){
      System.out.println(t);
      t = lexer.nextToken();
    }
    String expected = "<'<EOF>', <EOF>>";
    String actual = t.toString();
    System.out.println(actual);
    Assertions.assertEquals(expected, actual);
  }
}