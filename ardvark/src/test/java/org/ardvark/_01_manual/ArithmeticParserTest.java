package org.ardvark._01_manual;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.ardvark.ArithmeticLexer;
import org.ardvark.ArithmeticParser;
import org.ardvark.ExprLexer;
import org.ardvark.ExprParser;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class ArithmeticParserTest extends TestBase {

  @Test
  void shouldParserInputOk() throws IOException {
    Path path = Paths.get(BASE_PATH, "arithmetic-testdata.txt");
    CharStream input = CharStreams.fromPath(path);
    ArithmeticLexer lexer = new ArithmeticLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    ArithmeticParser parser = new ArithmeticParser(tokens);

    ArithmeticParser.File_Context result = parser.file_();
    assertNotNull(result);
    assertNull(result.exception);

    ArithmeticPrintVisitor visitor = new ArithmeticPrintVisitor();
    String actual = visitor.visit(result);
    assertNull(actual);
  }
}
