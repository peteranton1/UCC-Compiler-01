package org.ardvark._00_manual.man3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BacktrackParserTest {

  @Test
  void shouldParseInputOk1() {
    final String input = "[a, b=123, [bread,44]]";
    BacktrackLexer lexer = new BacktrackLexer(input);
    BacktrackParser parser = new BacktrackParser(lexer);
    assertDoesNotThrow(parser::list);
  }

  @Test
  void shouldParseInputOk2() {
    final String input = "[a, b]=[bread,44]";
    BacktrackLexer lexer = new BacktrackLexer(input);
    BacktrackParser parser = new BacktrackParser(lexer);
    assertDoesNotThrow(parser::list);
  }
}