// note "lexer grammar"
lexer grammar CommonLexerRules;

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

