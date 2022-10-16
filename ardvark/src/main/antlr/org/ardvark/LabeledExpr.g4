// Rename to distinguish from original
grammar LabeledExpr;

/** The start rule; begin parsing here. */
prog:   stat+ ;

stat:   printExpr
    |   assign
    |   blank
    ;

printExpr:  expr newline ;
assign:     id eq expr newline ;
blank:      newline ;

expr:    product (opAS product)* ;
product: term (opMD term)* ;
term:    number
     |   id
     |   parens
     ;

newline:    NEWLINE ;
opMD:       (MUL|DIV) ;
opAS:       (ADD|SUB) ;
number:     INT ('.' INT)? ;
id:         ID ;
eq:         EQ ;
parens:     LPAREN expr RPAREN ;

ID :    [a-zA-Z]+ ;

// match identifiers
INT :   [0-9]+ ;


// assigns token name to '*' used above in grammar
MUL :       '*' ;
DIV :       '/' ;
ADD :       '+' ;
SUB :       '-' ;
LPAREN :    '(' ;
RPAREN :    ')' ;
EQ :        '=' ;


// match integers
NEWLINE:    '\r'? '\n' ;

// return newlines to parser (end-statement signal)
WS :    [ \t]+ -> skip ; // toss out whitespace


