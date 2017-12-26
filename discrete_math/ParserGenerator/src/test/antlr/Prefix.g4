grammar Prefix;
@header{import java.util.*;}
@members{ Stack<String> stack = new Stack<String>();String result = "";}

start returns [String v]: e { v = stack.pop(); };
e : num SEP eprime;
num: N {stack.push(0#text); };
eprime: c| ;
c:e o SEP eprime;
o:PLUS {String temp = stack.pop();stack.push('(' + stack.pop() + '+' + temp + ')'); }
|MULT {String temp = stack.pop();stack.push('(' + stack.pop() + '*' + temp + ')'); }
|MINUS {String temp = stack.pop();stack.push('(' + stack.pop() + '-' + temp + ')'); };

PLUS: '+';
MULT: '*';
MINUS: '-';
N: ('0'..'9')+;
SEP: ' ';