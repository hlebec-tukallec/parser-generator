package meta.grammar;

public class SubTransition {
    private final String name;
    private final String attr;
    private String code;

    public SubTransition(String name, String attr) {
        this.name = name;
        this.attr = attr;
    }

    public void addCode(String code) {
         this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getAttr() {
        return attr;
    }
}
