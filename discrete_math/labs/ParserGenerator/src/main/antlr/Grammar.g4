grammar Grammar;
@header {package main.antlr;}

grammarSpec: GRAMMAR ID SEMI HEADER? MEMBERS? ruleSpec+ EOF;
MEMBERS: '@members{' .*? '}' WS;
HEADER: '@header{' .*? '}' WS;
ruleSpec: ID returnsSpec? COLON body (OR body)* SEMI;
returnsSpec: RETURNS LSQ oneReturn ( COMMA oneReturn )* RSQ;
oneReturn: type=ID name=ID;
body: literal block?| ID+ block?| block?;
literal: literal (PLUS|AST|QUE)+ | literal OR literal | LPAREN literal RPAREN | APO str APO | APO strChar APO | QUOTEDWS | APO ANYCHAR APO DOT DOT APO ANYCHAR APO;
block: ACTION;
str: ~('\r' | '\n' | '"' | '\'') (~('\r' | '\n' | '"' | '\''))+?;
strChar: (~('\r' | '\n' | '"' | '\'')|':');


QUOTEDWS: '\' \'';
ACTION:	'{'(ACTION|'/*'.*? '*/'|'//' ~[\r\n]*|.)*?('}'|EOF);
GRAMMAR: 'grammar';
COLON: ':';
OR: '|';
SEMI: ';';
APO: '\'';
DOT: '.';
LCU: '{';
RCU: '}';


RPAREN: ')';
LPAREN: '(';
PLUS: '+';
AST: '*';
QUE: '?';
LSQ: '[';
RSQ: ']';
RETURNS: 'returns';
ASG: '=';
COMMA: ',';
ID: ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'0'..'9'|'_'|'?')*;
RE: '\r' -> skip;
BR: '\n' -> skip;
WS: [ \r\n\t] -> skip;
ANYCHAR: .;


