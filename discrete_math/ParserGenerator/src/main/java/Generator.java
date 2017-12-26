package main.java;


import javafx.util.Pair;
import main.antlr.GrammarBaseVisitor;
import main.antlr.GrammarLexer;
import main.antlr.GrammarParser;
import org.antlr.v4.analysis.AnalysisPipeline;
import org.antlr.v4.codegen.CodeGenerator;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.tool.Grammar;
import org.antlr.v4.tool.LexerGrammar;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * Created by nikita on 07.05.16.
 */
public class Generator {
    private InputStream input;
    private String pack;
    private final static Lexeme EPSILON = new Lexeme("Token.EPSILON");
    private final static Lexeme EOF = new Lexeme("Token.EOF");

    private String convertStreamToString(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public Generator(InputStream input, String pack) {
        this.pack = pack;
        this.input = input;
    }

    public void generate() throws Exception {
        String stringInput = convertStreamToString(input);
        TranslatingGrammar grammar = parseTranslatingGrammar(stringInput);
        Grammar antlrGrammar = new Grammar(stringInput);
        new AnalysisPipeline(antlrGrammar).process();
        if (antlrGrammar.tool.errMgr.errors > 0)
            throw new Exception("Bad grammar");
        LexerGrammar lexer = antlrGrammar.implicitLexer;
        File lex = new File(grammar.getName() + "Lexer.java");
        new CodeGenerator(lexer).generateLexer().write(lex, null);
        if (pack != null) {
            String temp = "package " + pack + ";\n" + grammar.getHeader() + "\n" + new String(Files.readAllBytes(lex.toPath()));

            lex = new File(grammar.getName() + "Lexer.java");
            try (PrintWriter out = new PrintWriter(lex)) {
                out.println(temp);
            }
        }
        javafx.util.Pair<HashMap<Lexeme, HashSet<Lexeme>>, HashMap<Lexeme, HashSet<Lexeme>>> ff = getFirstAndFollow(grammar);
        ArrayList<StringBuilder> pc = parserCode(grammar, ff.getKey(), ff.getValue());
        try (PrintWriter out = new PrintWriter(grammar.getName() + "Translator.java")) {
            out.println(pc.get(1));
        }
        try (PrintWriter out = new PrintWriter(grammar.getName() + "Node.java")) {
            out.println(pc.get(0));
        }

    }

    private TranslatingGrammar parseTranslatingGrammar(String input) throws IOException {
        ANTLRInputStream antlrInput = new ANTLRInputStream(input);
        GrammarLexer lexer = new GrammarLexer(antlrInput);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        GrammarParser parser = new GrammarParser(tokens);
        GrammarParser.GrammarSpecContext root = parser.grammarSpec();
        String grammarName = root.ID().getText();
        Map<Lexeme, ArrayList<Rule>> rightPartsByLeft = new HashMap<>();
        Map<Lexeme, ArrayList<Attribute>> attrs = new HashMap<>();
        final Lexeme[] firstLexeme = {null};
        String members = root.MEMBERS().getText();
        members = members.substring(9, members.length() - 2);
        String header = root.HEADER().getText();
        header = header.substring(8, header.length() - 2);
        root.accept(new GrammarBaseVisitor<Void>() {
            @Override
            public Void visitRuleSpec(GrammarParser.RuleSpecContext ctx) {
                Lexeme leftPart = new Lexeme(ctx.ID().getText());
                if (firstLexeme[0] == null)
                    firstLexeme[0] = leftPart;
                GrammarParser.ReturnsSpecContext returnsSpecContext = ctx.returnsSpec();
                if (returnsSpecContext != null) {
                    for (GrammarParser.OneReturnContext r : returnsSpecContext.oneReturn()) {
                        ArrayList<Attribute> v = attrs.get(leftPart);
                        if (v == null) v = new ArrayList<Attribute>();
                        v.add(new Attribute(r.name.getText(), r.type.getText()));
                        attrs.put(leftPart, v);
                    }
                }
                ArrayList<Rule> rules = rightPartsByLeft.get(leftPart);
                if (rules == null) {
                    rightPartsByLeft.put(leftPart, new ArrayList<>());
                    rules = new ArrayList<Rule>();
                }

                for (GrammarParser.BodyContext b : ctx.body()) {
                    List<Lexeme> temp = new ArrayList<Lexeme>();
                    if (b.literal() != null)
                        temp = Collections.singletonList(new Literal(b.literal().getText()));
                    else if (b.ID() != null)
                        temp = b.ID().stream().map(x -> new Lexeme(x.getText())).collect(Collectors.toList());
                    rules.add(new Rule(leftPart, temp, b.block() != null ?
                            b.block().getText().substring(1, b.block().getText().length() - 2).trim() : null));
                }
                rightPartsByLeft.put(leftPart, rules);
                return null;
            }
        });
        Set<Lexeme> terms = rightPartsByLeft.keySet().stream().filter(
                x -> rightPartsByLeft.get(x).stream().allMatch(
                        y -> y.getRight().stream().allMatch(z -> z instanceof Literal || z instanceof FromToChars)
                )).collect(Collectors.toSet());
        Set<Lexeme> nonTerms = rightPartsByLeft.keySet();
        nonTerms.removeAll(terms);
        return new TranslatingGrammar(grammarName, firstLexeme[0], nonTerms, attrs, terms, rightPartsByLeft, members, header);
    }

    private javafx.util.Pair<HashMap<Lexeme, HashSet<Lexeme>>, HashMap<Lexeme, HashSet<Lexeme>>> getFirstAndFollow(TranslatingGrammar g) {
        ArrayList<Rule> nonTermRules = new ArrayList<>();
        //g.getRules().entrySet().stream()
        //.filter(x -> g.getNonTerminals().contains(x.getKey()))
        //.map(Map.Entry::getValue).findAny().orElse(new ArrayList<>());

        g.getRules().entrySet().stream().filter(e -> g.getNonTerminals().contains(e.getKey())).forEach(e -> {
            nonTermRules.addAll(e.getValue());
        });
        boolean changed = true;
        HashMap<Lexeme, HashSet<Lexeme>> first = new HashMap<>();
        for (Lexeme x : g.getNonTerminals()) first.put(x, new HashSet<>());
        BiFunction<Lexeme, Lexeme, Boolean> addToFirst = (to, l) -> {
            if (first.containsKey(to) && !first.get(to).contains(l)) {
                HashSet<Lexeme> temp = new HashSet<>(first.get(to));
                temp.add(l);
                first.put(to, temp);
                return true;
            }
            if (!first.containsKey(to)) {

                first.put(to, new HashSet<>(Collections.singletonList(l)));
                return true;

            }
            return false;
        };
        boolean flag = false;
        for (int h = 0; h < 3; h++) {
            changed = true;
            while (changed) {
                changed = false;
                for (Rule r : nonTermRules) {
                    Lexeme a = r.getLeft();
                    if (r.isEpsilonRule())
                        changed = addToFirst.apply(a, EPSILON);
                    else {
                        if (g.getTerminals().contains(r.getRight().get(0))) {
                            changed = addToFirst.apply(a, r.getRight().get(0));
                        } else {
                            int firstNonEpsilon = -1;
                            for (int i = 0; i < r.getRight().size(); i++) {
                                Lexeme x = r.getRight().get(i);
                                if (g.getTerminals().contains(x) || (first.get(x) != null && !first.get(x).contains(EPSILON))) {
                                    firstNonEpsilon = i;
                                    break;
                                }
                            }
                            for (int i = 0; i <= firstNonEpsilon; i++) {
                                Lexeme p = r.getRight().get(i);
                                if (g.getTerminals().contains(p))
                                    changed = addToFirst.apply(a, p);
                                else {
                                    if (first.get(p) != null) {
                                        for (Lexeme f : first.get(p))
                                            changed = addToFirst.apply(a, f);
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
        HashMap<Lexeme, HashSet<Lexeme>> follow = new HashMap<>();
        Set<Lexeme> allLexemes = new HashSet<>(g.getNonTerminals());
        allLexemes.addAll(g.getTerminals());
        for (Lexeme x : allLexemes) follow.put(x, new HashSet<Lexeme>());
        BiFunction<Lexeme, Lexeme, Boolean> addToFollow = (to, l) -> {
            if (follow.containsKey(to) && !follow.get(to).contains(l)) {
                HashSet<Lexeme> temp = follow.get(to);
                temp.add(l);
                follow.put(to, temp);
                return true;
            }
            if (!follow.containsKey(to)) {
                follow.put(to, new HashSet<>(Collections.singletonList(l)));
                return true;
            }
            return false;
        };
        changed = true;
        Function<Lexeme, HashSet<Lexeme>> goodFirst = l -> {
            if (g.getTerminals().contains(l))
                return new HashSet<>(Collections.singletonList(l));
            return first.get(l);
        };
        changed = addToFollow.apply(g.getStart(), EOF);
        for (int h = 0; h < 3; h++) {
            changed = true;
            while (changed) {
                changed = false;
                for (Rule r : nonTermRules) {
                    for (int i = 0; i < r.getRight().size(); i++) {
                        Lexeme b = r.getRight().get(i);
                        for (int j = i + 1; j < r.getRight().size(); j++) {
                            Lexeme gamma = r.getRight().get(j);
                            HashSet<Lexeme> eFirst = goodFirst.apply(gamma);
                            for (Lexeme x : eFirst)
                                if (x != EPSILON)
                                    changed = addToFollow.apply(b, x);
                            if (!eFirst.contains(EPSILON))
                                break;
                            if (j == r.getRight().size() - 1) {
                                if (follow.get(r.getLeft()) != null) {
                                    for (Lexeme x : follow.get(r.getLeft()))
                                        changed = addToFollow.apply(b, x);
                                }
                            }
                        }
                        if (i == r.getRight().size() - 1 || goodFirst.apply(r.getRight().get(i + 1)).contains(EPSILON)) {
                            if (follow.get(r.getLeft()) != null) {
                                for (Lexeme x : follow.get(r.getLeft()))
                                    if (x != EPSILON)
                                        changed = addToFollow.apply(b, x);
                            }
                        }
                    }
                }
            }
        }
        return new Pair<>(first, follow);
    }

    ArrayList<StringBuilder> parserCode(TranslatingGrammar g, HashMap<Lexeme, HashSet<Lexeme>> first, HashMap<Lexeme, HashSet<Lexeme>> follow) {
        ArrayList<StringBuilder> result = new ArrayList<>();
        result.add(new StringBuilder()); // nodeClassName
        result.add(new StringBuilder()); // Translator
        final int[] indent = {0};
        BiConsumer<String, Integer> writeln = (s, k) -> {
            for (int i = 0; i < indent[0]; i++)
                result.get(k).append("    ");
            result.get(k).append(s).append("\n");
        };

        Consumer<Runnable> indented = block -> {
            indent[0]++;
            block.run();
            indent[0]--;
        };

        HashMap<Rule, HashSet<Lexeme>> firstByRule = new HashMap<>();
        for (ArrayList<Rule> x : g.getRules().values()) {
            for (Rule r : x) {
                if (g.getNonTerminals().contains(r.getLeft())) {
                    HashSet<Lexeme> f = new HashSet<>();
                    int firstNonEpsilon = -1;
                    for (int i = 0; i < r.getRight().size(); i++) {
                        Lexeme z = r.getRight().get(i);
                        if (g.getTerminals().contains(z) ||
                                (first.get(z) != null && !first.get(z).contains(EPSILON))) {
                            firstNonEpsilon = i;
                            break;
                        }
                    }
                    for (int i = 0; i <= firstNonEpsilon; i++) {
                        Lexeme l = r.getRight().get(i);
                        if (g.getTerminals().contains(l))
                            f.add(l);
                        else {
                            if (first.get(l) != null)
                                f.addAll(first.get(l));
                        }
                    }
                    firstByRule.put(r, f);
                }
            }
        }
        String nodeClassName = g.getName() + "Node";
        String lexerClassName = g.getName() + "Lexer";
        // nodeClassName
        if (pack != null)
            writeln.accept("package " + pack + ";", 0);
        if (pack != null)
            writeln.accept("import static " + pack + "." + lexerClassName + ".*;", 0);
        else
            writeln.accept("import static " + lexerClassName + ".*;", 0);
        writeln.accept("import java.util.*;", 0);
        writeln.accept("import org.antlr.v4.runtime.*;", 0);
        writeln.accept("", 0);
        writeln.accept("public class " + nodeClassName + " {", 0);
        indented.accept(() -> {
                    writeln.accept("String name;", 0);
                    writeln.accept("public ArrayList<" + nodeClassName + "> children = new ArrayList<>();", 0);
                    writeln.accept("public HashMap<String, Object> attrs = new HashMap<>();", 0);
                    writeln.accept("public " + nodeClassName + "(String name) {", 0);
                    indented.accept(() -> writeln.accept("this.name = name;", 0));
                    writeln.accept("}", 0);
                    writeln.accept("public void addChild(" + nodeClassName + " n) {", 0);
                    indented.accept(() -> writeln.accept("children.add(n);", 0));
                    writeln.accept("}", 0);
                }
        );
        writeln.accept("}", 0);
        // Translator
        if (pack != null)
            writeln.accept("package " + pack + ";", 1);
        if (pack != null)
            writeln.accept("import static " + pack + "." + lexerClassName + ".*;", 1);
        else
            writeln.accept("import static " + lexerClassName + ".*;", 1);
        writeln.accept("import java.util.*;", 1);
        writeln.accept("import org.antlr.v4.runtime.*;", 1);
        writeln.accept("", 1);
        writeln.accept("public class " + g.getName() + "Translator {", 1);
        indented.accept(() -> {
                    writeln.accept(g.getMembers(), 1);
                    writeln.accept(lexerClassName + " lexer;", 1);
                    writeln.accept("Token curToken;", 1);
                    writeln.accept("public " + g.getName() + "Translator(" + lexerClassName + " lexer) {", 1);
                    indented.accept(() -> {
                                writeln.accept("this.lexer = lexer;", 1);
                                writeln.accept("curToken = nextToken();", 1);
                            }
                    );
                    writeln.accept("}", 1);
                    writeln.accept("Token nextToken() {", 1);
                    indented.accept(() -> {
                        writeln.accept("curToken = lexer.nextToken();", 1);
                        writeln.accept("return curToken;", 1);
                    });
                    writeln.accept("}", 1);
                    writeln.accept("String consume(Integer type) {", 1);
                    indented.accept(() -> {
                        writeln.accept("if (curToken.getType() != type) throw new RuntimeException();", 1);
                        writeln.accept("String result = curToken.getText();", 1);
                        writeln.accept("nextToken();", 1);
                        writeln.accept("return result;", 1);
                    });
                    writeln.accept("}", 1);

                    Function<Rule, String> injectedCode = (r) -> {
                        String res = r.getCode();
                        Matcher matcher = Pattern.compile("([0-9]+)#(\\w+)(\\b|\\w)").matcher(res);
                        while (matcher.find()) {
                            int index = Integer.parseInt(matcher.group(1));
                            Lexeme lex = r.getRight().get(index);
                            String attr = matcher.group(2);
                            String type = null;
                            if (g.getAttributes() != null && g.getAttributes().get(lex) != null) {
                                for (Attribute at : g.getAttributes().get(lex)) {
                                    if (at.getName().equals(attr)) {
                                        type = at.getType();
                                        break;
                                    }
                                }
                            }
                            if (type == null && g.getTerminals().contains(lex) && attr.equals("text")) {
                                type = "String";
                            }
                            String replacement = type != null ?
                                    "(" + type + ")(result.children.get(" + index + ").attrs.get(\"" + attr + "\"))"
                                    : matcher.group();
                            res = res.replaceFirst("([0-9]+)#(\\w+)(\\b|\\w)", replacement);

                        }


                        return res;
                    };

                    for (Lexeme nt : g.getNonTerminals()) {
                        writeln.accept("public " + nodeClassName + " " + nt.getString() + "() {", 1);
                        indented.accept(() -> {
                            writeln.accept(nodeClassName + " result = new " + nodeClassName + "(\"" + nt.getString() + "\");", 1);
                            if (g.getAttributes() != null && g.getAttributes().get(nt) != null)
                                g.getAttributes().get(nt).forEach(x -> writeln.accept(x.getType() + " " + x.getName() + " = null;", 1));
                            writeln.accept("switch (curToken.getType()) {", 1);
                            indented.accept(() -> {
                                for (Rule r : g.getRules().get(nt)) {
                                    if (!r.isEpsilonRule()) {
                                        for (Lexeme l : firstByRule.get(r)) {
                                            writeln.accept("case " + l.getString() + ": {", 1);
                                            indented.accept(() -> {
                                                int i = 0;
                                                for (Lexeme p : r.getRight()) {
                                                    if (g.getTerminals().contains(p)) {
                                                        writeln.accept(nodeClassName + " child" + i + " = new " + nodeClassName + "(consume(" + lexerClassName + "." + p.getString() + "));", 1);
                                                        writeln.accept("child" + i + ".attrs.put(\"text\", child" + i + ".name);", 1);
                                                        writeln.accept("result.addChild(child" + i + ");", 1);
                                                        i++;
                                                    } else {
                                                        writeln.accept("result.children.add(" + p.getString() + "());", 1);
                                                    }

                                                }
                                                if (r.getCode() != null)
                                                    writeln.accept(injectedCode.apply(r), 1);
                                                writeln.accept("break;", 1);
                                            });

                                            writeln.accept("}", 1);
                                        }
                                    }
                                }
                                if (firstByRule.entrySet().stream().filter(x -> x.getKey().getLeft() == nt).anyMatch(x -> x.getValue().isEmpty())) {
                                    for (Lexeme l : follow.get(nt)) {
                                        writeln.accept("case " + l.getString() + ": {", 1);
                                        Rule rule = null;
                                        for (Rule x : g.getRules().get(nt)) {
                                            if (x.isEpsilonRule()) {
                                                rule = x;
                                                break;
                                            }
                                        }
                                        if (rule != null && rule.getCode() != null) {
                                            final Rule finalRule = rule;
                                            indented.accept(() -> writeln.accept(finalRule.getCode(), 1));
                                        }
                                        indented.accept(() -> writeln.accept("break;", 1));

                                        writeln.accept("}", 1);
                                    }
                                    writeln.accept("", 1);
                                }
                                writeln.accept("default: throw new RuntimeException(\"Unexpected token\");", 1);
                            });
                            writeln.accept("}", 1);
                            if (g.getAttributes() != null && g.getAttributes().get(nt) != null)
                                g.getAttributes().get(nt).forEach(x -> {
                                    writeln.accept("result.attrs.put(\"" + x.getName() + "\", " + x.getName() + ");", 1);
                                });
                            writeln.accept("return result;", 1);
                        });
                        writeln.accept("}", 1);

                    }
                }
        );
        writeln.accept("}", 1);
        return result;
    }
}