package org.ardvark.python3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClassDef2Test {

  @Test
  public void shouldClassDefWhenValid() {
    Python3Utils utils = new Python3Utils();
    String source = """
        class foo(bar.baz):
        \tpass

         \s
        class foo(bar.baz):
        \tpass

         \s
        """;
    String actual = utils.treeString(source);
    String expected = expectedAssignmentWhenValid();
    assertEquals(expected, actual);
  }

  @Test
  public void shouldClassDefWhenInvalid() {
    Python3Utils utils = new Python3Utils();
    String source = """
        class foo(bar.baz):
        \tpass

         \s
        class foo(bar.baz):
        pass

         \s
        """;
    assertThrows(RuntimeException.class, () -> utils.treeString(source));
  }

  private String expectedAssignmentWhenValid() {
    return """
'- file_input
   |- stmt
   |  '- compound_stmt
   |     '- classdef
   |        |- class
   |        |- foo
   |        |- (
   |        |- arglist
   |        |  '- argument
   |        |     '- test
   |        |        '- or_test
   |        |           '- and_test
   |        |              '- not_test
   |        |                 '- comparison
   |        |                    '- star_expr
   |        |                       '- expr
   |        |                          '- xor_expr
   |        |                             '- and_expr
   |        |                                '- shift_expr
   |        |                                   '- arith_expr
   |        |                                      '- term
   |        |                                         '- factor
   |        |                                            '- power
   |        |                                               |- atom
   |        |                                               |  '- bar
   |        |                                               '- trailer
   |        |                                                  |- .
   |        |                                                  '- baz
   |        |- )
   |        |- :
   |        '- suite
   |           |- <NEWLINE>
   |           |- <INDENT>
   |           |- stmt
   |           |  '- simple_stmt
   |           |     |- small_stmt
   |           |     |  '- pass_stmt
   |           |     |     '- pass
   |           |     '- <NEWLINE>
   |           '- <DEDENT>
   |- stmt
   |  '- compound_stmt
   |     '- classdef
   |        |- class
   |        |- foo
   |        |- (
   |        |- arglist
   |        |  '- argument
   |        |     '- test
   |        |        '- or_test
   |        |           '- and_test
   |        |              '- not_test
   |        |                 '- comparison
   |        |                    '- star_expr
   |        |                       '- expr
   |        |                          '- xor_expr
   |        |                             '- and_expr
   |        |                                '- shift_expr
   |        |                                   '- arith_expr
   |        |                                      '- term
   |        |                                         '- factor
   |        |                                            '- power
   |        |                                               |- atom
   |        |                                               |  '- bar
   |        |                                               '- trailer
   |        |                                                  |- .
   |        |                                                  '- baz
   |        |- )
   |        |- :
   |        '- suite
   |           |- <NEWLINE>
   |           |- <INDENT>
   |           |- stmt
   |           |  '- simple_stmt
   |           |     |- small_stmt
   |           |     |  '- pass_stmt
   |           |     |     '- pass
   |           |     '- <NEWLINE>
   |           '- <DEDENT>
   '- <EOF>
""";
  }
}
