import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class A {
    private InputStream in;
    private PrintWriter out;
    private final static String FILE_NAME = "test";

    public static void main(String[] args) {
        new A().run();
    }

    void solve() throws IOException {
        ANTLRInputStream antlrInputStream = new ANTLRInputStream(in);
        GoodLanguageLexer goodLanguageLexer = new GoodLanguageLexer(antlrInputStream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(goodLanguageLexer);
        GoodLanguageParser goodLanguageParser = new GoodLanguageParser(commonTokenStream);
        GoodLanguageParser.ProgramContext context = goodLanguageParser.program();
        out.println("#include<stdio.h> \n #include<math.h>");
        context.accept(new GoodLanguageBaseVisitor<Void>() {
            @Override
            public Void visitMethod(GoodLanguageParser.MethodContext methodContext) {
                out.println("int " + methodContext.getTokens(GoodLanguageParser.NAME).get(0).getText() + "(" +
                        separate(getNames(methodContext.lvalueblock()).stream().map(arg -> "int " + arg).collect(Collectors.toList()), ", ")
                        + ") {");
                MyVisitor generator = new MyVisitor();
                methodContext.body().accept(generator);
                if (!generator.wasReturned)
                    out.println("return 0;");
                out.println("}");
                return null;
            }

        });
        out.println("int main(int argc, char *argv[]) {");
        context.accept(new MyVisitor());
        out.println("return 0;\n}");
    }

    public ArrayList<String> getNames(GoodLanguageParser.LvalueblockContext context) {
        ArrayList<String> result = new ArrayList<>();
        context.accept(new GoodLanguageBaseVisitor<Void>() {
            @Override
            public Void visitLvalue(GoodLanguageParser.LvalueContext lvalueContext) {
                result.add(lvalueContext.getText());
                return null;
            }
        });
        return result;
    }

    public ArrayList<String> getNames(GoodLanguageParser.RvalueblockContext context) {
        ArrayList<String> result = new ArrayList<>();
        context.accept(new GoodLanguageBaseVisitor<Void>() {
            @Override
            public Void visitRvalue(GoodLanguageParser.RvalueContext ctx) {
                result.add(ctx.getText());
                return null;
            }
        });
        return result;
    }

    private class MyVisitor extends GoodLanguageBaseVisitor<Void> {
        boolean wasReturned;

        public MyVisitor() {
            super();
            wasReturned = false;
        }

        @Override
        public Void visitAssignment(GoodLanguageParser.AssignmentContext assignmentContext) {
            List<String> lvalueBlock = getNames(assignmentContext.lvalueblock());
            List<String> rvalueBlock = getNames(assignmentContext.rvalueblock());
            for (int i = 0; i < lvalueBlock.size(); i++)
            //for (String lvalue : lvalueBlock)
                out.println(assign(lvalueBlock.get(i), i < rvalueBlock.size()? rvalueBlock.get(i) : "0"));
            return null;
        }

        @Override
        public Void visitFunction(GoodLanguageParser.FunctionContext functionContext) {
            out.println(functionContext.getText() + ";");
            return null;
        }

        @Override
        public Void visitWhileStructure(GoodLanguageParser.WhileStructureContext whileStructureContext) {
            out.println("while (" + whileStructureContext.booleanExpression().getText() + ") {");
            visitBody(whileStructureContext.body());
            out.println("}");
            return null;
        }

        @Override
        public Void visitIfStructure(GoodLanguageParser.IfStructureContext ifStructureContext) {
            out.println("if (" + ifStructureContext.booleanExpression().getText() + ") {");
            visitBody(ifStructureContext.body(0));
            if (ifStructureContext.body(1) == null)
                out.println("}");
            else {
                out.println("} else {");
                visitBody(ifStructureContext.body(1));
                out.println("}");
            }
            return null;
        }

        @Override
        public Void visitForStructure(GoodLanguageParser.ForStructureContext forStructureContext) {
            //String name = forStructureContext.lvalue().getText();
            List<GoodLanguageParser.ForAtomContext> forAtoms = forStructureContext.forBlock().forAtom();
            for (GoodLanguageParser.ForAtomContext forAtom : forAtoms) {
                String name = forAtom.lvalue().getText();
                out.println("for (int " +
                        assign(name, forAtom.rvalue(0).getText()) +
                        " " + name + " < " + forAtom.rvalue(1).getText() + "; ++" + name + ") {");
            }
            visitBody(forStructureContext.body());
            for (GoodLanguageParser.ForAtomContext forAtom : forAtoms) out.println("}");
            return null;
        }

        @Override
        public Void visitWrite(GoodLanguageParser.WriteContext writeContext) {
            List<GoodLanguageParser.WritableContext> args = writeContext.writable();
            StringBuilder result = new StringBuilder();
            result.append("printf(\"");
            List<String> rvalues = new ArrayList<>();
            for (GoodLanguageParser.WritableContext arg : args) {
                if (arg.line() != null) {
                    result.append("%s");
                    rvalues.add(arg.getText());
                } else {
                    result.append("%d");
                    rvalues.add(arg.getText());
                }
            }
            if (writeContext.WRL() != null) result.append("\\n");
            result.append("\"");

            if (rvalues.size() > 0) {
                result.append(", ");
                result.append(separate(rvalues, ", "));
            }
            result.append(");");
            out.println(result.toString());
            return null;
        }

        @Override
        public Void visitRead(GoodLanguageParser.ReadContext readContext) {
            List<String> args = getNames(readContext.lvalueblock());
            StringBuilder result = new StringBuilder();
            result.append("scanf(\"");
            for (int i = 0; i < args.size(); i++)
                result.append("%d");
            result.append("\", ");
            result.append(separate(args.stream().map(a -> "&" + a).collect(Collectors.toList()), ", "));
            result.append(");");
            out.println(result.toString());
            return null;
        }

        @Override
        public Void visitMethod(GoodLanguageParser.MethodContext methodContext) {
            return null;
        }

        @Override
        public Void visitReturnStructure(GoodLanguageParser.ReturnStructureContext returnStructureContext) {
            wasReturned = true;
            String rvalue = "0";
            if (returnStructureContext.rvalue() != null)
                rvalue = returnStructureContext.rvalue().getText();
            out.println("return " + rvalue + ";");
            return null;
        }

        @Override
        public Void visitVariables(GoodLanguageParser.VariablesContext variablesContext) {
            List<String> names;
            String value = "0";
            if (variablesContext.lvalueblock() != null)
                names = getNames(variablesContext.lvalueblock());
            else {
                names = getNames(variablesContext.assignment().lvalueblock());
                List<String> values = getNames(variablesContext.assignment().rvalueblock());
                names = names.stream().map(n -> "int " + n).collect(Collectors.toList());
                for (int i = 0; i < names.size(); i++)
                    out.println(assign(names.get(i), i < values.size() ? values.get(i) : "0"));
                return null;
            }
            for (String var : names.stream().map(n -> "int " + n).collect(Collectors.toList()))
                out.println(assign(var, value));
            return null;
        }

        @Override
        public Void visitSwap(GoodLanguageParser.SwapContext swapContext) {
            String temp = "a_" + Math.abs(new Random().nextInt());
            String first = swapContext.lvalue(0).getText();
            String second = swapContext.lvalue(1).getText();
            out.println("int " + temp + " = " + first + ";");
            out.println(first + " = " + second + ";");
            out.println(second + " = " + temp + ";");
            return null;
        }

        @Override
        public Void visitSqrt(GoodLanguageParser.SqrtContext sqrtContext) {
            out.println("sqrt(" + sqrtContext.lvalue().getText() + ")");
            return null;
        }
    }

    private String separate(List<String> items, String separator) {
        if (items.isEmpty())
            return "";
        else {
            StringBuilder sb = new StringBuilder();
            sb.append(items.get(0));
            for (int i = 1; i < items.size(); i++) sb.append(separator).append(items.get(i));
            return sb.toString();
        }
    }


    private String assign(String name, String value) {
        return name + " = " + value + ";";
    }

    public void run() {
        try {
            in = new FileInputStream(FILE_NAME + ".in");
            // in = new FastScanner(is);
            out = new PrintWriter(new File(FILE_NAME + ".c"));
            solve();
            out.close();
        } catch (IOException ignored) {
        }
    }

    public static class FastScanner {

        private StringTokenizer tokenizer;

        public FastScanner(InputStream is) {
            try {
                ByteArrayOutputStream bos = new ByteArrayOutputStream(1024 * 1024);
                byte[] buf = new byte[1024];
                while (true) {
                    int read = is.read(buf);
                    if (read == -1)
                        break;
                    bos.write(buf, 0, read);
                }
                tokenizer = new StringTokenizer(new String(bos.toByteArray()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public boolean hasNext() {
            return tokenizer.hasMoreTokens();
        }

        public int nextInt() {
            return Integer.parseInt(tokenizer.nextToken());
        }

        public long nextLong() {
            return Long.parseLong(tokenizer.nextToken());
        }

        public double nextDouble() {
            return Double.parseDouble(tokenizer.nextToken());
        }

        public String next() {
            return tokenizer.nextToken();
        }

    }
}