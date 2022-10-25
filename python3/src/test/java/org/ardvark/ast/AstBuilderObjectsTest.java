package org.ardvark.ast;

import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
class AstBuilderObjectsTest extends AstTestBase  {

  private static final String input = """
      survey = {
        "yes": { "Y": "Yes" }
        }
      """;

  @Test
  void assignStringAsAstNode() {
    ParseTree tree = parseSource(input);
    //printTree(input);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- Root
           '- =
              |- survey
              '- :
                 |- {
                 '- }
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }


}