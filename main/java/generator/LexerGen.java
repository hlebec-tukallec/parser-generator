package generator;

import meta.grammar.Grammar;
import meta.grammar.Terminal;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class LexerGen implements Generator {
    private static final String SKIP = "SKIP";

    @Override
    public void generate(Grammar grammar, String packageName) {
        List<Terminal> terminals = grammar.getTerminals();

        String curPackage = String.format("package %s;\n", packageName);
        String ans = String.format("""
                        %s

                        public class GeneratedLexer implements Iterator<GeneratedEnum>, Iterable<GeneratedEnum> {
                        %s

                        %s
                        private int curStart, curEnd;\nprivate String text;\nprivate boolean hasNext;

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
                        }""",
                generateImports(),
                generateTokenMap(terminals),
                generateSkip(terminals));

        saveToFile(curPackage + ans, Path.of("src", "main", "java", packageName, "GeneratedLexer.java"));
    }

    private String generateImports() {
        return generateImports(List.of("java.util.Map", "java.util.HashMap", "java.util.regex.*", "java.util.Iterator"));
    }

    private String generateTokenMap(List<Terminal> terminals) {
        return String.format("private final Map<GeneratedEnum.Token, Pattern> patterns = Map.ofEntries(\n%s\n);",
                terminals.stream()
                        .filter(x -> !x.getName().equals(SKIP))
                        .map(x ->
                                String.format("Map.entry(GeneratedEnum.Token.%s, Pattern.compile(\"%s\"))",
                                        x.getName(), x.getValue().substring(2, x.getValue().length() - 2))).collect(Collectors.joining(",\n")));
    }

    private String generateSkip(List<Terminal> terminals) {
        return String.format("private final Pattern skip = Pattern.compile(\"%s+\");" +
                        "\nprivate final Matcher matcher = skip.matcher(\"\");",
                terminals.stream().filter(x -> x.getName().equals(SKIP)).findAny().orElseThrow().getValue());
    }
}
