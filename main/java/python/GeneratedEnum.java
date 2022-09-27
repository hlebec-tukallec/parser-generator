package python;
public class GeneratedEnum {
public String text;
public Token token;
public GeneratedEnum(Token token, String text) {
this.text = text;
this.token = token;
}
enum Token {LAMBDA, COLON, VAR, NUM, COMMA, LPAREN, RPAREN, PLUS, MINUS, MULT, DIV, END, EPS;}
@Override
public String toString() {
return text;
}
}