package org.ardvark.ast;

import org.antlr.v4.runtime.tree.ParseTree;

public class CstPanic {

  public AstNode panic(ParseTree tree, StringBuilder errBuf) {
    return panic(tree, errBuf.toString());
  }

  public AstNode panic(ParseTree tree, String errBuf) {
    String msg0 = String.format("%s : %s",
        errBuf, tree.toStringTree());
    throw new AstRecogniserException(msg0);
  }

}
