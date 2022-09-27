package generator;

import meta.grammar.Grammar;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public interface Generator {
    public void generate(Grammar grammar, String packageName);

    default String generateImports(List<String> packages) {
        return packages.stream().map(x -> "import " + x + ";").collect(Collectors.joining("\n"));
    }

    default void saveToFile(String ans, Path path) {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)) {
            bufferedWriter.write(ans);
        } catch (IOException ignored) {
//            throw new ParserGenerationException("Can't create TypeToken.java");
        }
    }
}
