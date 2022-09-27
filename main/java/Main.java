import generator.EnumGen;
import generator.LexerGen;
import generator.ParserGen;
import meta.generated.GrammarLexer;
import meta.generated.GrammarParser;
import meta.grammar.Grammar;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {
        generateArithmetics();
        generatePython();
    }

    private static void generateArithmetics() throws IOException {
        String expressions = Files.readString(Path.of("src", "main", "java", "arithmetics", "arithmetics.txt"));

        GrammarLexer lexer = new GrammarLexer(CharStreams.fromString(expressions));
        GrammarParser parser = new GrammarParser(new CommonTokenStream(lexer));
        Grammar grammar = parser.result().gram;

        EnumGen enumGen = new EnumGen();
        enumGen.generate(grammar, "arithmetics");
        LexerGen lexerGenerator = new LexerGen();
        lexerGenerator.generate(grammar, "arithmetics");
        ParserGen parserGen = new ParserGen();
        parserGen.generate(grammar, "arithmetics");
    }

    private static void generatePython() throws IOException {
        String expressions = Files.readString(Path.of("src", "main", "java", "python", "python.txt"));

        GrammarLexer lexer = new GrammarLexer(CharStreams.fromString(expressions));
        GrammarParser parser = new GrammarParser(new CommonTokenStream(lexer));
        Grammar grammar = parser.result().gram;

        EnumGen enumGen = new EnumGen();
        enumGen.generate(grammar, "python");
        LexerGen lexerGenerator = new LexerGen();
        lexerGenerator.generate(grammar, "python");
        ParserGen parserGen = new ParserGen();
        parserGen.generate(grammar, "python");
    }
}