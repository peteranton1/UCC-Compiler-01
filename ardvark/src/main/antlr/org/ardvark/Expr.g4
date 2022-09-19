/*
  see: https://github.com/antlr/grammars-v4/blob/master/html/examples/antlr.html
*/

grammar Expr;
prog:	(expr NEWLINE)* ;
expr:	expr ('*'|'/') expr
    |	expr ('+'|'-') expr
    |	INT
    |	'(' expr ')'
    ;
NEWLINE : [\r\n]+ ;
INT     : [0-9]+ ;