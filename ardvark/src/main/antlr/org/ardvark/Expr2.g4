grammar Expr2;

/**
 * The start rule; begin parsing here.
 */
prog:   stat+ ;

stat:   expr NEWLINE
    |   ID '=' expr NEWLINE
    |   NEWLINE
    ;

expr:   expr ('*'|'/') expr
    |   expr ('+'|'-') expr
    |   INT
    |   ID
    |   '(' expr ')'
    ;

// match identifiers
ID  :   [a-zA-Z]+ ;

// match integers
INT :   [0-9]+ ;


// return newlines to parser
// (is end-statement signal)
NEWLINE:    '\r'? '\n' ;

// toss out whitespace
WS  :   [ \t]+ -> skip ;
