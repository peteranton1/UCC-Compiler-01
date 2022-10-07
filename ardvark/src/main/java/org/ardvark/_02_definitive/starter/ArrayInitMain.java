package org.ardvark._02_definitive.starter;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.ardvark.ArrayInitLexer;
import org.ardvark.ArrayInitParser;

public class ArrayInitMain {
  public static void main(String[] args) {

    // create a CharStream that reads
    // from standard input
    String inputStr = "{1,{2,3},4}";
    CharStream input =
        CharStreams.fromString(inputStr);

    // create a lexer that feeds off
    // of input CharStream
    ArrayInitLexer lexer =
        new ArrayInitLexer(input);

    // create a buffer of tokens pulled
    // from the lexer
    CommonTokenStream tokens =
        new CommonTokenStream(lexer);

    /*
     create a parser that feeds off
     the tokens buffer
    */
    ArrayInitParser parser =
        new ArrayInitParser(tokens);

    // begin parsing at init rule
    ParseTree tree = parser.init();

    // print LISP-style tree
    printLispTree(parser, tree);

    printUsingWalker(tree);
  }

  private static void printUsingWalker(ParseTree tree) {
    // Create a generic parse tree walker
    // that can trigger callbacks
    ParseTreeWalker walker = new ParseTreeWalker();
    // Walk the tree created during the
    // parse, trigger callbacks
    walker.walk(new ShortToUnicodeString(), tree);
    // print a \n after translation
    System.out.println();
  }

  private static void printLispTree(
      ArrayInitParser parser, ParseTree tree) {
    /*
    Given input: {1,{2,3},4}

    Produced output:

    (init {
      (value 1) ,
      (value (init {
        (value 2) ,
        (value 3) })) ,
      (value 4) })
     */
    System.out.println(
        tree.toStringTree(parser));
  }
}