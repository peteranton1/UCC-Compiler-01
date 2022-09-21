package org.ardvark._00_manual.man2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LookaheadParserTest {

  @Test
  void shouldParseInputOk1() {
    final String input = "[a, 123, bread=44]";
    LookaheadLexer lexer = new LookaheadLexer(input);
    LookaheadParser parser = new LookaheadParser(lexer, 2);
    assertDoesNotThrow(parser::list);
  }

  @Test
  void shouldParseInputOk2() {
    final String input = "[a, b=123, [bread,44]]";
    LookaheadLexer lexer = new LookaheadLexer(input);
    LookaheadParser parser = new LookaheadParser(lexer, 2);
    assertDoesNotThrow(parser::list);
  }
}