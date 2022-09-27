package arithmetics;
public class GeneratedEnum {
public String text;
public Token token;
public GeneratedEnum(Token token, String text) {
this.text = text;
this.token = token;
}
enum Token {SUB, ADD, MUL, DIV, NUM, LPAR, RPAR, SEMI, COMMA, END, EPS;}
@Override
public String toString() {
return text;
}
}