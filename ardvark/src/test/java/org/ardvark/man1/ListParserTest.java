package org.ardvark.man1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ListParserTest {

  @Test
  void shouldParseInputOk() {
    final String input = "[a, 123, bread]";
    ListLexer lexer = new ListLexer(input);
    ListParser parser = new ListParser(lexer);
    assertDoesNotThrow(parser::list);
  }
}