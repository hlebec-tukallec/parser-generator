package arithmetics;
import java.text.ParseException;
import java.util.*;
public class GeneratedParser {
public GeneratedEnum curToken;
public final GeneratedLexer lexer;
public GeneratedParser(String text) {
lexer = new GeneratedLexer(text);
curToken = lexer.next();
}

public static class EAttributes extends Node {
public EAttributes(String name) {
super(name);
}

public int val;
public int acc;


}
public EAttributes e() throws ParseException{
int val = 0;
int acc = 0;

EpAttributes ep = null;
TAttributes t = null;

	EAttributes inner = new EAttributes("e");
t = t();
inner.children.add(t);val = t.val; acc = t.val;

ep = ep(acc);
inner.children.add(ep);val = ep.val; acc = val;



inner.val = val;
inner.acc = acc;

return inner;
}

public static class EpAttributes extends Node {
public EpAttributes(String name) {
super(name);
}

public int val;
public int myAcc;

public int acc;

}
public EpAttributes ep(int acc) throws ParseException{
int val = 0;
int myAcc = 0;

EpAttributes ep = null;
GeneratedEnum ADD = null;
GeneratedEnum SUB = null;
TAttributes t = null;
GeneratedEnum EPS = null;

	EpAttributes inner = new EpAttributes("ep");
switch (curToken.token) {
case ADD -> {
ADD = curToken;
inner.children.add(new Node(curToken.toString()));
curToken = lexer.next();

t = t();
inner.children.add(t);myAcc = acc + t.val;
ep = ep(myAcc);
inner.children.add(ep);val = ep.val;;

}
case SUB -> {
SUB = curToken;
inner.children.add(new Node(curToken.toString()));
curToken = lexer.next();

t = t();
inner.children.add(t);myAcc = acc - t.val;;
ep = ep(myAcc);
inner.children.add(ep);val = ep.val;;

}
case RPAR, END -> {val = acc;}
		default -> throw new ParseException("Unexpected token", lexer.position());
}
inner.acc = acc;

inner.val = val;
inner.myAcc = myAcc;

return inner;
}

public static class TAttributes extends Node {
public TAttributes(String name) {
super(name);
}

public int val;
public int myAcc;


}
public TAttributes t() throws ParseException{
int val = 0;
int myAcc = 0;

SochetAttributes sochet = null;
TpAttributes tp = null;

	TAttributes inner = new TAttributes("t");
sochet = sochet();
inner.children.add(sochet);myAcc = sochet.val;

tp = tp(myAcc);
inner.children.add(tp);val = tp.val;



inner.val = val;
inner.myAcc = myAcc;

return inner;
}

public static class TpAttributes extends Node {
public TpAttributes(String name) {
super(name);
}

public int val;
public int myAcc;

public int acc;

}
public TpAttributes tp(int acc) throws ParseException{
int val = 0;
int myAcc = 0;

GeneratedEnum MUL = null;
GeneratedEnum DIV = null;
SochetAttributes sochet = null;
TpAttributes tp = null;
GeneratedEnum EPS = null;

	TpAttributes inner = new TpAttributes("tp");
switch (curToken.token) {
case MUL -> {
MUL = curToken;
inner.children.add(new Node(curToken.toString()));
curToken = lexer.next();

sochet = sochet();
inner.children.add(sochet);myAcc = acc * sochet.val;;
tp = tp(myAcc);
inner.children.add(tp);val = tp.val;;

}
case DIV -> {
DIV = curToken;
inner.children.add(new Node(curToken.toString()));
curToken = lexer.next();

sochet = sochet();
inner.children.add(sochet);myAcc = acc / sochet.val;;
tp = tp(myAcc);
inner.children.add(tp);val = tp.val;;

}
case ADD, SUB, RPAR, END -> {val = acc;}
		default -> throw new ParseException("Unexpected token", lexer.position());
}
inner.acc = acc;

inner.val = val;
inner.myAcc = myAcc;

return inner;
}

public static class FAttributes extends Node {
public FAttributes(String name) {
super(name);
}

public int val;


}
public FAttributes f() throws ParseException{
int val = 0;

GeneratedEnum NUM = null;
FAttributes f = null;
EAttributes e = null;
GeneratedEnum SUB = null;
GeneratedEnum LPAR = null;
GeneratedEnum RPAR = null;

	FAttributes inner = new FAttributes("f");
switch (curToken.token) {
case LPAR -> {
LPAR = curToken;
inner.children.add(new Node(curToken.toString()));
curToken = lexer.next();

e = e();
inner.children.add(e);val = e.val;;
RPAR = curToken;
if (GeneratedEnum.Token.RPAR != curToken.token) {
throw new ParseException("UNEXPECTED TOKEN(((", lexer.position());
}
curToken = lexer.next();

inner.children.add(new Node("RPAR"));
}
case NUM -> {
NUM = curToken;
inner.children.add(new Node(curToken.toString()));
curToken = lexer.next();
val = Integer.parseInt(NUM.text);;


}
case SUB -> {
SUB = curToken;
inner.children.add(new Node(curToken.toString()));
curToken = lexer.next();

f = f();
inner.children.add(f);val = -f.val;;

}
		default -> throw new ParseException("Unexpected token", lexer.position());
}

inner.val = val;

return inner;
}

public static class SochetAttributes extends Node {
public SochetAttributes(String name) {
super(name);
}

public int val;


}
public SochetAttributes sochet() throws ParseException{
int val = 0;

FAttributes f = null;
HelptocountAttributes helpToCount = null;

	SochetAttributes inner = new SochetAttributes("sochet");
f = f();
inner.children.add(f);
helpToCount = helpToCount(f.val);
inner.children.add(helpToCount);val = helpToCount.val;



inner.val = val;

return inner;
}

public static class HelptocountAttributes extends Node {
public HelptocountAttributes(String name) {
super(name);
}

public int val;

public int acc;

}
public HelptocountAttributes helpToCount(int acc) throws ParseException{
int val = 0;

GeneratedEnum COMMA = null;
SochetAttributes sochet = null;
GeneratedEnum EPS = null;

	HelptocountAttributes inner = new HelptocountAttributes("helpToCount");
switch (curToken.token) {
case COMMA -> {
COMMA = curToken;
inner.children.add(new Node(curToken.toString()));
curToken = lexer.next();

sochet = sochet();
inner.children.add(sochet);
    int tmp = acc - sochet.val;
    int cur1 = tmp = acc + 1 - sochet.val;
    int cur2 = 1;
    tmp++;
    for (int i = 2; i <= sochet.val; i++, tmp++) cur1 = cur1 * tmp;
    for (int i = 2; i <= sochet.val; i++, tmp++) cur2 = cur2 * i;
    val = cur1 / cur2;
    System.out.println(cur1);
    System.out.println(cur2);
    System.out.println(val);;

}
case DIV, ADD, SUB, MUL, RPAR, END -> {val = acc;}
		default -> throw new ParseException("Unexpected token", lexer.position());
}
inner.acc = acc;

inner.val = val;

return inner;
}


public static class Node {
public List<Node> children;
public String name;

public Node(String name) {
this.name = name;
children = new ArrayList<>();
}
}

}