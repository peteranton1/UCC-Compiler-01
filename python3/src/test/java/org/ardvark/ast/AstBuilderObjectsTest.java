package org.ardvark.ast;

import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
class AstBuilderObjectsTest extends AstTestBase  {

  @Test
  void assignNameToDictOrSetWith1Set() {
    String input = """
      survey = {
        "yes": { "Y": "Yes" }
        }
      """;
    ParseTree tree = parseSource(input);
    //printTree(input);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           '- =
              |- survey
              '- DictOrSet
                 '- :
                    |- "yes"
                    '- DictOrSet
                       '- :
                          |- "Y"
                          '- "Yes"
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }

  @Test
  void assignNameToDictOrSetWith2Sets() {
    String input = """
      survey = {
        "yes": { "Y": "Yes" },
        "no": { "N": "No" }
        }
      """;
    ParseTree tree = parseSource(input);
    //printTree(input);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           '- =
              |- survey
              '- DictOrSet
                 |- :
                 |  |- "yes"
                 |  '- DictOrSet
                 |     '- :
                 |        |- "Y"
                 |        '- "Yes"
                 '- :
                    |- "no"
                    '- DictOrSet
                       '- :
                          |- "N"
                          '- "No"
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }

  @Test
  void assignNameToDictOrSetWith3Sets() {
    String input = """
      survey = {
        "yes": { "Y": "Yes" },
        "no": 1 + 2,
        maybe : \"\"\"
        Hello World
        \"\"\"
        }
      """;
    ParseTree tree = parseSource(input);
    //printTree(input);
    AstNode actualNode = new AstBuilder().toAST(tree);
    String expected = """
        '- {}
           '- =
              |- survey
              '- DictOrSet
                 |- :
                 |  |- "yes"
                 |  '- DictOrSet
                 |     '- :
                 |        |- "Y"
                 |        '- "Yes"
                 |- :
                 |  |- "no"
                 |  '- +
                 |     |- 1
                 |     '- 2
                 '- :
                    |- maybe
                    '- ""\"\\n  Hello World\\n  ""\"
        """;
    String actual = nodeToString(actualNode);
    assertEquals(expected, actual);
  }


}