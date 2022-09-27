package meta.grammar;

public class Terminal {
    private final String name;
    private final String value;

    public Terminal(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getJavaValue() {
        return String.format("Pattern.compile(\"%s\")", value);
    }
}
