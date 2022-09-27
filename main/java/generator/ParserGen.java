package generator;

import meta.grammar.*;

import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class ParserGen implements Generator {
    private static final String PARSE_EXCEPTION_NAME = "ParseException";
    private static Grammar grammar;
    private HashSet<String> terminals;
    public FirstAndFollow firstAndFollow;

    @Override
    public void generate(Grammar grammar, String packageName) {
        ParserGen.grammar = grammar;
        List<Rule> rules = grammar.getRules();
        String curPackage = String.format("package %s;\n", packageName);
        firstAndFollow = new FirstAndFollow(grammar.getRules());

        String ans = String.format("""
                        %s
                        public class GeneratedParser {
                        public GeneratedEnum curToken;
                        public final GeneratedLexer lexer;
                        %s
                        %s
                                                
                                                
                        %s
                        }""",
                generateImports(List.of("java.text.ParseException", "java.util.*")),
                generateConstructor(),
                rules.stream()
                        .map(this::generateEverything)
                        .collect(Collectors.joining("\n")),
                generateTreeNodeClass());

        saveToFile(curPackage + ans, Path.of("src", "main", "java", packageName, "GeneratedParser.java"));
    }

    private String generateEverything(Rule r) {
        this.terminals = new HashSet<>();
        for (Terminal term : grammar.getTerminals()) {
            terminals.add(term.getName());
        }
        String representation = generateDataType(r.getName());
        return '\n' + representation + '\n' + generateHeader(r.getName()) + "{\n" + generateBody(r) + "\n}";
    }

    private String generateBody(Rule r) {
        String innerAttributesDeclaration = innerAttributesDeclaration(r.getName().getSynthesizedAttr());
        String transitionVariablesDeclaration = generateTransitionVariablesDeclaration(r);
        String innerAttributesWrapper = innerAttributesWrapper(r.getName());
        String calls = generateTransitions(r);
        String inheritedAssignment = wrapAttributes(r.getName().getInheritedAttr());
        String innerAssignment = wrapAttributes(r.getName().getSynthesizedAttr());
        String returnTail = "return inner;";
        return innerAttributesDeclaration + '\n'
                + transitionVariablesDeclaration + '\n'
                + innerAttributesWrapper + '\n'
                + calls + '\n'
                + inheritedAssignment + '\n'
                + innerAssignment + '\n'
                + returnTail;
    }

    private String wrapAttributes(String attr) {
        attr = attr.substring(1, attr.length() - 1);
        StringBuilder sb = new StringBuilder();
        String[] attrs = attr.split("int");
        for (String str : attrs) {
            if (str.length() != 0) {
                sb.append(String.format("%s.%s = %s;\n", "inner", str, str));
            }
        }
        return sb.toString();
    }

    private String generateTransitions(Rule r) {
        List<MyTransition> transitions = r.getTransitions();
        if (transitions.size() > 1) {
            return generateSwitch(transitions, r.getName().getName());
        }
        StringBuilder sb = new StringBuilder();
        MyTransition tran = transitions.get(0);
        List<SubTransition> subTransitions = tran.getSubTransitions();
        for (SubTransition subTran : subTransitions) {
            if (terminals.contains(subTran.getName())) {
                sb.append(javaTerminal(subTran.getName()));
                sb.append(String.format("\ninner.children.add(new Node(\"%s\"));", subTran.getName()));
            } else {
                sb.append(javaNonTerminal(subTran.getName(), subTran.getAttr()));
                sb.append(String.format("\ninner.children.add(%s);", subTran.getName()));
            }


            if (subTran.getCode() != null) {
                sb.append(subTran.getCode(), 1, subTran.getCode().length() - 1).append('\n');
            }
            sb.append('\n');
        }
        if (tran.getCode() != null) {
            sb.append(tran.getCode(), 1, tran.getCode().length() - 1).append('\n');
        }
        return sb.toString();
    }

    private String javaTerminal(String name) {
        return String.format(
                """
                        %s = curToken;
                        if (GeneratedEnum.Token.%s != curToken.token) {
                        throw new ParseException("UNEXPECTED TOKEN(((", lexer.position());
                        }
                        curToken = lexer.next();
                        """,
                name, name, name);
    }

    private String javaNonTerminal(String name, String attr) {
        String attributes = attr == null ? "" : attr.substring(1, attr.length() - 1);
        return String.format("%s = %s(%s);",
                name,
                name,
                String.join(", ", attributes));
    }

    private String generateSwitch(List<MyTransition> transitions, String nonTerminalName) {
        return String.format("""
                switch (curToken.token) {%s
                }""", generateInnerSwitch(transitions, nonTerminalName));
    }

    private String addNext(SubTransition sub) {
        StringBuilder sb = new StringBuilder(ADD_NEXT);
        if (sub.getCode() != null) {
            sb.append(sub.getCode(), 1, sub.getCode().length() - 1).append(";\n");
        }
        return sb.toString();
    }

    private static final String ADD_NEXT = """
            inner.children.add(new Node(curToken.toString()));
            curToken = lexer.next();
            """;

    private String generateInnerSwitch(List<MyTransition> transitions, String nonTerminalName) {
        StringBuilder sb = new StringBuilder();
        Set<String> follow = firstAndFollow.getFollow().get(nonTerminalName);
        for (MyTransition trans : transitions) {
            List<SubTransition> subTrans = trans.getSubTransitions();
            if (!subTrans.get(0).getName().equals("EPS")) {
                String ans = String.format("\ncase %s -> {\n%s = curToken;\n%s\n%s\n}",
                        subTrans.get(0).getName(),
                        subTrans.get(0).getName(),
                        subTrans.get(0).getName().equals("END") ? "" : addNext(subTrans.get(0)),
                        generateOthers(subTrans));
                sb.append(ans);
            } else {
                sb.append("\ncase ");
                int cnt = 0;
                for (String name : follow) {
                    sb.append(name);
                    cnt++;
                    if (cnt < follow.size()) {
                        sb.append(", ");
                    }
                }
                if (subTrans.get(0).getCode() != null) {
                    sb.append(String.format(" -> %s", subTrans.stream().map(SubTransition::getCode)
                            .collect(Collectors.joining(";"))));
                } else {
                    sb.append(" -> {}");
                }
            }
        }
        sb.append("\n\t\tdefault -> throw new ParseException(\"Unexpected token\", lexer.position());");

        return sb.toString();
    }

    private String generateOthers(List<SubTransition> subTransitions) {
        StringBuilder sb = new StringBuilder();
        subTransitions = subTransitions.subList(1, subTransitions.size());
        for (SubTransition sub : subTransitions) {
            if (terminals.contains(sub.getName())) {
                sb.append(javaTerminal(sub.getName()));
                if (sub.getName().equals("EPS")) {
                    continue;
                }
                sb.append(String.format("\ninner.children.add(new Node(\"%s\"));", sub.getName()));
            } else {
                sb.append(javaNonTerminal(sub.getName(), sub.getAttr()));
                sb.append(String.format("\ninner.children.add(%s);", sub.getName()));
            }
            if (sub.getCode() != null) {
                sb.append(sub.getCode(), 1, sub.getCode().length() - 1).append(";\n");
            }
        }
        return sb.toString();
    }

    private String innerAttributesWrapper(NonTerminal nonTerminal) {
        String name = nonTerminal.getName();
        return String.format("\t%s inner = new %s(\"%s\");", stringToClassName(name), stringToClassName(name), name);
    }

    private String generateTransitionVariablesDeclaration(Rule r) {
        StringBuilder sb = new StringBuilder();
        List<MyTransition> curTrans = r.getTransitions();
        Set<String> varSet = new HashSet<>();
        for (MyTransition cur : curTrans) {
            for (SubTransition subTransition : cur.getSubTransitions()) {
                String name = subTransition.getName();
                if (terminals.contains(name) || name.equals("EPS")) {
                    varSet.add("GeneratedEnum " + name);
                } else {
                    varSet.add(stringToClassName(name) + " " + name);
                }
            }
        }
        for (String s : varSet) {
            sb.append(s).append(" = null;\n");
        }
        return sb.toString();
    }

    private String innerAttributesDeclaration(String attributes) {
        String attr = attributes.substring(1, attributes.length() - 1);
        if (attr.length() == 0) {
            return "";
        }
        String[] vars = attr.split("int");
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < vars.length; i++) {
            sb.append("int ").append(vars[i]).append(" = 0;\n");
        }
        return sb.toString();
    }

    private String generateHeader(NonTerminal nonTerminal) {
        return String.format("public %s %s(%s) throws %s",
                stringToClassName(nonTerminal.getName()),
                nonTerminal.getName(),
                attributesToParams(nonTerminal.getInheritedAttr()),
                PARSE_EXCEPTION_NAME);
    }

    private String attributesToParams(String attributes) {
        String attr = attributes.substring(1, attributes.length() - 1);
        if (attr.length() == 0) {
            return "";
        }
        String[] vars = attr.split("int");
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < vars.length; i++) {
            if (i != vars.length - 1) {
                sb.append("int ").append(vars[i]).append(", ");
            } else {
                sb.append("int ").append(vars[i]);

            }
        }
        return sb.toString();
    }

    private String generateDataType(NonTerminal nonTerminal) {
        return String.format("public static class %s extends Node {\n%s\n%s\n%s\n}",
                stringToClassName(nonTerminal.getName()),
                generateRequiredConstructor(nonTerminal.getName()),
                attributesToVars(nonTerminal.getSynthesizedAttr()),
                attributesToVars(nonTerminal.getInheritedAttr()));
    }

    private static String stringToClassName(String s) {
        String name = s.toLowerCase(Locale.ROOT);
        name = Character.toUpperCase(name.charAt(0)) + name.substring(1) + "Attributes";
        return name;
    }

    private String generateRequiredConstructor(String name) {
        return String.format("""
                public %s(String name) {
                super(name);
                }
                """, stringToClassName(name));
    }

    private String attributesToVars(String attributes) {
        String attr = attributes.substring(1, attributes.length() - 1);
        if (attr.length() == 0) {
            return "";
        }
        String[] vars = attr.split("int");
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < vars.length; i++) {
            sb.append("public int ").append(vars[i]).append(";\n");
        }
        return sb.toString();
    }

    private String generateConstructor() {
        return "public GeneratedParser(String text) {\nlexer = new GeneratedLexer(text);\ncurToken = lexer.next();\n}";
    }

    private String generateTreeNodeClass() {
        return """
                public static class Node {
                public List<Node> children;
                public String name;
                                
                public Node(String name) {
                this.name = name;
                children = new ArrayList<>();
                }
                }
                """;
    }
}
