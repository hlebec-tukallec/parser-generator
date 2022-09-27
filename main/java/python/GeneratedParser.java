package python;
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



}
public EAttributes e() throws ParseException{

GeneratedEnum LAMBDA = null;
GeneratedEnum COLON = null;
ExprAttributes expr = null;
TAttributes t = null;

	EAttributes inner = new EAttributes("e");
LAMBDA = curToken;
if (GeneratedEnum.Token.LAMBDA != curToken.token) {
throw new ParseException("UNEXPECTED TOKEN(((", lexer.position());
}
curToken = lexer.next();

inner.children.add(new Node("LAMBDA"));
t = t();
inner.children.add(t);
COLON = curToken;
if (GeneratedEnum.Token.COLON != curToken.token) {
throw new ParseException("UNEXPECTED TOKEN(((", lexer.position());
}
curToken = lexer.next();

inner.children.add(new Node("COLON"));
expr = expr();
inner.children.add(expr);



return inner;
}

public static class TAttributes extends Node {
public TAttributes(String name) {
super(name);
}



}
public TAttributes t() throws ParseException{

GeneratedEnum VAR = null;
T2Attributes t2 = null;
GeneratedEnum EPS = null;

	TAttributes inner = new TAttributes("t");
switch (curToken.token) {
case VAR -> {
VAR = curToken;
inner.children.add(new Node(curToken.toString()));
curToken = lexer.next();

t2 = t2();
inner.children.add(t2);
}
case COLON -> {}
		default -> throw new ParseException("Unexpected token", lexer.position());
}


return inner;
}

public static class T2Attributes extends Node {
public T2Attributes(String name) {
super(name);
}



}
public T2Attributes t2() throws ParseException{

GeneratedEnum COMMA = null;
T3Attributes t3 = null;
GeneratedEnum EPS = null;

	T2Attributes inner = new T2Attributes("t2");
switch (curToken.token) {
case COMMA -> {
COMMA = curToken;
inner.children.add(new Node(curToken.toString()));
curToken = lexer.next();

t3 = t3();
inner.children.add(t3);
}
case COLON -> {}
		default -> throw new ParseException("Unexpected token", lexer.position());
}


return inner;
}

public static class T3Attributes extends Node {
public T3Attributes(String name) {
super(name);
}



}
public T3Attributes t3() throws ParseException{

GeneratedEnum VAR = null;
T2Attributes t2 = null;

	T3Attributes inner = new T3Attributes("t3");
VAR = curToken;
if (GeneratedEnum.Token.VAR != curToken.token) {
throw new ParseException("UNEXPECTED TOKEN(((", lexer.position());
}
curToken = lexer.next();

inner.children.add(new Node("VAR"));
t2 = t2();
inner.children.add(t2);



return inner;
}

public static class ExprAttributes extends Node {
public ExprAttributes(String name) {
super(name);
}



}
public ExprAttributes expr() throws ParseException{

E2Attributes e2 = null;
E1Attributes e1 = null;

	ExprAttributes inner = new ExprAttributes("expr");
e1 = e1();
inner.children.add(e1);
e2 = e2();
inner.children.add(e2);



return inner;
}

public static class E2Attributes extends Node {
public E2Attributes(String name) {
super(name);
}



}
public E2Attributes e2() throws ParseException{

GeneratedEnum MINUS = null;
FAttributes f = null;
GeneratedEnum PLUS = null;
MAttributes m = null;
GeneratedEnum EPS = null;

	E2Attributes inner = new E2Attributes("e2");
switch (curToken.token) {
case PLUS -> {
PLUS = curToken;
inner.children.add(new Node(curToken.toString()));
curToken = lexer.next();

f = f();
inner.children.add(f);m = m();
inner.children.add(m);
}
case MINUS -> {
MINUS = curToken;
inner.children.add(new Node(curToken.toString()));
curToken = lexer.next();

f = f();
inner.children.add(f);m = m();
inner.children.add(m);
}
case END, RPAREN -> {}
		default -> throw new ParseException("Unexpected token", lexer.position());
}


return inner;
}

public static class E1Attributes extends Node {
public E1Attributes(String name) {
super(name);
}



}
public E1Attributes e1() throws ParseException{

FAttributes f = null;
MAttributes m = null;

	E1Attributes inner = new E1Attributes("e1");
f = f();
inner.children.add(f);
m = m();
inner.children.add(m);



return inner;
}

public static class MAttributes extends Node {
public MAttributes(String name) {
super(name);
}



}
public MAttributes m() throws ParseException{

FAttributes f = null;
GeneratedEnum DIV = null;
MAttributes m = null;
GeneratedEnum EPS = null;
GeneratedEnum MULT = null;

	MAttributes inner = new MAttributes("m");
switch (curToken.token) {
case MULT -> {
MULT = curToken;
inner.children.add(new Node(curToken.toString()));
curToken = lexer.next();

f = f();
inner.children.add(f);m = m();
inner.children.add(m);
}
case DIV -> {
DIV = curToken;
inner.children.add(new Node(curToken.toString()));
curToken = lexer.next();

f = f();
inner.children.add(f);m = m();
inner.children.add(m);
}
case END, RPAREN, PLUS, MINUS -> {}
		default -> throw new ParseException("Unexpected token", lexer.position());
}


return inner;
}

public static class FAttributes extends Node {
public FAttributes(String name) {
super(name);
}



}
public FAttributes f() throws ParseException{

GeneratedEnum VAR = null;
GeneratedEnum NUM = null;
GeneratedEnum RPAREN = null;
ExprAttributes expr = null;
GeneratedEnum LPAREN = null;

	FAttributes inner = new FAttributes("f");
switch (curToken.token) {
case VAR -> {
VAR = curToken;
inner.children.add(new Node(curToken.toString()));
curToken = lexer.next();


}
case NUM -> {
NUM = curToken;
inner.children.add(new Node(curToken.toString()));
curToken = lexer.next();


}
case LPAREN -> {
LPAREN = curToken;
inner.children.add(new Node(curToken.toString()));
curToken = lexer.next();

expr = expr();
inner.children.add(expr);RPAREN = curToken;
if (GeneratedEnum.Token.RPAREN != curToken.token) {
throw new ParseException("UNEXPECTED TOKEN(((", lexer.position());
}
curToken = lexer.next();

inner.children.add(new Node("RPAREN"));
}
		default -> throw new ParseException("Unexpected token", lexer.position());
}


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