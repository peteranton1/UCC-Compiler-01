/*
    see: https://github.com/antlr/grammars-v4/blob/master/arithmetic/Arithmetic.g4
*/

grammar Arithmetic;

file_ : (equation NEWLINE)* ;

equation
   : expression
   | assignment
   ;

assignment
   : expression relop expression
   ;

expression
   :  expression  pow expression
   |  expression  muldiv  expression
   |  expression  plusminus expression
   |  lparen expression rparen
   |  unary atom
   ;

pow
   : POW
   ;

muldiv
   : (TIMES | DIV)
   ;

plusminus
   : (PLUS | MINUS)
   ;

unary
   : (PLUS | MINUS)?
   ;

lparen
   : LPAREN
   ;

rparen
   : RPAREN
   ;

atom
   : scientific
   | variable
   ;

scientific
   : SCIENTIFIC_NUMBER
   ;

variable
   : VARIABLE
   ;

relop
   : EQ
   | GT
   | LT
   ;


VARIABLE
   : VALID_ID_START VALID_ID_CHAR*
   ;


fragment VALID_ID_START
   : ('a' .. 'z') | ('A' .. 'Z') | '_'
   ;


fragment VALID_ID_CHAR
   : VALID_ID_START | ('0' .. '9')
   ;

//The NUMBER part gets its potential sign from "(PLUS | MINUS)* atom" in the expression rule
SCIENTIFIC_NUMBER
   : NUMBER (E SIGN? UNSIGNED_INTEGER)?
   ;

fragment NUMBER
   : ('0' .. '9') + ('.' ('0' .. '9') +)?
   ;

fragment UNSIGNED_INTEGER
   : ('0' .. '9')+
   ;


fragment E
   : 'E' | 'e'
   ;


fragment SIGN
   : ('+' | '-')
   ;


LPAREN
   : '('
   ;


RPAREN
   : ')'
   ;


PLUS
   : '+'
   ;


MINUS
   : '-'
   ;


TIMES
   : '*'
   ;


DIV
   : '/'
   ;


GT
   : '>'
   ;


LT
   : '<'
   ;


EQ
   : '='
   ;


POINT
   : '.'
   ;


POW
   : '^'
   ;

NEWLINE
   : [\r\n]+
   ;

WS
   : [ \t] + -> skip
   ;
