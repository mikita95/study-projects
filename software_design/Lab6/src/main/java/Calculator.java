import tokenizer.Tokenizer;
import tokens.Token;
import visitors.CalcVisitor;
import visitors.ParserVisitor;
import visitors.PrintVisitor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Created by nikita on 15.12.16.
 */
public class Calculator {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Usage: [expression]");
            System.exit(1);
        }
        String expr = args[0];

        InputStream stream = new ByteArrayInputStream(expr.getBytes(StandardCharsets.UTF_8));
        List<Token> tokens = new Tokenizer(stream).getTokens();

        ParserVisitor parserVisitor = new ParserVisitor();
        tokens.forEach(t -> t.accept(parserVisitor));
        List<Token> polish = parserVisitor.getPolish();

        PrintVisitor printVisitor = new PrintVisitor();
        polish.forEach(t -> t.accept(printVisitor));

        CalcVisitor calcVisitor = new CalcVisitor();
        polish.forEach(t -> t.accept(calcVisitor));

        System.out.println(printVisitor);
        System.out.println(calcVisitor.getResult());

    }
}
