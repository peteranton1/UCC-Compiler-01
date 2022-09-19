/*
  see: https://github.com/antlr/grammars-v4/blob/master/csv/CSV.g4
*/
grammar CSV;

csvFile: hdr row+ EOF ;

hdr : row ;

row : field (',' field)* '\r'? '\n' ;

field
    : TEXT
    | STRING
    |
    ;

TEXT   : ~[,\n\r"]+ ;
STRING : '"' ('""'|~'"')* '"' ; // quote-quote is an escaped quote