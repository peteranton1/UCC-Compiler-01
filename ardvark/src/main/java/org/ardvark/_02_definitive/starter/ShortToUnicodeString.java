package org.ardvark._02_definitive.starter;

import org.ardvark.ArrayInitBaseListener;
import org.ardvark.ArrayInitParser;

/**
 * Convert short array inits like
 * {1,2,3} to "\u0001\u0002\u0003"
 */
public class ShortToUnicodeString extends ArrayInitBaseListener {

  /**
   * Translate { to "
   */
  @Override
  public void enterInit(ArrayInitParser.InitContext ctx) {
    System.out.print('"');
  }

  /**
   * Translate } to "
   */
  @Override
  public void exitInit(ArrayInitParser.InitContext ctx) {
    System.out.print('"');
  }

  /**
   * Translate integers to 4-digit
   * hexadecimal strings prefixed with \\u
   */
  @Override
  public void enterValue(ArrayInitParser.ValueContext ctx) {
    // Assumes no nested array initializers
    int value = 0;
    if(ctx.INT() != null) {
      value = Integer.parseInt(ctx.INT().getText());
    }
    System.out.printf("\\u%04x", value);
  }
}