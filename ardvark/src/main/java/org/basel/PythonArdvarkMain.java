package org.basel;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.ardvark.PythonArdvarkLexer;
import org.ardvark.PythonArdvarkParser;
import org.ardvark._02_definitive.starter.FileUtil;

import java.io.InputStream;

public class PythonArdvarkMain {
  public static void main(String[] args) throws Exception {
    String inputFile = "python-ardvark/J123456.py";
    if (args.length > 0) inputFile = args[0];
    InputStream is = System.in;
    if (inputFile != null) is =
        new FileUtil().fromResource(inputFile);
    CharStream input = CharStreams.fromStream(is);

    PythonArdvarkLexer lexer = new PythonArdvarkLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    PythonArdvarkParser parser = new PythonArdvarkParser(tokens);
    ParseTree tree = parser.file_input(); // parse; start at root

    System.out.println(tree.toStringTree(parser));
    PythonArdvarkPrintVisitor eval = new PythonArdvarkPrintVisitor();
    eval.visit(tree);
  }
}
