package org.ardvark._01_manual;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.ardvark.VecMathASTLexer;
import org.ardvark.VecMathASTParser;
import org.ardvark.VecMathLexer;
import org.ardvark.VecMathParser;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class VecMathASTTest extends TestBase {

  @Test
  void shouldParserVisitorInputOk() throws IOException {
    Path path = Paths.get(BASE_PATH, "vecmath-testdata.txt");
    CharStream input = CharStreams.fromPath(path);
    VecMathASTLexer lexer = new VecMathASTLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    VecMathASTParser parser = new VecMathASTParser(tokens);

    VecMathASTParser.StatlistContext result = parser.statlist();
    assertNotNull(result);
    assertNull(result.exception);

    VecMathASTPrintVisitor visitor = new VecMathASTPrintVisitor();
    visitor.visit(result);
  }
}
