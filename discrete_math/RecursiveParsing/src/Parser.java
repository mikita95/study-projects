import java.io.InputStream;
import java.text.ParseException;

public class Parser {
    LexicalAnalyzer lex;

    Tree E() throws ParseException {
        switch (lex.curToken()) {
            case NUMBER:
                String number = lex.curToken().getName();
                lex.nextToken();
                Tree n = new Tree(number);
                Tree ePrime = EPrime();
                return new Tree("E", n, ePrime);
            default:
                throw new ParseException("Number expected at position ", lex.curPos());
        }
    }

    Tree EPrime() throws ParseException {
        switch (lex.curToken()) {
            case NUMBER:
            case UNARY:
                Tree c = C();
                return new Tree("E'", c);
            case PLUS:
            case MINUS:
            case MULTIPLY:
            case END:
                // eps
                return new Tree("E'", new Tree("eps"));
            default:
                throw new ParseException("Number, operation or end expected at position ", lex.curPos());

        }
    }

    Tree U() throws ParseException {
        switch (lex.curToken()) {
            case UNARY:
                lex.nextToken();
                return new Tree("?");
            default: throw new AssertionError();
        }
    }

    Tree C() throws ParseException {
        switch (lex.curToken()) {
            case NUMBER:
                Tree e = E();
                Tree o = O();
                Tree cPrime = CPrime();
                return new Tree("C", e, o, cPrime);
            case UNARY:
                Tree u = U();
                Tree f = F();
                return new Tree("C", u, f);
            default:
                throw new ParseException("Number expected at position ", lex.curPos());
        }
    }

    Tree CPrime() throws ParseException {
        switch (lex.curToken()) {
            case UNARY:
            case NUMBER:
                Tree c = C();
                return new Tree("C'", c);
            case PLUS:
            case MINUS:
            case MULTIPLY:
            case END:
                // eps
                return new Tree("C'", new Tree("eps"));
            default:
                throw new ParseException("Number, operation or end expected at position ", lex.curPos());
        }
    }

    Tree F() throws ParseException {
        switch (lex.curToken()) {
            case NUMBER:
            case UNARY:
                Tree c = C();
                return new Tree("F", c);
            case PLUS:
            case MINUS:
            case MULTIPLY:
            case END:
                // eps
                return new Tree("C'", new Tree("eps"));
            default:
                throw new ParseException("Number, operation or end expected at position ", lex.curPos());

        }
    }

    Tree O() throws ParseException {
        switch (lex.curToken()) {
            case PLUS:
                lex.nextToken();
                return new Tree("+");
            case MINUS:
                lex.nextToken();
                return new Tree("-");
            case MULTIPLY:
                lex.nextToken();
                return new Tree("*");
            default:
                throw new ParseException("Operation sign expected at position ", lex.curPos());
        }
    }

    Tree parse(InputStream inputStream) throws ParseException {
        lex = new LexicalAnalyzer(inputStream);
        lex.nextToken();
        Tree e = E();
        if (lex.curToken() != Token.END)
            throw new ParseException("End of expression expected, but " +
                    lex.curToken().getName() + " found at position", lex.curPos());
        return e;
    }
}

