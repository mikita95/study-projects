// Generated from src/main/antlr/Grammar.g4 by ANTLR 4.5.3
package main.antlr;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GrammarLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, MEMBERS=2, HEADER=3, QUOTEDWS=4, ACTION=5, GRAMMAR=6, COLON=7, 
		OR=8, SEMI=9, APO=10, DOT=11, LCU=12, RCU=13, RPAREN=14, LPAREN=15, PLUS=16, 
		AST=17, QUE=18, LSQ=19, RSQ=20, RETURNS=21, ASG=22, COMMA=23, ID=24, RE=25, 
		BR=26, WS=27, ANYCHAR=28;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "MEMBERS", "HEADER", "QUOTEDWS", "ACTION", "GRAMMAR", "COLON", 
		"OR", "SEMI", "APO", "DOT", "LCU", "RCU", "RPAREN", "LPAREN", "PLUS", 
		"AST", "QUE", "LSQ", "RSQ", "RETURNS", "ASG", "COMMA", "ID", "RE", "BR", 
		"WS", "ANYCHAR"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'\"'", null, null, "'' ''", null, "'grammar'", "':'", "'|'", "';'", 
		"'''", "'.'", "'{'", "'}'", "')'", "'('", "'+'", "'*'", "'?'", "'['", 
		"']'", "'returns'", "'='", "','", null, "'\r'", "'\n'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, "MEMBERS", "HEADER", "QUOTEDWS", "ACTION", "GRAMMAR", "COLON", 
		"OR", "SEMI", "APO", "DOT", "LCU", "RCU", "RPAREN", "LPAREN", "PLUS", 
		"AST", "QUE", "LSQ", "RSQ", "RETURNS", "ASG", "COMMA", "ID", "RE", "BR", 
		"WS", "ANYCHAR"
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


	public GrammarLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Grammar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\36\u00ca\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\3\2\3\2\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\7\3I\n\3\f\3\16\3L\13\3\3\3\3\3\3\3\3\4\3"+
		"\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\7\4[\n\4\f\4\16\4^\13\4\3\4\3\4\3\4"+
		"\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\7\6m\n\6\f\6\16\6p\13\6\3\6\3"+
		"\6\3\6\3\6\3\6\3\6\7\6x\n\6\f\6\16\6{\13\6\3\6\7\6~\n\6\f\6\16\6\u0081"+
		"\13\6\3\6\5\6\u0084\n\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\t\3"+
		"\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\21"+
		"\3\21\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3\26\3\26\3\26\3\26\3\26"+
		"\3\26\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31\7\31\u00b8\n\31\f\31\16"+
		"\31\u00bb\13\31\3\32\3\32\3\32\3\32\3\33\3\33\3\33\3\33\3\34\3\34\3\34"+
		"\3\34\3\35\3\35\6J\\n\177\2\36\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13"+
		"\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61"+
		"\32\63\33\65\34\67\359\36\3\2\7\4\2\f\f\17\17\3\3\177\177\5\2C\\aac|\7"+
		"\2\62;AAC\\aac|\5\2\13\f\17\17\"\"\u00d2\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3"+
		"\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2"+
		"\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35"+
		"\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)"+
		"\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2"+
		"\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\3;\3\2\2\2\5=\3\2\2\2\7P\3\2\2\2\t"+
		"b\3\2\2\2\13f\3\2\2\2\r\u0085\3\2\2\2\17\u008d\3\2\2\2\21\u008f\3\2\2"+
		"\2\23\u0091\3\2\2\2\25\u0093\3\2\2\2\27\u0095\3\2\2\2\31\u0097\3\2\2\2"+
		"\33\u0099\3\2\2\2\35\u009b\3\2\2\2\37\u009d\3\2\2\2!\u009f\3\2\2\2#\u00a1"+
		"\3\2\2\2%\u00a3\3\2\2\2\'\u00a5\3\2\2\2)\u00a7\3\2\2\2+\u00a9\3\2\2\2"+
		"-\u00b1\3\2\2\2/\u00b3\3\2\2\2\61\u00b5\3\2\2\2\63\u00bc\3\2\2\2\65\u00c0"+
		"\3\2\2\2\67\u00c4\3\2\2\29\u00c8\3\2\2\2;<\7$\2\2<\4\3\2\2\2=>\7B\2\2"+
		">?\7o\2\2?@\7g\2\2@A\7o\2\2AB\7d\2\2BC\7g\2\2CD\7t\2\2DE\7u\2\2EF\7}\2"+
		"\2FJ\3\2\2\2GI\13\2\2\2HG\3\2\2\2IL\3\2\2\2JK\3\2\2\2JH\3\2\2\2KM\3\2"+
		"\2\2LJ\3\2\2\2MN\7\177\2\2NO\5\67\34\2O\6\3\2\2\2PQ\7B\2\2QR\7j\2\2RS"+
		"\7g\2\2ST\7c\2\2TU\7f\2\2UV\7g\2\2VW\7t\2\2WX\7}\2\2X\\\3\2\2\2Y[\13\2"+
		"\2\2ZY\3\2\2\2[^\3\2\2\2\\]\3\2\2\2\\Z\3\2\2\2]_\3\2\2\2^\\\3\2\2\2_`"+
		"\7\177\2\2`a\5\67\34\2a\b\3\2\2\2bc\7)\2\2cd\7\"\2\2de\7)\2\2e\n\3\2\2"+
		"\2f\177\7}\2\2g~\5\13\6\2hi\7\61\2\2ij\7,\2\2jn\3\2\2\2km\13\2\2\2lk\3"+
		"\2\2\2mp\3\2\2\2no\3\2\2\2nl\3\2\2\2oq\3\2\2\2pn\3\2\2\2qr\7,\2\2r~\7"+
		"\61\2\2st\7\61\2\2tu\7\61\2\2uy\3\2\2\2vx\n\2\2\2wv\3\2\2\2x{\3\2\2\2"+
		"yw\3\2\2\2yz\3\2\2\2z~\3\2\2\2{y\3\2\2\2|~\13\2\2\2}g\3\2\2\2}h\3\2\2"+
		"\2}s\3\2\2\2}|\3\2\2\2~\u0081\3\2\2\2\177\u0080\3\2\2\2\177}\3\2\2\2\u0080"+
		"\u0083\3\2\2\2\u0081\177\3\2\2\2\u0082\u0084\t\3\2\2\u0083\u0082\3\2\2"+
		"\2\u0084\f\3\2\2\2\u0085\u0086\7i\2\2\u0086\u0087\7t\2\2\u0087\u0088\7"+
		"c\2\2\u0088\u0089\7o\2\2\u0089\u008a\7o\2\2\u008a\u008b\7c\2\2\u008b\u008c"+
		"\7t\2\2\u008c\16\3\2\2\2\u008d\u008e\7<\2\2\u008e\20\3\2\2\2\u008f\u0090"+
		"\7~\2\2\u0090\22\3\2\2\2\u0091\u0092\7=\2\2\u0092\24\3\2\2\2\u0093\u0094"+
		"\7)\2\2\u0094\26\3\2\2\2\u0095\u0096\7\60\2\2\u0096\30\3\2\2\2\u0097\u0098"+
		"\7}\2\2\u0098\32\3\2\2\2\u0099\u009a\7\177\2\2\u009a\34\3\2\2\2\u009b"+
		"\u009c\7+\2\2\u009c\36\3\2\2\2\u009d\u009e\7*\2\2\u009e \3\2\2\2\u009f"+
		"\u00a0\7-\2\2\u00a0\"\3\2\2\2\u00a1\u00a2\7,\2\2\u00a2$\3\2\2\2\u00a3"+
		"\u00a4\7A\2\2\u00a4&\3\2\2\2\u00a5\u00a6\7]\2\2\u00a6(\3\2\2\2\u00a7\u00a8"+
		"\7_\2\2\u00a8*\3\2\2\2\u00a9\u00aa\7t\2\2\u00aa\u00ab\7g\2\2\u00ab\u00ac"+
		"\7v\2\2\u00ac\u00ad\7w\2\2\u00ad\u00ae\7t\2\2\u00ae\u00af\7p\2\2\u00af"+
		"\u00b0\7u\2\2\u00b0,\3\2\2\2\u00b1\u00b2\7?\2\2\u00b2.\3\2\2\2\u00b3\u00b4"+
		"\7.\2\2\u00b4\60\3\2\2\2\u00b5\u00b9\t\4\2\2\u00b6\u00b8\t\5\2\2\u00b7"+
		"\u00b6\3\2\2\2\u00b8\u00bb\3\2\2\2\u00b9\u00b7\3\2\2\2\u00b9\u00ba\3\2"+
		"\2\2\u00ba\62\3\2\2\2\u00bb\u00b9\3\2\2\2\u00bc\u00bd\7\17\2\2\u00bd\u00be"+
		"\3\2\2\2\u00be\u00bf\b\32\2\2\u00bf\64\3\2\2\2\u00c0\u00c1\7\f\2\2\u00c1"+
		"\u00c2\3\2\2\2\u00c2\u00c3\b\33\2\2\u00c3\66\3\2\2\2\u00c4\u00c5\t\6\2"+
		"\2\u00c5\u00c6\3\2\2\2\u00c6\u00c7\b\34\2\2\u00c78\3\2\2\2\u00c8\u00c9"+
		"\13\2\2\2\u00c9:\3\2\2\2\13\2J\\ny}\177\u0083\u00b9\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}