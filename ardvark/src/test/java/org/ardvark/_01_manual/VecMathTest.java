package org.ardvark._01_manual;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.ardvark.VecMathLexer;
import org.ardvark.VecMathParser;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class VecMathTest extends TestBase {

  @Test
  void shouldGraphicsParserInputOk() throws IOException {
    Path path = Paths.get(BASE_PATH, "vecmath-testdata.txt");
    CharStream input = CharStreams.fromPath(path);
    VecMathLexer lexer = new VecMathLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    VecMathParser parser = new VecMathParser(tokens);

    VecMathParser.StatlistContext result = parser.statlist();
    // Unless there is an error strategy,
    // will always be successful.
    assertNotNull(result);
    assertNull(result.exception);
  }
}
