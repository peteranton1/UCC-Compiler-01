package org.ardvark._00_manual.man3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BacktrackLexerTest {

  @Test
  public void shouldLexInputOk() {
    final String input = "[a, b] = [123, bread]";
    BacktrackLexer lexer = new BacktrackLexer(input);
    Token t = lexer.nextToken();
    while(t.type != Lexer.EOF_TYPE){
      System.out.println(t);
      t = lexer.nextToken();
    }
    String expected = "<'<EOF>', <EOF>>";
    String actual = t.toString();
    System.out.println(actual);
    assertEquals(expected, actual);
  }
}