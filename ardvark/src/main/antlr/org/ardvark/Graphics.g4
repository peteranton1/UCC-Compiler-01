//
// Graphics language chap4.3
//
// parses following input
// --------------------------
// line from 0.0 to 0.10
// line from 0.10 to 10.10
// line from 10.10 to 10.0
// line from 10.0 to 0.0
// --------------------------
grammar Graphics;

file : command+ ;

command : 'line' 'from' point 'to' point ;

point : INT ',' INT ;

INT : '0'..'9'+ ;

WS : (' ' | '\t' | '\r' | '\n' ) { skip(); } ;

