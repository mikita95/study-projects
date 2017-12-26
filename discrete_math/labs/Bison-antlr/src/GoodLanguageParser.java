// Generated from src/GoodLanguage.g4 by ANTLR 4.5.3
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GoodLanguageParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, DEF=8, FOR=9, 
		WHILE=10, ELSE=11, RETURN=12, SWAP=13, SQRT=14, IF=15, OR=16, AND=17, 
		NOT=18, L=19, LE=20, G=21, GE=22, E=23, NE=24, LCURLY=25, RCURLY=26, RD=27, 
		WR=28, WRL=29, VAR=30, PLUS=31, MINUS=32, MULT=33, DIV=34, PR=35, NUMBER=36, 
		LPAREN=37, RPAREN=38, NAME=39, COMMA=40, ASSIGN=41, SEMICOLON=42, WS=43, 
		NWR=44, NL=45;
	public static final int
		RULE_program = 0, RULE_statement = 1, RULE_assignment = 2, RULE_variables = 3, 
		RULE_lvalueblock = 4, RULE_lvalue = 5, RULE_rvalue = 6, RULE_atom = 7, 
		RULE_rvalueblock = 8, RULE_function = 9, RULE_read = 10, RULE_write = 11, 
		RULE_writable = 12, RULE_line = 13, RULE_body = 14, RULE_forStructure = 15, 
		RULE_forAtom = 16, RULE_forBlock = 17, RULE_booleanExpression = 18, RULE_booleanTerm = 19, 
		RULE_booleanAtom = 20, RULE_compare = 21, RULE_whileStructure = 22, RULE_ifStructure = 23, 
		RULE_returnStructure = 24, RULE_booleanValue = 25, RULE_swap = 26, RULE_sqrt = 27, 
		RULE_method = 28;
	public static final String[] ruleNames = {
		"program", "statement", "assignment", "variables", "lvalueblock", "lvalue", 
		"rvalue", "atom", "rvalueblock", "function", "read", "write", "writable", 
		"line", "body", "forStructure", "forAtom", "forBlock", "booleanExpression", 
		"booleanTerm", "booleanAtom", "compare", "whileStructure", "ifStructure", 
		"returnStructure", "booleanValue", "swap", "sqrt", "method"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'\"'", "'.'", "':'", "'\r'", "'..'", "'false'", "'true'", "'def'", 
		"'for'", "'while'", "'else'", "'return'", "'swap'", "'sqrt'", "'if'", 
		"'|'", "'&'", "'!'", "'<'", "'<='", "'>'", "'>='", "'=='", null, "'{'", 
		"'}'", "'>>'", "'<<'", "'<<<'", "'var'", "'+'", "'-'", "'*'", "'/'", "'%'", 
		null, "'('", "')'", null, "','", "'='", "';'", null, null, "'\n'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, "DEF", "FOR", "WHILE", 
		"ELSE", "RETURN", "SWAP", "SQRT", "IF", "OR", "AND", "NOT", "L", "LE", 
		"G", "GE", "E", "NE", "LCURLY", "RCURLY", "RD", "WR", "WRL", "VAR", "PLUS", 
		"MINUS", "MULT", "DIV", "PR", "NUMBER", "LPAREN", "RPAREN", "NAME", "COMMA", 
		"ASSIGN", "SEMICOLON", "WS", "NWR", "NL"
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

	@Override
	public String getGrammarFileName() { return "GoodLanguage.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public GoodLanguageParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgramContext extends ParserRuleContext {
		public List<MethodContext> method() {
			return getRuleContexts(MethodContext.class);
		}
		public MethodContext method(int i) {
			return getRuleContext(MethodContext.class,i);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).exitProgram(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoodLanguageVisitor ) return ((GoodLanguageVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(60); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(60);
				switch (_input.LA(1)) {
				case DEF:
					{
					setState(58);
					method();
					}
					break;
				case FOR:
				case WHILE:
				case RETURN:
				case SWAP:
				case IF:
				case RD:
				case WR:
				case WRL:
				case VAR:
				case NAME:
					{
					setState(59);
					statement();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(62); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << DEF) | (1L << FOR) | (1L << WHILE) | (1L << RETURN) | (1L << SWAP) | (1L << IF) | (1L << RD) | (1L << WR) | (1L << WRL) | (1L << VAR) | (1L << NAME))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public AssignmentContext assignment() {
			return getRuleContext(AssignmentContext.class,0);
		}
		public ForStructureContext forStructure() {
			return getRuleContext(ForStructureContext.class,0);
		}
		public WhileStructureContext whileStructure() {
			return getRuleContext(WhileStructureContext.class,0);
		}
		public ReadContext read() {
			return getRuleContext(ReadContext.class,0);
		}
		public WriteContext write() {
			return getRuleContext(WriteContext.class,0);
		}
		public FunctionContext function() {
			return getRuleContext(FunctionContext.class,0);
		}
		public VariablesContext variables() {
			return getRuleContext(VariablesContext.class,0);
		}
		public IfStructureContext ifStructure() {
			return getRuleContext(IfStructureContext.class,0);
		}
		public SwapContext swap() {
			return getRuleContext(SwapContext.class,0);
		}
		public ReturnStructureContext returnStructure() {
			return getRuleContext(ReturnStructureContext.class,0);
		}
		public List<TerminalNode> SEMICOLON() { return getTokens(GoodLanguageParser.SEMICOLON); }
		public TerminalNode SEMICOLON(int i) {
			return getToken(GoodLanguageParser.SEMICOLON, i);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoodLanguageVisitor ) return ((GoodLanguageVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_statement);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(74);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				{
				setState(64);
				assignment();
				}
				break;
			case 2:
				{
				setState(65);
				forStructure();
				}
				break;
			case 3:
				{
				setState(66);
				whileStructure();
				}
				break;
			case 4:
				{
				setState(67);
				read();
				}
				break;
			case 5:
				{
				setState(68);
				write();
				}
				break;
			case 6:
				{
				setState(69);
				function();
				}
				break;
			case 7:
				{
				setState(70);
				variables();
				}
				break;
			case 8:
				{
				setState(71);
				ifStructure();
				}
				break;
			case 9:
				{
				setState(72);
				swap();
				}
				break;
			case 10:
				{
				setState(73);
				returnStructure();
				}
				break;
			}
			setState(79);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(76);
					match(SEMICOLON);
					}
					} 
				}
				setState(81);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignmentContext extends ParserRuleContext {
		public LvalueblockContext lvalueblock() {
			return getRuleContext(LvalueblockContext.class,0);
		}
		public TerminalNode ASSIGN() { return getToken(GoodLanguageParser.ASSIGN, 0); }
		public RvalueblockContext rvalueblock() {
			return getRuleContext(RvalueblockContext.class,0);
		}
		public AssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).enterAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).exitAssignment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoodLanguageVisitor ) return ((GoodLanguageVisitor<? extends T>)visitor).visitAssignment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentContext assignment() throws RecognitionException {
		AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_assignment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(82);
			lvalueblock();
			setState(83);
			match(ASSIGN);
			setState(84);
			rvalueblock();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariablesContext extends ParserRuleContext {
		public TerminalNode VAR() { return getToken(GoodLanguageParser.VAR, 0); }
		public LvalueblockContext lvalueblock() {
			return getRuleContext(LvalueblockContext.class,0);
		}
		public AssignmentContext assignment() {
			return getRuleContext(AssignmentContext.class,0);
		}
		public VariablesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variables; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).enterVariables(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).exitVariables(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoodLanguageVisitor ) return ((GoodLanguageVisitor<? extends T>)visitor).visitVariables(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariablesContext variables() throws RecognitionException {
		VariablesContext _localctx = new VariablesContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_variables);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(86);
			match(VAR);
			setState(89);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				{
				setState(87);
				lvalueblock();
				}
				break;
			case 2:
				{
				setState(88);
				assignment();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LvalueblockContext extends ParserRuleContext {
		public List<LvalueContext> lvalue() {
			return getRuleContexts(LvalueContext.class);
		}
		public LvalueContext lvalue(int i) {
			return getRuleContext(LvalueContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(GoodLanguageParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GoodLanguageParser.COMMA, i);
		}
		public LvalueblockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lvalueblock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).enterLvalueblock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).exitLvalueblock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoodLanguageVisitor ) return ((GoodLanguageVisitor<? extends T>)visitor).visitLvalueblock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LvalueblockContext lvalueblock() throws RecognitionException {
		LvalueblockContext _localctx = new LvalueblockContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_lvalueblock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(91);
			lvalue();
			setState(96);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(92);
				match(COMMA);
				setState(93);
				lvalue();
				}
				}
				setState(98);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LvalueContext extends ParserRuleContext {
		public TerminalNode NAME() { return getToken(GoodLanguageParser.NAME, 0); }
		public LvalueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lvalue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).enterLvalue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).exitLvalue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoodLanguageVisitor ) return ((GoodLanguageVisitor<? extends T>)visitor).visitLvalue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LvalueContext lvalue() throws RecognitionException {
		LvalueContext _localctx = new LvalueContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_lvalue);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(99);
			match(NAME);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RvalueContext extends ParserRuleContext {
		public List<AtomContext> atom() {
			return getRuleContexts(AtomContext.class);
		}
		public AtomContext atom(int i) {
			return getRuleContext(AtomContext.class,i);
		}
		public List<TerminalNode> PLUS() { return getTokens(GoodLanguageParser.PLUS); }
		public TerminalNode PLUS(int i) {
			return getToken(GoodLanguageParser.PLUS, i);
		}
		public List<TerminalNode> MINUS() { return getTokens(GoodLanguageParser.MINUS); }
		public TerminalNode MINUS(int i) {
			return getToken(GoodLanguageParser.MINUS, i);
		}
		public List<TerminalNode> MULT() { return getTokens(GoodLanguageParser.MULT); }
		public TerminalNode MULT(int i) {
			return getToken(GoodLanguageParser.MULT, i);
		}
		public List<TerminalNode> DIV() { return getTokens(GoodLanguageParser.DIV); }
		public TerminalNode DIV(int i) {
			return getToken(GoodLanguageParser.DIV, i);
		}
		public List<TerminalNode> PR() { return getTokens(GoodLanguageParser.PR); }
		public TerminalNode PR(int i) {
			return getToken(GoodLanguageParser.PR, i);
		}
		public RvalueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rvalue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).enterRvalue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).exitRvalue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoodLanguageVisitor ) return ((GoodLanguageVisitor<? extends T>)visitor).visitRvalue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RvalueContext rvalue() throws RecognitionException {
		RvalueContext _localctx = new RvalueContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_rvalue);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(102);
			_la = _input.LA(1);
			if (_la==PLUS || _la==MINUS) {
				{
				setState(101);
				_la = _input.LA(1);
				if ( !(_la==PLUS || _la==MINUS) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				}
			}

			{
			setState(104);
			atom();
			setState(109);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MULT) | (1L << DIV) | (1L << PR))) != 0)) {
				{
				{
				setState(105);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MULT) | (1L << DIV) | (1L << PR))) != 0)) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(106);
				atom();
				}
				}
				setState(111);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
			setState(123);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==PLUS || _la==MINUS) {
				{
				{
				setState(112);
				_la = _input.LA(1);
				if ( !(_la==PLUS || _la==MINUS) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				{
				setState(113);
				atom();
				setState(118);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MULT) | (1L << DIV) | (1L << PR))) != 0)) {
					{
					{
					setState(114);
					_la = _input.LA(1);
					if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MULT) | (1L << DIV) | (1L << PR))) != 0)) ) {
					_errHandler.recoverInline(this);
					} else {
						consume();
					}
					setState(115);
					atom();
					}
					}
					setState(120);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				}
				}
				setState(125);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AtomContext extends ParserRuleContext {
		public TerminalNode NUMBER() { return getToken(GoodLanguageParser.NUMBER, 0); }
		public LvalueContext lvalue() {
			return getRuleContext(LvalueContext.class,0);
		}
		public FunctionContext function() {
			return getRuleContext(FunctionContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(GoodLanguageParser.LPAREN, 0); }
		public RvalueContext rvalue() {
			return getRuleContext(RvalueContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GoodLanguageParser.RPAREN, 0); }
		public SqrtContext sqrt() {
			return getRuleContext(SqrtContext.class,0);
		}
		public AtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atom; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).enterAtom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).exitAtom(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoodLanguageVisitor ) return ((GoodLanguageVisitor<? extends T>)visitor).visitAtom(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AtomContext atom() throws RecognitionException {
		AtomContext _localctx = new AtomContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_atom);
		try {
			setState(134);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(126);
				match(NUMBER);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(127);
				lvalue();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(128);
				function();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(129);
				match(LPAREN);
				setState(130);
				rvalue();
				setState(131);
				match(RPAREN);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(133);
				sqrt();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RvalueblockContext extends ParserRuleContext {
		public List<RvalueContext> rvalue() {
			return getRuleContexts(RvalueContext.class);
		}
		public RvalueContext rvalue(int i) {
			return getRuleContext(RvalueContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(GoodLanguageParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GoodLanguageParser.COMMA, i);
		}
		public RvalueblockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rvalueblock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).enterRvalueblock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).exitRvalueblock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoodLanguageVisitor ) return ((GoodLanguageVisitor<? extends T>)visitor).visitRvalueblock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RvalueblockContext rvalueblock() throws RecognitionException {
		RvalueblockContext _localctx = new RvalueblockContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_rvalueblock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(136);
			rvalue();
			setState(141);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(137);
				match(COMMA);
				setState(138);
				rvalue();
				}
				}
				setState(143);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionContext extends ParserRuleContext {
		public TerminalNode NAME() { return getToken(GoodLanguageParser.NAME, 0); }
		public TerminalNode LPAREN() { return getToken(GoodLanguageParser.LPAREN, 0); }
		public RvalueblockContext rvalueblock() {
			return getRuleContext(RvalueblockContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GoodLanguageParser.RPAREN, 0); }
		public FunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).enterFunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).exitFunction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoodLanguageVisitor ) return ((GoodLanguageVisitor<? extends T>)visitor).visitFunction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionContext function() throws RecognitionException {
		FunctionContext _localctx = new FunctionContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_function);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(144);
			match(NAME);
			setState(145);
			match(LPAREN);
			setState(146);
			rvalueblock();
			setState(147);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReadContext extends ParserRuleContext {
		public TerminalNode RD() { return getToken(GoodLanguageParser.RD, 0); }
		public LvalueblockContext lvalueblock() {
			return getRuleContext(LvalueblockContext.class,0);
		}
		public ReadContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_read; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).enterRead(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).exitRead(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoodLanguageVisitor ) return ((GoodLanguageVisitor<? extends T>)visitor).visitRead(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReadContext read() throws RecognitionException {
		ReadContext _localctx = new ReadContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_read);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(149);
			match(RD);
			setState(150);
			lvalueblock();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WriteContext extends ParserRuleContext {
		public List<WritableContext> writable() {
			return getRuleContexts(WritableContext.class);
		}
		public WritableContext writable(int i) {
			return getRuleContext(WritableContext.class,i);
		}
		public TerminalNode WR() { return getToken(GoodLanguageParser.WR, 0); }
		public TerminalNode WRL() { return getToken(GoodLanguageParser.WRL, 0); }
		public List<TerminalNode> COMMA() { return getTokens(GoodLanguageParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GoodLanguageParser.COMMA, i);
		}
		public WriteContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_write; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).enterWrite(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).exitWrite(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoodLanguageVisitor ) return ((GoodLanguageVisitor<? extends T>)visitor).visitWrite(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WriteContext write() throws RecognitionException {
		WriteContext _localctx = new WriteContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_write);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(152);
			_la = _input.LA(1);
			if ( !(_la==WR || _la==WRL) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(153);
			writable();
			setState(158);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(154);
				match(COMMA);
				setState(155);
				writable();
				}
				}
				setState(160);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WritableContext extends ParserRuleContext {
		public RvalueContext rvalue() {
			return getRuleContext(RvalueContext.class,0);
		}
		public LineContext line() {
			return getRuleContext(LineContext.class,0);
		}
		public WritableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_writable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).enterWritable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).exitWritable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoodLanguageVisitor ) return ((GoodLanguageVisitor<? extends T>)visitor).visitWritable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WritableContext writable() throws RecognitionException {
		WritableContext _localctx = new WritableContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_writable);
		try {
			setState(166);
			switch (_input.LA(1)) {
			case SQRT:
			case PLUS:
			case MINUS:
			case NUMBER:
			case LPAREN:
			case NAME:
				enterOuterAlt(_localctx, 1);
				{
				setState(161);
				rvalue();
				}
				break;
			case T__0:
				enterOuterAlt(_localctx, 2);
				{
				setState(162);
				match(T__0);
				setState(163);
				line();
				setState(164);
				match(T__0);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LineContext extends ParserRuleContext {
		public LineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_line; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).enterLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).exitLine(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoodLanguageVisitor ) return ((GoodLanguageVisitor<? extends T>)visitor).visitLine(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LineContext line() throws RecognitionException {
		LineContext _localctx = new LineContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_line);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(173);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__2) | (1L << T__4) | (1L << T__5) | (1L << T__6) | (1L << DEF) | (1L << FOR) | (1L << WHILE) | (1L << ELSE) | (1L << RETURN) | (1L << SWAP) | (1L << SQRT) | (1L << IF) | (1L << OR) | (1L << AND) | (1L << NOT) | (1L << L) | (1L << LE) | (1L << G) | (1L << GE) | (1L << E) | (1L << NE) | (1L << LCURLY) | (1L << RCURLY) | (1L << RD) | (1L << WR) | (1L << WRL) | (1L << VAR) | (1L << PLUS) | (1L << MINUS) | (1L << MULT) | (1L << DIV) | (1L << PR) | (1L << NUMBER) | (1L << LPAREN) | (1L << RPAREN) | (1L << NAME) | (1L << COMMA) | (1L << ASSIGN) | (1L << SEMICOLON) | (1L << WS) | (1L << NWR))) != 0)) {
				{
				setState(171);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
				case 1:
					{
					setState(168);
					match(T__1);
					}
					break;
				case 2:
					{
					setState(169);
					match(T__2);
					}
					break;
				case 3:
					{
					setState(170);
					_la = _input.LA(1);
					if ( _la <= 0 || ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__3) | (1L << NL))) != 0)) ) {
					_errHandler.recoverInline(this);
					} else {
						consume();
					}
					}
					break;
				}
				}
				setState(175);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BodyContext extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public TerminalNode LCURLY() { return getToken(GoodLanguageParser.LCURLY, 0); }
		public TerminalNode RCURLY() { return getToken(GoodLanguageParser.RCURLY, 0); }
		public BodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_body; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).enterBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).exitBody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoodLanguageVisitor ) return ((GoodLanguageVisitor<? extends T>)visitor).visitBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BodyContext body() throws RecognitionException {
		BodyContext _localctx = new BodyContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_body);
		int _la;
		try {
			setState(185);
			switch (_input.LA(1)) {
			case FOR:
			case WHILE:
			case RETURN:
			case SWAP:
			case IF:
			case RD:
			case WR:
			case WRL:
			case VAR:
			case NAME:
				enterOuterAlt(_localctx, 1);
				{
				setState(176);
				statement();
				}
				break;
			case LCURLY:
				enterOuterAlt(_localctx, 2);
				{
				setState(177);
				match(LCURLY);
				setState(181);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << FOR) | (1L << WHILE) | (1L << RETURN) | (1L << SWAP) | (1L << IF) | (1L << RD) | (1L << WR) | (1L << WRL) | (1L << VAR) | (1L << NAME))) != 0)) {
					{
					{
					setState(178);
					statement();
					}
					}
					setState(183);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(184);
				match(RCURLY);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ForStructureContext extends ParserRuleContext {
		public TerminalNode FOR() { return getToken(GoodLanguageParser.FOR, 0); }
		public TerminalNode LPAREN() { return getToken(GoodLanguageParser.LPAREN, 0); }
		public ForBlockContext forBlock() {
			return getRuleContext(ForBlockContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GoodLanguageParser.RPAREN, 0); }
		public BodyContext body() {
			return getRuleContext(BodyContext.class,0);
		}
		public ForStructureContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forStructure; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).enterForStructure(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).exitForStructure(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoodLanguageVisitor ) return ((GoodLanguageVisitor<? extends T>)visitor).visitForStructure(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForStructureContext forStructure() throws RecognitionException {
		ForStructureContext _localctx = new ForStructureContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_forStructure);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(187);
			match(FOR);
			setState(188);
			match(LPAREN);
			setState(189);
			forBlock();
			setState(190);
			match(RPAREN);
			setState(191);
			body();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ForAtomContext extends ParserRuleContext {
		public LvalueContext lvalue() {
			return getRuleContext(LvalueContext.class,0);
		}
		public List<RvalueContext> rvalue() {
			return getRuleContexts(RvalueContext.class);
		}
		public RvalueContext rvalue(int i) {
			return getRuleContext(RvalueContext.class,i);
		}
		public ForAtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forAtom; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).enterForAtom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).exitForAtom(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoodLanguageVisitor ) return ((GoodLanguageVisitor<? extends T>)visitor).visitForAtom(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForAtomContext forAtom() throws RecognitionException {
		ForAtomContext _localctx = new ForAtomContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_forAtom);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(193);
			lvalue();
			setState(194);
			match(T__2);
			setState(195);
			rvalue();
			setState(196);
			match(T__4);
			setState(197);
			rvalue();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ForBlockContext extends ParserRuleContext {
		public List<ForAtomContext> forAtom() {
			return getRuleContexts(ForAtomContext.class);
		}
		public ForAtomContext forAtom(int i) {
			return getRuleContext(ForAtomContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(GoodLanguageParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GoodLanguageParser.COMMA, i);
		}
		public ForBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).enterForBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).exitForBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoodLanguageVisitor ) return ((GoodLanguageVisitor<? extends T>)visitor).visitForBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForBlockContext forBlock() throws RecognitionException {
		ForBlockContext _localctx = new ForBlockContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_forBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(199);
			forAtom();
			setState(204);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(200);
				match(COMMA);
				setState(201);
				forAtom();
				}
				}
				setState(206);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BooleanExpressionContext extends ParserRuleContext {
		public List<BooleanTermContext> booleanTerm() {
			return getRuleContexts(BooleanTermContext.class);
		}
		public BooleanTermContext booleanTerm(int i) {
			return getRuleContext(BooleanTermContext.class,i);
		}
		public List<TerminalNode> AND() { return getTokens(GoodLanguageParser.AND); }
		public TerminalNode AND(int i) {
			return getToken(GoodLanguageParser.AND, i);
		}
		public List<TerminalNode> OR() { return getTokens(GoodLanguageParser.OR); }
		public TerminalNode OR(int i) {
			return getToken(GoodLanguageParser.OR, i);
		}
		public BooleanExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_booleanExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).enterBooleanExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).exitBooleanExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoodLanguageVisitor ) return ((GoodLanguageVisitor<? extends T>)visitor).visitBooleanExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BooleanExpressionContext booleanExpression() throws RecognitionException {
		BooleanExpressionContext _localctx = new BooleanExpressionContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_booleanExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(207);
			booleanTerm();
			setState(212);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OR || _la==AND) {
				{
				{
				setState(208);
				_la = _input.LA(1);
				if ( !(_la==OR || _la==AND) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(209);
				booleanTerm();
				}
				}
				setState(214);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BooleanTermContext extends ParserRuleContext {
		public BooleanAtomContext booleanAtom() {
			return getRuleContext(BooleanAtomContext.class,0);
		}
		public TerminalNode NOT() { return getToken(GoodLanguageParser.NOT, 0); }
		public BooleanTermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_booleanTerm; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).enterBooleanTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).exitBooleanTerm(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoodLanguageVisitor ) return ((GoodLanguageVisitor<? extends T>)visitor).visitBooleanTerm(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BooleanTermContext booleanTerm() throws RecognitionException {
		BooleanTermContext _localctx = new BooleanTermContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_booleanTerm);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(216);
			_la = _input.LA(1);
			if (_la==NOT) {
				{
				setState(215);
				match(NOT);
				}
			}

			setState(218);
			booleanAtom();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BooleanAtomContext extends ParserRuleContext {
		public BooleanValueContext booleanValue() {
			return getRuleContext(BooleanValueContext.class,0);
		}
		public CompareContext compare() {
			return getRuleContext(CompareContext.class,0);
		}
		public RvalueContext rvalue() {
			return getRuleContext(RvalueContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(GoodLanguageParser.LPAREN, 0); }
		public BooleanExpressionContext booleanExpression() {
			return getRuleContext(BooleanExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GoodLanguageParser.RPAREN, 0); }
		public BooleanAtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_booleanAtom; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).enterBooleanAtom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).exitBooleanAtom(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoodLanguageVisitor ) return ((GoodLanguageVisitor<? extends T>)visitor).visitBooleanAtom(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BooleanAtomContext booleanAtom() throws RecognitionException {
		BooleanAtomContext _localctx = new BooleanAtomContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_booleanAtom);
		try {
			setState(227);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(220);
				booleanValue();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(221);
				compare();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(222);
				rvalue();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(223);
				match(LPAREN);
				setState(224);
				booleanExpression();
				setState(225);
				match(RPAREN);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CompareContext extends ParserRuleContext {
		public List<RvalueContext> rvalue() {
			return getRuleContexts(RvalueContext.class);
		}
		public RvalueContext rvalue(int i) {
			return getRuleContext(RvalueContext.class,i);
		}
		public TerminalNode L() { return getToken(GoodLanguageParser.L, 0); }
		public TerminalNode G() { return getToken(GoodLanguageParser.G, 0); }
		public TerminalNode LE() { return getToken(GoodLanguageParser.LE, 0); }
		public TerminalNode GE() { return getToken(GoodLanguageParser.GE, 0); }
		public TerminalNode E() { return getToken(GoodLanguageParser.E, 0); }
		public TerminalNode NE() { return getToken(GoodLanguageParser.NE, 0); }
		public CompareContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compare; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).enterCompare(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).exitCompare(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoodLanguageVisitor ) return ((GoodLanguageVisitor<? extends T>)visitor).visitCompare(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CompareContext compare() throws RecognitionException {
		CompareContext _localctx = new CompareContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_compare);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(229);
			rvalue();
			setState(230);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << L) | (1L << LE) | (1L << G) | (1L << GE) | (1L << E) | (1L << NE))) != 0)) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(231);
			rvalue();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WhileStructureContext extends ParserRuleContext {
		public TerminalNode WHILE() { return getToken(GoodLanguageParser.WHILE, 0); }
		public TerminalNode LPAREN() { return getToken(GoodLanguageParser.LPAREN, 0); }
		public BooleanExpressionContext booleanExpression() {
			return getRuleContext(BooleanExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GoodLanguageParser.RPAREN, 0); }
		public BodyContext body() {
			return getRuleContext(BodyContext.class,0);
		}
		public WhileStructureContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whileStructure; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).enterWhileStructure(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).exitWhileStructure(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoodLanguageVisitor ) return ((GoodLanguageVisitor<? extends T>)visitor).visitWhileStructure(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhileStructureContext whileStructure() throws RecognitionException {
		WhileStructureContext _localctx = new WhileStructureContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_whileStructure);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(233);
			match(WHILE);
			setState(234);
			match(LPAREN);
			setState(235);
			booleanExpression();
			setState(236);
			match(RPAREN);
			setState(237);
			body();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IfStructureContext extends ParserRuleContext {
		public TerminalNode IF() { return getToken(GoodLanguageParser.IF, 0); }
		public TerminalNode LPAREN() { return getToken(GoodLanguageParser.LPAREN, 0); }
		public BooleanExpressionContext booleanExpression() {
			return getRuleContext(BooleanExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GoodLanguageParser.RPAREN, 0); }
		public List<BodyContext> body() {
			return getRuleContexts(BodyContext.class);
		}
		public BodyContext body(int i) {
			return getRuleContext(BodyContext.class,i);
		}
		public TerminalNode ELSE() { return getToken(GoodLanguageParser.ELSE, 0); }
		public IfStructureContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStructure; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).enterIfStructure(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).exitIfStructure(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoodLanguageVisitor ) return ((GoodLanguageVisitor<? extends T>)visitor).visitIfStructure(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfStructureContext ifStructure() throws RecognitionException {
		IfStructureContext _localctx = new IfStructureContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_ifStructure);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(239);
			match(IF);
			setState(240);
			match(LPAREN);
			setState(241);
			booleanExpression();
			setState(242);
			match(RPAREN);
			setState(243);
			body();
			setState(246);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
			case 1:
				{
				setState(244);
				match(ELSE);
				setState(245);
				body();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReturnStructureContext extends ParserRuleContext {
		public TerminalNode RETURN() { return getToken(GoodLanguageParser.RETURN, 0); }
		public RvalueContext rvalue() {
			return getRuleContext(RvalueContext.class,0);
		}
		public List<TerminalNode> SEMICOLON() { return getTokens(GoodLanguageParser.SEMICOLON); }
		public TerminalNode SEMICOLON(int i) {
			return getToken(GoodLanguageParser.SEMICOLON, i);
		}
		public ReturnStructureContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnStructure; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).enterReturnStructure(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).exitReturnStructure(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoodLanguageVisitor ) return ((GoodLanguageVisitor<? extends T>)visitor).visitReturnStructure(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnStructureContext returnStructure() throws RecognitionException {
		ReturnStructureContext _localctx = new ReturnStructureContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_returnStructure);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(248);
			match(RETURN);
			setState(249);
			rvalue();
			setState(253);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(250);
					match(SEMICOLON);
					}
					} 
				}
				setState(255);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BooleanValueContext extends ParserRuleContext {
		public BooleanValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_booleanValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).enterBooleanValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).exitBooleanValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoodLanguageVisitor ) return ((GoodLanguageVisitor<? extends T>)visitor).visitBooleanValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BooleanValueContext booleanValue() throws RecognitionException {
		BooleanValueContext _localctx = new BooleanValueContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_booleanValue);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(256);
			_la = _input.LA(1);
			if ( !(_la==T__5 || _la==T__6) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SwapContext extends ParserRuleContext {
		public TerminalNode SWAP() { return getToken(GoodLanguageParser.SWAP, 0); }
		public List<LvalueContext> lvalue() {
			return getRuleContexts(LvalueContext.class);
		}
		public LvalueContext lvalue(int i) {
			return getRuleContext(LvalueContext.class,i);
		}
		public TerminalNode COMMA() { return getToken(GoodLanguageParser.COMMA, 0); }
		public SwapContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_swap; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).enterSwap(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).exitSwap(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoodLanguageVisitor ) return ((GoodLanguageVisitor<? extends T>)visitor).visitSwap(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SwapContext swap() throws RecognitionException {
		SwapContext _localctx = new SwapContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_swap);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(258);
			match(SWAP);
			setState(259);
			lvalue();
			setState(260);
			match(COMMA);
			setState(261);
			lvalue();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SqrtContext extends ParserRuleContext {
		public TerminalNode SQRT() { return getToken(GoodLanguageParser.SQRT, 0); }
		public LvalueContext lvalue() {
			return getRuleContext(LvalueContext.class,0);
		}
		public SqrtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sqrt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).enterSqrt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).exitSqrt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoodLanguageVisitor ) return ((GoodLanguageVisitor<? extends T>)visitor).visitSqrt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SqrtContext sqrt() throws RecognitionException {
		SqrtContext _localctx = new SqrtContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_sqrt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(263);
			match(SQRT);
			setState(264);
			match(LPAREN);
			setState(265);
			lvalue();
			setState(266);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MethodContext extends ParserRuleContext {
		public TerminalNode DEF() { return getToken(GoodLanguageParser.DEF, 0); }
		public TerminalNode NAME() { return getToken(GoodLanguageParser.NAME, 0); }
		public TerminalNode LPAREN() { return getToken(GoodLanguageParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(GoodLanguageParser.RPAREN, 0); }
		public BodyContext body() {
			return getRuleContext(BodyContext.class,0);
		}
		public LvalueblockContext lvalueblock() {
			return getRuleContext(LvalueblockContext.class,0);
		}
		public MethodContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_method; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).enterMethod(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GoodLanguageListener ) ((GoodLanguageListener)listener).exitMethod(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GoodLanguageVisitor ) return ((GoodLanguageVisitor<? extends T>)visitor).visitMethod(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodContext method() throws RecognitionException {
		MethodContext _localctx = new MethodContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_method);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(268);
			match(DEF);
			setState(269);
			match(NAME);
			setState(270);
			match(LPAREN);
			setState(272);
			_la = _input.LA(1);
			if (_la==NAME) {
				{
				setState(271);
				lvalueblock();
				}
			}

			setState(274);
			match(RPAREN);
			setState(275);
			body();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3/\u0118\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\3\2\3\2\6\2?\n\2\r"+
		"\2\16\2@\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3M\n\3\3\3\7\3P\n\3"+
		"\f\3\16\3S\13\3\3\4\3\4\3\4\3\4\3\5\3\5\3\5\5\5\\\n\5\3\6\3\6\3\6\7\6"+
		"a\n\6\f\6\16\6d\13\6\3\7\3\7\3\b\5\bi\n\b\3\b\3\b\3\b\7\bn\n\b\f\b\16"+
		"\bq\13\b\3\b\3\b\3\b\3\b\7\bw\n\b\f\b\16\bz\13\b\7\b|\n\b\f\b\16\b\177"+
		"\13\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5\t\u0089\n\t\3\n\3\n\3\n\7\n\u008e"+
		"\n\n\f\n\16\n\u0091\13\n\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\r\3\r"+
		"\3\r\3\r\7\r\u009f\n\r\f\r\16\r\u00a2\13\r\3\16\3\16\3\16\3\16\3\16\5"+
		"\16\u00a9\n\16\3\17\3\17\3\17\7\17\u00ae\n\17\f\17\16\17\u00b1\13\17\3"+
		"\20\3\20\3\20\7\20\u00b6\n\20\f\20\16\20\u00b9\13\20\3\20\5\20\u00bc\n"+
		"\20\3\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\23\3"+
		"\23\3\23\7\23\u00cd\n\23\f\23\16\23\u00d0\13\23\3\24\3\24\3\24\7\24\u00d5"+
		"\n\24\f\24\16\24\u00d8\13\24\3\25\5\25\u00db\n\25\3\25\3\25\3\26\3\26"+
		"\3\26\3\26\3\26\3\26\3\26\5\26\u00e6\n\26\3\27\3\27\3\27\3\27\3\30\3\30"+
		"\3\30\3\30\3\30\3\30\3\31\3\31\3\31\3\31\3\31\3\31\3\31\5\31\u00f9\n\31"+
		"\3\32\3\32\3\32\7\32\u00fe\n\32\f\32\16\32\u0101\13\32\3\33\3\33\3\34"+
		"\3\34\3\34\3\34\3\34\3\35\3\35\3\35\3\35\3\35\3\36\3\36\3\36\3\36\5\36"+
		"\u0113\n\36\3\36\3\36\3\36\3\36\2\2\37\2\4\6\b\n\f\16\20\22\24\26\30\32"+
		"\34\36 \"$&(*,.\60\62\64\668:\2\t\3\2!\"\3\2#%\3\2\36\37\5\2\3\3\6\6/"+
		"/\3\2\22\23\3\2\25\32\3\2\b\t\u0121\2>\3\2\2\2\4L\3\2\2\2\6T\3\2\2\2\b"+
		"X\3\2\2\2\n]\3\2\2\2\fe\3\2\2\2\16h\3\2\2\2\20\u0088\3\2\2\2\22\u008a"+
		"\3\2\2\2\24\u0092\3\2\2\2\26\u0097\3\2\2\2\30\u009a\3\2\2\2\32\u00a8\3"+
		"\2\2\2\34\u00af\3\2\2\2\36\u00bb\3\2\2\2 \u00bd\3\2\2\2\"\u00c3\3\2\2"+
		"\2$\u00c9\3\2\2\2&\u00d1\3\2\2\2(\u00da\3\2\2\2*\u00e5\3\2\2\2,\u00e7"+
		"\3\2\2\2.\u00eb\3\2\2\2\60\u00f1\3\2\2\2\62\u00fa\3\2\2\2\64\u0102\3\2"+
		"\2\2\66\u0104\3\2\2\28\u0109\3\2\2\2:\u010e\3\2\2\2<?\5:\36\2=?\5\4\3"+
		"\2><\3\2\2\2>=\3\2\2\2?@\3\2\2\2@>\3\2\2\2@A\3\2\2\2A\3\3\2\2\2BM\5\6"+
		"\4\2CM\5 \21\2DM\5.\30\2EM\5\26\f\2FM\5\30\r\2GM\5\24\13\2HM\5\b\5\2I"+
		"M\5\60\31\2JM\5\66\34\2KM\5\62\32\2LB\3\2\2\2LC\3\2\2\2LD\3\2\2\2LE\3"+
		"\2\2\2LF\3\2\2\2LG\3\2\2\2LH\3\2\2\2LI\3\2\2\2LJ\3\2\2\2LK\3\2\2\2MQ\3"+
		"\2\2\2NP\7,\2\2ON\3\2\2\2PS\3\2\2\2QO\3\2\2\2QR\3\2\2\2R\5\3\2\2\2SQ\3"+
		"\2\2\2TU\5\n\6\2UV\7+\2\2VW\5\22\n\2W\7\3\2\2\2X[\7 \2\2Y\\\5\n\6\2Z\\"+
		"\5\6\4\2[Y\3\2\2\2[Z\3\2\2\2\\\t\3\2\2\2]b\5\f\7\2^_\7*\2\2_a\5\f\7\2"+
		"`^\3\2\2\2ad\3\2\2\2b`\3\2\2\2bc\3\2\2\2c\13\3\2\2\2db\3\2\2\2ef\7)\2"+
		"\2f\r\3\2\2\2gi\t\2\2\2hg\3\2\2\2hi\3\2\2\2ij\3\2\2\2jo\5\20\t\2kl\t\3"+
		"\2\2ln\5\20\t\2mk\3\2\2\2nq\3\2\2\2om\3\2\2\2op\3\2\2\2p}\3\2\2\2qo\3"+
		"\2\2\2rs\t\2\2\2sx\5\20\t\2tu\t\3\2\2uw\5\20\t\2vt\3\2\2\2wz\3\2\2\2x"+
		"v\3\2\2\2xy\3\2\2\2y|\3\2\2\2zx\3\2\2\2{r\3\2\2\2|\177\3\2\2\2}{\3\2\2"+
		"\2}~\3\2\2\2~\17\3\2\2\2\177}\3\2\2\2\u0080\u0089\7&\2\2\u0081\u0089\5"+
		"\f\7\2\u0082\u0089\5\24\13\2\u0083\u0084\7\'\2\2\u0084\u0085\5\16\b\2"+
		"\u0085\u0086\7(\2\2\u0086\u0089\3\2\2\2\u0087\u0089\58\35\2\u0088\u0080"+
		"\3\2\2\2\u0088\u0081\3\2\2\2\u0088\u0082\3\2\2\2\u0088\u0083\3\2\2\2\u0088"+
		"\u0087\3\2\2\2\u0089\21\3\2\2\2\u008a\u008f\5\16\b\2\u008b\u008c\7*\2"+
		"\2\u008c\u008e\5\16\b\2\u008d\u008b\3\2\2\2\u008e\u0091\3\2\2\2\u008f"+
		"\u008d\3\2\2\2\u008f\u0090\3\2\2\2\u0090\23\3\2\2\2\u0091\u008f\3\2\2"+
		"\2\u0092\u0093\7)\2\2\u0093\u0094\7\'\2\2\u0094\u0095\5\22\n\2\u0095\u0096"+
		"\7(\2\2\u0096\25\3\2\2\2\u0097\u0098\7\35\2\2\u0098\u0099\5\n\6\2\u0099"+
		"\27\3\2\2\2\u009a\u009b\t\4\2\2\u009b\u00a0\5\32\16\2\u009c\u009d\7*\2"+
		"\2\u009d\u009f\5\32\16\2\u009e\u009c\3\2\2\2\u009f\u00a2\3\2\2\2\u00a0"+
		"\u009e\3\2\2\2\u00a0\u00a1\3\2\2\2\u00a1\31\3\2\2\2\u00a2\u00a0\3\2\2"+
		"\2\u00a3\u00a9\5\16\b\2\u00a4\u00a5\7\3\2\2\u00a5\u00a6\5\34\17\2\u00a6"+
		"\u00a7\7\3\2\2\u00a7\u00a9\3\2\2\2\u00a8\u00a3\3\2\2\2\u00a8\u00a4\3\2"+
		"\2\2\u00a9\33\3\2\2\2\u00aa\u00ae\7\4\2\2\u00ab\u00ae\7\5\2\2\u00ac\u00ae"+
		"\n\5\2\2\u00ad\u00aa\3\2\2\2\u00ad\u00ab\3\2\2\2\u00ad\u00ac\3\2\2\2\u00ae"+
		"\u00b1\3\2\2\2\u00af\u00ad\3\2\2\2\u00af\u00b0\3\2\2\2\u00b0\35\3\2\2"+
		"\2\u00b1\u00af\3\2\2\2\u00b2\u00bc\5\4\3\2\u00b3\u00b7\7\33\2\2\u00b4"+
		"\u00b6\5\4\3\2\u00b5\u00b4\3\2\2\2\u00b6\u00b9\3\2\2\2\u00b7\u00b5\3\2"+
		"\2\2\u00b7\u00b8\3\2\2\2\u00b8\u00ba\3\2\2\2\u00b9\u00b7\3\2\2\2\u00ba"+
		"\u00bc\7\34\2\2\u00bb\u00b2\3\2\2\2\u00bb\u00b3\3\2\2\2\u00bc\37\3\2\2"+
		"\2\u00bd\u00be\7\13\2\2\u00be\u00bf\7\'\2\2\u00bf\u00c0\5$\23\2\u00c0"+
		"\u00c1\7(\2\2\u00c1\u00c2\5\36\20\2\u00c2!\3\2\2\2\u00c3\u00c4\5\f\7\2"+
		"\u00c4\u00c5\7\5\2\2\u00c5\u00c6\5\16\b\2\u00c6\u00c7\7\7\2\2\u00c7\u00c8"+
		"\5\16\b\2\u00c8#\3\2\2\2\u00c9\u00ce\5\"\22\2\u00ca\u00cb\7*\2\2\u00cb"+
		"\u00cd\5\"\22\2\u00cc\u00ca\3\2\2\2\u00cd\u00d0\3\2\2\2\u00ce\u00cc\3"+
		"\2\2\2\u00ce\u00cf\3\2\2\2\u00cf%\3\2\2\2\u00d0\u00ce\3\2\2\2\u00d1\u00d6"+
		"\5(\25\2\u00d2\u00d3\t\6\2\2\u00d3\u00d5\5(\25\2\u00d4\u00d2\3\2\2\2\u00d5"+
		"\u00d8\3\2\2\2\u00d6\u00d4\3\2\2\2\u00d6\u00d7\3\2\2\2\u00d7\'\3\2\2\2"+
		"\u00d8\u00d6\3\2\2\2\u00d9\u00db\7\24\2\2\u00da\u00d9\3\2\2\2\u00da\u00db"+
		"\3\2\2\2\u00db\u00dc\3\2\2\2\u00dc\u00dd\5*\26\2\u00dd)\3\2\2\2\u00de"+
		"\u00e6\5\64\33\2\u00df\u00e6\5,\27\2\u00e0\u00e6\5\16\b\2\u00e1\u00e2"+
		"\7\'\2\2\u00e2\u00e3\5&\24\2\u00e3\u00e4\7(\2\2\u00e4\u00e6\3\2\2\2\u00e5"+
		"\u00de\3\2\2\2\u00e5\u00df\3\2\2\2\u00e5\u00e0\3\2\2\2\u00e5\u00e1\3\2"+
		"\2\2\u00e6+\3\2\2\2\u00e7\u00e8\5\16\b\2\u00e8\u00e9\t\7\2\2\u00e9\u00ea"+
		"\5\16\b\2\u00ea-\3\2\2\2\u00eb\u00ec\7\f\2\2\u00ec\u00ed\7\'\2\2\u00ed"+
		"\u00ee\5&\24\2\u00ee\u00ef\7(\2\2\u00ef\u00f0\5\36\20\2\u00f0/\3\2\2\2"+
		"\u00f1\u00f2\7\21\2\2\u00f2\u00f3\7\'\2\2\u00f3\u00f4\5&\24\2\u00f4\u00f5"+
		"\7(\2\2\u00f5\u00f8\5\36\20\2\u00f6\u00f7\7\r\2\2\u00f7\u00f9\5\36\20"+
		"\2\u00f8\u00f6\3\2\2\2\u00f8\u00f9\3\2\2\2\u00f9\61\3\2\2\2\u00fa\u00fb"+
		"\7\16\2\2\u00fb\u00ff\5\16\b\2\u00fc\u00fe\7,\2\2\u00fd\u00fc\3\2\2\2"+
		"\u00fe\u0101\3\2\2\2\u00ff\u00fd\3\2\2\2\u00ff\u0100\3\2\2\2\u0100\63"+
		"\3\2\2\2\u0101\u00ff\3\2\2\2\u0102\u0103\t\b\2\2\u0103\65\3\2\2\2\u0104"+
		"\u0105\7\17\2\2\u0105\u0106\5\f\7\2\u0106\u0107\7*\2\2\u0107\u0108\5\f"+
		"\7\2\u0108\67\3\2\2\2\u0109\u010a\7\20\2\2\u010a\u010b\7\'\2\2\u010b\u010c"+
		"\5\f\7\2\u010c\u010d\7(\2\2\u010d9\3\2\2\2\u010e\u010f\7\n\2\2\u010f\u0110"+
		"\7)\2\2\u0110\u0112\7\'\2\2\u0111\u0113\5\n\6\2\u0112\u0111\3\2\2\2\u0112"+
		"\u0113\3\2\2\2\u0113\u0114\3\2\2\2\u0114\u0115\7(\2\2\u0115\u0116\5\36"+
		"\20\2\u0116;\3\2\2\2\33>@LQ[bhox}\u0088\u008f\u00a0\u00a8\u00ad\u00af"+
		"\u00b7\u00bb\u00ce\u00d6\u00da\u00e5\u00f8\u00ff\u0112";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}