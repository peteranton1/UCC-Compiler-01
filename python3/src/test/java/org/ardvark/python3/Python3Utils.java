package org.ardvark.python3;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.ardvark.Python3BaseListener;
import org.ardvark.Python3Parser;

public class Python3Utils {
  /**
   * An example how to use a listener that lists all method names from
   * a Python source string.
   *
   * @param source
   *         the Python source to list the methods from.
   */
  public void listMethodNames(String source) {

    Python3Parser parser = new Builder.Parser(source).build();

    ParseTreeWalker.DEFAULT.walk(new Python3BaseListener() {

      // The grammar rule for a function definition looks like this:
      //
      //      funcdef
      //       : DEF NAME parameters ( '->' test )? ':' suite
      //       ;
      //
      @Override
      public void enterFuncdef(@NotNull Python3Parser.FuncdefContext ctx) {
        System.out.println("NAME=" + ctx.NAME().getText());
      }

    }, parser.file_input());
  }

  public String treeString(String source) {
    return new Builder.Tree(source).toStringASCII();
  }
}
