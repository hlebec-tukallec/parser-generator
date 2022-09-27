package meta.grammar;


import java.util.ArrayList;
import java.util.List;

public class MyTransition {

    private final List<SubTransition> subTransitions;
    private String code;

    public MyTransition() {
        this.subTransitions = new ArrayList<>();
    }

    public MyTransition(String code) {
        this.subTransitions = new ArrayList<>();
        this.code = code;
    }

    public MyTransition(List<SubTransition> subTransitions) {
        this.subTransitions = subTransitions;
    }

    public void addSubTransition(SubTransition subTransition) {
        this.subTransitions.add(subTransition);
    }

    public void addCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public List<SubTransition> getSubTransitions() {
        return subTransitions;
    }
}
