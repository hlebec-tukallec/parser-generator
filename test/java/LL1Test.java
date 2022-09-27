import generator.FirstAndFollow;
import meta.generated.GrammarLexer;
import meta.generated.GrammarParser;
import meta.grammar.Grammar;
import meta.grammar.MyTransition;
import meta.grammar.Rule;
import meta.grammar.SubTransition;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LL1Test {
    @Test
    public void test() throws IOException {
        String ll1 = Files.readString(Path.of("src", "main", "java", "arithmetics", "arithmetics.txt"));
        String notLl1 = Files.readString(Path.of("src", "test", "java", "notLL1.txt"));

        GrammarLexer lexer = new GrammarLexer(CharStreams.fromString(ll1));
        GrammarParser parser = new GrammarParser(new CommonTokenStream(lexer));
        Grammar grammar = parser.result().gram;

        FirstAndFollow firstAndFollow = new FirstAndFollow(grammar.getRules());
        List<Rule> rules = grammar.getRules();

        for (Rule r : rules) {
            List<MyTransition> transitions = r.getTransitions();
            for (int i = 0; i < transitions.size(); i++) {
                for (int j = i + 1; j < transitions.size(); j++) {
                    Set<String> A = firstAndFollow.findFirst(List.of(transitions.get(i)));
                    Set<String> B = firstAndFollow.findFirst(List.of(transitions.get(j)));
                    // first(a) x first(b) = empty
                    Assert.assertTrue(intersect(A, B).isEmpty());

                    if (!A.contains("EPS")) continue;
                    Set<String> N = firstAndFollow.getFollow().get(r.getName().getName());
                    // Follow(N) x first(b) = empty
                    Assert.assertTrue(intersect(N, B).isEmpty());
                }
            }
        }
    }

    private <T> Set<T> intersect(Set<T> a, Set<T> b) {
        Set<T> intersection = new HashSet<T>(b);
        intersection.retainAll(a);
        return intersection;
    }
}
