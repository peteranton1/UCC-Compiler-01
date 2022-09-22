package org.ardvark._01_manual;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.ardvark.ExprLexer;
import org.ardvark.ExprParser;
import org.ardvark.VecMathLexer;
import org.ardvark.VecMathParser;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ExprParserTest extends TestBase {

  @Test
  void shouldParserInputOk() throws IOException {
    Path path = Paths.get(BASE_PATH, "expr-testdata.txt");
    CharStream input = CharStreams.fromPath(path);
    ExprLexer lexer = new ExprLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    ExprParser parser = new ExprParser(tokens);

    ExprParser.ProgContext result = parser.prog();
    assertNotNull(result);
    assertNull(result.exception);
  }
}
