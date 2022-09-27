grammar Grammar;

@header{
  import meta.grammar.*;
}

result returns [Grammar gram]
@init {$gram = new Grammar();}
: (term[$gram] SEMI)+ EOF;

term[Grammar gram] : EXCL terminal[$gram] | rules[$gram];

terminal[Grammar gram] : name EQ regexp {$gram.addTerminal(new Terminal($name.text, $regexp.text));};

rules[Grammar gram]
@init {Rule r = new Rule();}
: inheritedAttr name synthesizedAttr
 {r.setName(new NonTerminal($inheritedAttr.text, $name.text, $synthesizedAttr.text));} ARROW
     transition {r.addTransition($transition.trans);}
      (PIPE transition {r.addTransition($transition.trans);})*
      {$gram.addRule(r);};

transition returns [MyTransition trans]
@init {$trans = new MyTransition();}
: (subTransition {$trans.addSubTransition($subTransition.sub);})+
| EPS code? {$trans.addCode($code.text);};

subTransition returns [SubTransition sub]
:(name inheritedAttr? {$sub = new SubTransition($name.text, $inheritedAttr.text);})
code? {$sub.addCode($code.text);};

inheritedAttr : attr;
synthesizedAttr : attr;
attr : OPENB .*? CLOSEB;

name: NAME | TOKEN_NAME;
regexp: OPENB REGEXP CLOSEB;
code : CODE;

SEMI : ';';
EXCL : '!';
EQ : '=';
OPENB : '[';
CLOSEB : ']';
ARROW : '->';
PIPE : '|';
EPS : 'ε';
CODE :  '{' (~('{'|'}')+ CODE?)* '}';

TOKEN_NAME: [A-Z]+;
NAME: [a-zA-Z0-9\\.]+;
REGEXP: '\''(~['])+'\'';

WS : [ \t\r\n]+ -> skip;

