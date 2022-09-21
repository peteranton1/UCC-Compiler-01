/*
    Vector Math file
    ----------------

    See:
     resources/_01_testdata/vecmath-testdata.txt
*/
grammar VecMath;

statlist : stat+ ;

stat : ID '=' expr
     | 'print' expr
     ;

expr : multExpr ('+' multExpr)* ;

multExpr : primary (('*'|'.') primary )* ;

primary
    : INT
    | ID
    | '[' expr (',' expr)* ']'
    ;

INT : '0'..'9'+ ;

ID : (('a'..'z')|('A'..'Z'))+ ;

WS : (' ' | '\t' | '\r' | '\n' ) { skip(); } ;
