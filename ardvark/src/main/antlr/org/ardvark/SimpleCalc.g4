grammar SimpleCalc;
//tokens { A, B, C }
//a : X ;
/*
 * Parser Rules
 */
simplecalc  : NUMBER '+' NUMBER ;
/*
 * Lexer Rules
 */
NUMBER     : [0-9]+ ;
WHITESPACE : ' ' -> skip ;