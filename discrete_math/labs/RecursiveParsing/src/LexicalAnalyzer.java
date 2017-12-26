import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

public class LexicalAnalyzer {
    private InputStream is;
    private int curChar;
    private int curPos;
    private Token curToken;

    public LexicalAnalyzer(InputStream is) throws ParseException {
        this.is = is;
        curPos = 0;
        nextChar();
    }

    private void nextChar() throws ParseException {
        curPos++;
        try {
            curChar = is.read();
        } catch (IOException e) {
            throw new ParseException(e.getMessage(), curPos);
        }
    }

    public void nextToken() throws ParseException {
        while (Character.isWhitespace(curChar)) nextChar();
        switch (curChar) {
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                String s = String.valueOf((char) curChar);
                do {
                    nextChar();
                    s += (char) curChar;
                } while (!Character.isWhitespace(curChar) && curChar != -1 &&
                        curChar != '+' && curChar != '-' && curChar != '*');
                curToken = Token.NUMBER;
                curToken.setName(s);
                break;
            case '+':
                nextChar();
                curToken = Token.PLUS;
                curToken.setName("+");
                break;
            case '-':
                nextChar();
                curToken = Token.MINUS;
                curToken.setName("-");
                break;
            case '*':
                nextChar();
                curToken = Token.MULTIPLY;
                curToken.setName("*");
                break;
            case '?':
                nextChar();
                curToken = Token.UNARY;
                curToken.setName("?");
                break;
            case -1:
                curToken = Token.END;
                break;
            default:
                throw new ParseException("Illegal character " + (char) curChar + " at position ", curPos);
        }
    }


    public int curChar() {
        return curChar;
    }

    public int curPos() {
        return curPos;
    }

    public Token curToken() {
        return curToken;
    }
}
