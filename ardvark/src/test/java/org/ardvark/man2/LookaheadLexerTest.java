package org.ardvark.man2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LookaheadLexerTest {
  @Test
  public void shouldLexInputOk() {
    final String input = "[a, b = 123, [br,ead]] ";
    LookaheadLexer lexer = new LookaheadLexer(input);
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
