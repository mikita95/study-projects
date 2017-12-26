grammar Arithm;
@header{import java.util.*;}
@members{}

e returns [Integer v]: t ep { v = 0#v + 1#v; };

ep returns [Integer v]: PLUS t ep { v = 1#v + 2#v; } | { v = 0; };

t returns [Integer v]: a tp { v = 0#v - 1#v; };

tp returns [Integer v]: MINUS a tp { v = 1#v - 2#v; }| { v = 0; };

a returns [Integer v]: f ap { v = 0#v * 1#v; };

ap returns [Integer v]: MULT f ap { v = 1#v * 2#v; } | { v = 1; };

f returns [Integer v]: N { v = Integer.parseInt(0#text); }| LBRACE e RBRACE { v = 1#v; };

PLUS: '+';
MINUS: '-';
MULT: '*';
LBRACE: '(';
RBRACE: ')';
N: ('0'..'9')+;