package generator;

import meta.grammar.Grammar;
import meta.grammar.Terminal;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class EnumGen implements Generator {

    @Override
    public void generate(Grammar grammar, String packageName) {
        List<Terminal> terminals = grammar.getTerminals();

        String curPackage = String.format("package %s;\n", packageName);
        String ans = String.format(
                """
                        public class GeneratedEnum {
                        public String text;
                        public Token token;
                        public GeneratedEnum(Token token, String text) {
                        this.text = text;
                        this.token = token;
                        }
                        enum Token {%s, END, EPS;}
                        @Override
                        public String toString() {
                        return text;
                        }
                        }""",
                terminals.stream()
                        .filter(x -> !x.getName().equals("SKIP") && !x.getName().equals("EPS"))
                        .map(Terminal::getName)
                        .collect(Collectors.joining(", ")));

        saveToFile(curPackage + ans, Path.of("src", "main", "java", packageName, "GeneratedEnum.java"));

    }
}
