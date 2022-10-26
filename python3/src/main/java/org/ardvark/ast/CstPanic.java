package org.ardvark.ast;

import org.antlr.v4.runtime.tree.ParseTree;

public class CstPanic {

  public AstNode panic(ParseTree tree, StringBuilder errBuf) {
    return panic(tree.toStringTree(), errBuf);
  }

  public AstNode panic(String tree, StringBuilder errBuf) {
    String msg0 = String.format("%s : %s",
        errBuf, tree);
    throw new AstRecogniserException(msg0);
  }

}
