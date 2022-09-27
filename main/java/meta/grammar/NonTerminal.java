package meta.grammar;

public class NonTerminal {
    private final String inheritedAttr;
    private final String name;
    private final String synthesizedAttr;

    public NonTerminal(String inheritedAttr, String name, String synthesizedAttr) {
        this.inheritedAttr = inheritedAttr;
        this.name = name;
        this.synthesizedAttr = synthesizedAttr;
    }

    public String getInheritedAttr() {
        return inheritedAttr;
    }

    public String getName() {
        return name;
    }

    public String getSynthesizedAttr() {
        return synthesizedAttr;
    }
}
