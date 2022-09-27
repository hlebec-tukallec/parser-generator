package meta.grammar;

import java.util.ArrayList;
import java.util.List;

public class Grammar {
    private final List<Terminal> terminals;
    private final List<Rule> rules;

    public Grammar() {
        this.terminals = new ArrayList<>();
        this.rules = new ArrayList<>();
    }

    public void addTerminal(Terminal terminal) {
        terminals.add(terminal);
    }

    public void addRule(Rule rule) {
        rules.add(rule);
    }

    public List<Terminal> getTerminals() {
        return terminals;
    }

    public List<Rule> getRules() {
        return rules;
    }
}
