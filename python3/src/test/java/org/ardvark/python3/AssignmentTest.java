package org.ardvark.python3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AssignmentTest {

  @Test
  public void shouldAssignmentWhenValid() {
    Python3Utils utils = new Python3Utils();
    String source = "a = 10\n\n";
    String actual = utils.treeString(source);
    String expected = expectedAssignmentWhenValid();
    assertEquals(expected, actual);
  }

  @Test
  public void shouldAssignmentWhenInvalid() {
    Python3Utils utils = new Python3Utils();
    String source = "a = \n\n";
    assertThrows(RuntimeException.class, () -> utils.treeString(source));
  }

  private String expectedAssignmentWhenValid() {
    return """
'- file_input
   |- stmt
   |  '- simple_stmt
   |     |- small_stmt
   |     |  '- expr_stmt
   |     |     |- testlist_star_expr
   |     |     |  '- test
   |     |     |     '- or_test
   |     |     |        '- and_test
   |     |     |           '- not_test
   |     |     |              '- comparison
   |     |     |                 '- star_expr
   |     |     |                    '- expr
   |     |     |                       '- xor_expr
   |     |     |                          '- and_expr
   |     |     |                             '- shift_expr
   |     |     |                                '- arith_expr
   |     |     |                                   '- term
   |     |     |                                      '- factor
   |     |     |                                         '- power
   |     |     |                                            '- atom
   |     |     |                                               '- a
   |     |     |- =
   |     |     '- testlist_star_expr
   |     |        '- test
   |     |           '- or_test
   |     |              '- and_test
   |     |                 '- not_test
   |     |                    '- comparison
   |     |                       '- star_expr
   |     |                          '- expr
   |     |                             '- xor_expr
   |     |                                '- and_expr
   |     |                                   '- shift_expr
   |     |                                      '- arith_expr
   |     |                                         '- term
   |     |                                            '- factor
   |     |                                               '- power
   |     |                                                  '- atom
   |     |                                                     '- number
   |     |                                                        '- integer
   |     |                                                           '- 10
   |     '- <NEWLINE>
   '- <EOF>
""";
  }
}
