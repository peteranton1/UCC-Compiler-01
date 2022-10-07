package org.ardvark._02_definitive.starter;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.ardvark.LabeledExprLexer;
import org.ardvark.LabeledExprParser;

import java.io.InputStream;

public class LabeledExprCalc {
  public static void main(String[] args) throws Exception {
    String inputFile = "_01_testdata/expr2.a.txt";
    if (args.length > 0) inputFile = args[0];
    InputStream is = System.in;
    if (inputFile != null) is =
        new FileUtil().fromResource(inputFile);
    CharStream input = CharStreams.fromStream(is);

    LabeledExprLexer lexer = new LabeledExprLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    LabeledExprParser parser = new LabeledExprParser(tokens);
    ParseTree tree = parser.prog(); // parse; start at prog

    LabeledExprEvalVisitor eval = new LabeledExprEvalVisitor();
    eval.visit(tree);
  }
  /*
  (prog
    (stat (expr 193) \n)
    (stat a = (expr 5) \n)
    (stat b = (expr 6) \n)
    (stat (expr (expr a) + (expr (expr b) * (expr 2))) \n)
    (stat (expr (expr ( (expr (expr 1) + (expr 2)) )) * (expr 3)) \n))
   */
}