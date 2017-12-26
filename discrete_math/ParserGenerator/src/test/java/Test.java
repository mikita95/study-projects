package test.java;

import org.antlr.v4.runtime.ANTLRInputStream;

/**
 * Created by nikita on 08.05.16.
 */
public class Test {
    public static void main(String[] args) {
        /*GoodLanguageLexer lexer = new GoodLanguageLexer(new ANTLRInputStream("var$a=5+7;<<7*8"));
        GoodLanguageTranslator tr = new GoodLanguageTranslator(lexer);
        GoodLanguageNode t = tr.program();*/
        ArithmLexer lexer = new ArithmLexer(new ANTLRInputStream("((2-3)*4+2*3-6)*(0-5)"));
        ArithmTranslator tr = new ArithmTranslator(lexer);
        ArithmNode t = tr.e();
        System.out.print(t.attrs.get("v"));

        /*if (!lexer._token.getText().equals("2"))
            System.err.println("bad");

        if ((Integer)t.attrs.get("v") != 37)
            System.err.println("very bad");
        System.err.println("SUCCESS");*/

    }
}
