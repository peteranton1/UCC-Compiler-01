/*
  Simple expression grammar.
*/

grammar Expr;

prog:	(expr NEWLINE)* ;

expr
    : term (('+'|'−') term)*
    ;

term
    : factor (('*'|'/') factor)*
    ;

factor
    : number
    | '(' expr ')'
    | '+' factor
    | '−' factor
    ;

number
    : INT
    | INT '.' INT ;

INT : '0'..'9'+ ;

ID : (('a'..'z')|('A'..'Z'))+ ;

NEWLINE : [\r\n]+ ;

WS : (' ' | '\t' ) { skip(); } ;