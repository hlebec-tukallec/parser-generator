package python;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.*;
import java.util.Iterator;

public class GeneratedLexer implements Iterator<GeneratedEnum>, Iterable<GeneratedEnum> {
private final Map<GeneratedEnum.Token, Pattern> patterns = Map.ofEntries(
Map.entry(GeneratedEnum.Token.LAMBDA, Pattern.compile("lambda")),
Map.entry(GeneratedEnum.Token.COLON, Pattern.compile(":")),
Map.entry(GeneratedEnum.Token.VAR, Pattern.compile("[a-zA-Z]+")),
Map.entry(GeneratedEnum.Token.NUM, Pattern.compile("[0-9]+")),
Map.entry(GeneratedEnum.Token.COMMA, Pattern.compile(",")),
Map.entry(GeneratedEnum.Token.LPAREN, Pattern.compile("\\(")),
Map.entry(GeneratedEnum.Token.RPAREN, Pattern.compile("\\)")),
Map.entry(GeneratedEnum.Token.PLUS, Pattern.compile("\\+")),
Map.entry(GeneratedEnum.Token.MINUS, Pattern.compile("\\-")),
Map.entry(GeneratedEnum.Token.MULT, Pattern.compile("\\*")),
Map.entry(GeneratedEnum.Token.DIV, Pattern.compile("/"))
);

private final Pattern skip = Pattern.compile("[' \t\n\r']+");
private final Matcher matcher = skip.matcher("");
private int curStart, curEnd;
private String text;
private boolean hasNext;

public int position() { return curEnd; }

@Override
public Iterator<GeneratedEnum> iterator() {
return this;
}

public GeneratedLexer(String text) {
this.text = text;
curStart = 0;
curEnd = 0;
hasNext = true;
}

@Override
public boolean hasNext() {
return hasNext;
}

private boolean matchLookingAt() {
if (matcher.lookingAt()) {
curStart = curEnd;
curEnd = curStart + matcher.end();
matcher.reset(text.substring(curEnd));
return true;
}
return false;
}

@Override
public GeneratedEnum next() {
curStart = curEnd;
matcher.usePattern(skip);
matcher.reset(text.substring(curStart));
matchLookingAt();
for (var t : GeneratedEnum.Token.values()) {
if (t == GeneratedEnum.Token.END || t == GeneratedEnum.Token.EPS) {
continue;
}
matcher.usePattern(patterns.get(t));
if (matchLookingAt()) {
return new GeneratedEnum(t, text.substring(curStart, curEnd));
}
}
if (curEnd != text.length()) {
throw new Error();
}
hasNext = false;
return new GeneratedEnum(GeneratedEnum.Token.END, null);
}
}