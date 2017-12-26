package test.java;
import java.util.*;
// Generated from <string> by ANTLR 4.5.3
import java.util.*;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GoodLanguageLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		LINE=1, DAPO=2, SEP=3, LCURLY=4, RCURLY=5, RD=6, WR=7, VAR=8, PLUS=9, MINUS=10, MULT=11, NUMBER=12, LPAREN=13, RPAREN=14, NAME=15, COMMA=16, ASSIGN=17, SEMICOLON=18;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"LINE", "DAPO", "SEP", "LCURLY", "RCURLY", "RD", "WR", "VAR", "PLUS", "MINUS", "MULT", "NUMBER", "LPAREN", "RPAREN", "NAME", "COMMA", "ASSIGN", "SEMICOLON"
	};

	private static final String[] _LITERAL_NAMES = {
		null, null, "'^'", "'$'", "'{'", "'}'", "'>>'", "'<<'", "'var'", "'+'", "'-'", "'*'", null, "'('", "')'", null, "','", "'='", "';'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "LINE", "DAPO", "SEP", "LCURLY", "RCURLY", "RD", "WR", "VAR", "PLUS", "MINUS", "MULT", "NUMBER", "LPAREN", "RPAREN", "NAME", "COMMA", "ASSIGN", "SEMICOLON"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	 

	public GoodLanguageLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "<string>"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\24X\b\1\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22\4\23\t\23\3\2\6\2)\n\2\r\2\16\2*\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\7\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\6\rF\n\r\r\r\16\rG\3\16\3\16\3\17\3\17\3\20\6\20O\n\20\r\20\16\20P\3\21\3\21\3\22\3\22\3\23\3\23\2\2\24\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\3\2\3\6\2C\\dsuwy|Z\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\3(\3\2\2\2\5,\3\2\2\2\7.\3\2\2\2\t\60\3\2\2\2\13\62\3\2\2\2\r\64\3\2\2\2\17\67\3\2\2\2\21:\3\2\2\2\23>\3\2\2\2\25@\3\2\2\2\27B\3\2\2\2\31E\3\2\2\2\33I\3\2\2\2\35K\3\2\2\2\37N\3\2\2\2!R\3\2\2\2#T\3\2\2\2%V\3\2\2\2\')\t\2\2\2(\'\3\2\2\2)*\3\2\2\2*(\3\2\2\2*+\3\2\2\2+\4\3\2\2\2,-\7`\2\2-\6\3\2\2\2./\7&\2\2/\b\3\2\2\2\60\61\7}\2\2\61\n\3\2\2\2\62\63\7\177\2\2\63\f\3\2\2\2\64\65\7@\2\2\65\66\7@\2\2\66\16\3\2\2\2\678\7>\2\289\7>\2\29\20\3\2\2\2:;\7x\2\2;<\7c\2\2<=\7t\2\2=\22\3\2\2\2>?\7-\2\2?\24\3\2\2\2@A\7/\2\2A\26\3\2\2\2BC\7,\2\2C\30\3\2\2\2DF\4\62;\2ED\3\2\2\2FG\3\2\2\2GE\3\2\2\2GH\3\2\2\2H\32\3\2\2\2IJ\7*\2\2J\34\3\2\2\2KL\7+\2\2L\36\3\2\2\2MO\4c|\2NM\3\2\2\2OP\3\2\2\2PN\3\2\2\2PQ\3\2\2\2Q \3\2\2\2RS\7.\2\2S\"\3\2\2\2TU\7?\2\2U$\3\2\2\2VW\7=\2\2W&\3\2\2\2\6\2*GP\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
