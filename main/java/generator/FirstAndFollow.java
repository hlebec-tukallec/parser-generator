package generator;

import meta.grammar.MyTransition;
import meta.grammar.Rule;
import meta.grammar.SubTransition;

import java.util.*;

public class FirstAndFollow {
    private final List<Rule> rules;
    private final Map<String, Set<String>> first;
    private final Map<String, Set<String>> follow;

    public FirstAndFollow(List<Rule> rules) {
        this.rules = rules;
        first = new HashMap<>();
        follow = new HashMap<>();

        if (rules.isEmpty()) {
            return;
        }

        countFirst();
        countFollow();

        System.out.println(first);
        System.out.println('\n');
        System.out.println(follow);
    }

    public Map<String, Set<String>> getFirst() {
        return first;
    }

    public Map<String, Set<String>> getFollow() {
        return follow;
    }

    private void countFirst() {
        boolean flag = true;

        while (flag) {
            flag = false;

            for (Rule r : rules) {
                String nonTerminalName = r.getName().getName();
                if (!first.containsKey(nonTerminalName)) {
                    first.put(nonTerminalName, new HashSet<>());
                }
                int size = first.get(nonTerminalName).size();
                first.get(nonTerminalName).addAll(findFirst(r.getTransitions()));
                flag |= size < first.get(nonTerminalName).size();
            }
        }
    }

    public Set<String> findFirst(List<MyTransition> transitions) {
        Set<String> res = new HashSet<>();
        if (transitions.isEmpty() || transitions.get(0).getSubTransitions().isEmpty()) {
            res.add("EPS");
        }
        for (MyTransition trans : transitions) {
            List<SubTransition> subTransitions = trans.getSubTransitions();
            for (SubTransition sub : subTransitions) {
                String cur = sub.getName();
                if (cur.matches("[A-Z]+")) {
                    res.add(cur);
                } else if (first.containsKey(cur)) {
                    res.addAll(first.get(cur));
                    if (first.get(cur).contains("EPS")) {
                        continue;
                    }
                }
                break;
            }
        }
        return res;
    }

    private void countFollow() {
        boolean flag = true;

        follow.put("e", new HashSet<>(Set.of("END")));

        while (flag) {
            flag = false;

            for (Rule r : rules) {
                String nonTerminalName = r.getName().getName();
                if (!follow.containsKey(nonTerminalName)) {
                    follow.put(nonTerminalName, new HashSet<>());
                }
                List<MyTransition> transitions = r.getTransitions(); //A -> B D E | M N T
                for (MyTransition trans : transitions) {
                    List<SubTransition> subTransitions = trans.getSubTransitions();
                    for (int i = 0; i < subTransitions.size(); i++) {
                        SubTransition sub = subTransitions.get(i);
                        if (!sub.getName().matches("[A-Z]+")) {
                            if (!follow.containsKey(sub.getName())) {
                                follow.put(sub.getName(), new HashSet<>());
                            }
                            int size = follow.get(sub.getName()).size();
                            MyTransition tmp = new MyTransition(subTransitions.subList(i + 1, subTransitions.size()));
                            Set<String> gamma = findFirst(List.of(tmp));
                            boolean withEps = gamma.contains("EPS");

                            gamma.remove("EPS");
                            follow.get(sub.getName()).addAll(gamma);

                            if (withEps) {
                                follow.get(sub.getName()).addAll(follow.get(nonTerminalName));
                            }

                            flag |= size < follow.get(sub.getName()).size();
                        }
                    }
                }
            }
        }
    }
}
