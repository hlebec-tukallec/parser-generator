package meta.grammar;

import java.util.ArrayList;
import java.util.List;

public class Rule {
    private NonTerminal name;
    private final List<MyTransition> transitions;

    public Rule() {
        this.transitions = new ArrayList<>();
    }

    public Rule(NonTerminal name) {
        this.name = name;
        this.transitions = new ArrayList<>();
    }

    public void setName(NonTerminal name) {
        this.name = name;
    }

    public void addTransition(MyTransition transition) {
        transitions.add(transition);
    }

    public NonTerminal getName() {
        return name;
    }

    public List<MyTransition> getTransitions() {
        return transitions;
    }
}
