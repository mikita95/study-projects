grammar GoodLanguage;
@header{import java.util.*;}
@members{ }

program:    statement program
    |       ;

statement:  assignment sem
    |       read sem
    |       write sem
    |       variables sem;

sem:        SEMICOLON sem
    |       ;

assignment: lvalueblock ASSIGN rvalueblock;

variables:  VAR SEP assignment;

lvalueblock:lvalue morelv;

morelv:     COMMA lvalue morelv
    |       ;
lvalue: NAME ;
rvalue: atom contatom;
contatom: o atom | ;
o: PLUS | MINUS | MULT;
atom: NUMBER | LPAREN rvalue RPAREN;
rvalueblock: rvalue morerv;
morerv: COMMA rvalue morerv | ;
read: RD lvalueblock ;
write: WR writable ;
writable: rvalue | DAPO LINE DAPO ;
LINE: ('b'|'c'|'d'|'e'|'f'|'g'|'h'|'i'|'j'|'k'|'l'|'m'|'n'|'o'|'p'|'q'|'s'|'t'|'u'|'w'|'x'|'y'|'z'|'A'|'B'|'C'|'D'|'E'|'F'|'G'|'H'|'I'|'J'|'K'|'L'|'M'|'N'|'O'|'P'|'Q'|'R'|'S'|'T'|'U'|'V'|'W'|'X'|'Y'|'Z')+ ;

DAPO: '^';
SEP: '$';
LCURLY: '{' ;
RCURLY: '}' ;
RD: '>>' ;
WR: '<<' ;
VAR: 'var' ;
PLUS: '+' ;
MINUS: '-' ;
MULT: '*' ;
NUMBER: ('0'..'9')+ ;
LPAREN: '(' ;
RPAREN: ')' ;
NAME: ('a'|'b'|'c'|'d'|'e'|'f'|'g'|'h'|'i'|'j'|'k'|'l'|'m'|'n'|'o'|'p'|'q'|'r'|'s'|'t'|'u'|'v'|'w'|'x'|'y'|'z')+ ;
COMMA: ',' ;
ASSIGN: '=' ;
SEMICOLON: ';' ;