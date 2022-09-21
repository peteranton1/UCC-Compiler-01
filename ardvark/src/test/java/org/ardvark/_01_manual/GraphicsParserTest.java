package org.ardvark._01_manual;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.ardvark.GraphicsLexer;
import org.ardvark.GraphicsParser;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class GraphicsParserTest extends TestBase {

  @Test
  void shouldGraphicsParserInputOk() throws IOException {
    Path path = Paths.get(BASE_PATH, "graphics-testdata.txt");
    CharStream input = CharStreams.fromPath(path);
    GraphicsLexer lexer = new GraphicsLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    GraphicsParser parser = new GraphicsParser(tokens);

    GraphicsParser.FileContext result = parser.file();
    // Unless there is an error strategy,
    // will always be successful.
    assertNotNull(result);
    assertNull(result.exception);
  }
}
