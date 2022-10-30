package org.ardvark.ast;

import lombok.Getter;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.ArrayList;
import java.util.List;

import static org.ardvark.ast.NodeType.*;

@Getter
public class PythonCSTDefParser {

  private final AstBuilderVisitor visitor;
  private final PythonCSTBaseParser baseParser;

  public PythonCSTDefParser(AstBuilderVisitor visitor) {
    this.visitor = visitor;
    this.baseParser = visitor.baseParser;
  }

  /*
    /// funcdef: 'def' NAME parameters ['->' test] ':' suite
     */
  public AstNode visitFuncdef(ParserRuleContext ctx) {
    return parseFuncdef(ctx, baseParser.errBuf("Funcdef"));
  }

  /*
  /// parameters: '(' [typedargslist] ')'
   */
  public AstNode visitParameters(ParserRuleContext ctx) {
    return parseParameters(ctx, baseParser.errBuf("Parameters"));
  }

  /*
  /// typedargslist: (tfpdef ['=' test] (',' tfpdef ['=' test])* [','
  ///                ['*' [tfpdef] (',' tfpdef ['=' test])* [',' '**' tfpdef] | '**' tfpdef]]
  ///              |  '*' [tfpdef] (',' tfpdef ['=' test])* [',' '**' tfpdef] | '**' tfpdef)
   */
  public AstNode visitTypedargslist(ParserRuleContext ctx) {
    return parseTypedargslist(ctx, baseParser.errBuf("Typedargslist"));
  }

  /*
  /// tfpdef: NAME [':' test]
   */
  public AstNode visitTfpdef(ParserRuleContext ctx) {
    return parseTfpdef(ctx, baseParser.errBuf("Tfpdef"));
  }

  public AstNode parseTfpdef(ParserRuleContext ctx,
                             StringBuilder errBuf) {
    errBuf.append("parseTfpdef \n");

    List<AstNode> children = new ArrayList<>();
    if(ctx.getChildCount()>0) {
      children.add(baseParser.parseName(ctx.getChild(0), errBuf));
    }
    AstNode aggNode = visitor.visitChildren(ctx);
    if (aggNode != null && AGG.equals(aggNode.getNodeType())) {
      children.addAll(aggNode.getChildren());
    } else if (aggNode != null) {
      children.add(aggNode);
    }
    return AstNode.builder()
        .nodeType(PARAM)
        .text(PARAM.getText())
        .children(children)
        .build();
  }

  public AstNode parseTypedargslist(ParserRuleContext ctx,
                                    StringBuilder errBuf) {
    errBuf.append("parseTypedargslist \n");
    return visitor.visitChildren(ctx);
  }

  public AstNode parseParameters(ParserRuleContext ctx,
                              StringBuilder errBuf) {
    errBuf.append("parseParameters \n");

    AstNode aggNode = visitor.visitChildren(ctx);
    List<AstNode> children = new ArrayList<>();
    if (aggNode != null && AGG.equals(aggNode.getNodeType())) {
      children.addAll(aggNode.getChildren());
    } else if (aggNode != null) {
      children.add(aggNode);
    }
    return AstNode.builder()
        .nodeType(PARAMS)
        .text(PARAMS.getText())
        .children(children)
        .build();
  }

  public AstNode parseFuncdef(ParserRuleContext ctx,
                              StringBuilder errBuf) {
    errBuf.append("parseFuncdef \n");

    AstNode aggNode = visitor.visitChildren(ctx);
    List<AstNode> children = new ArrayList<>();
    if (aggNode != null && AGG.equals(aggNode.getNodeType())) {
      children.addAll(aggNode.getChildren());
    } else if (aggNode != null) {
      children.add(aggNode);
    }
    return AstNode.builder()
        .nodeType(FUNCDEF)
        .text(FUNCDEF.getText())
        .children(children)
        .build();
  }
}
