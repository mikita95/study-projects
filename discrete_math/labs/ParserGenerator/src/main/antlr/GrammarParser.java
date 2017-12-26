// Generated from src/main/antlr/Grammar.g4 by ANTLR 4.5.3
package main.antlr;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GrammarParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, MEMBERS=2, HEADER=3, QUOTEDWS=4, ACTION=5, GRAMMAR=6, COLON=7, 
		OR=8, SEMI=9, APO=10, DOT=11, LCU=12, RCU=13, RPAREN=14, LPAREN=15, PLUS=16, 
		AST=17, QUE=18, LSQ=19, RSQ=20, RETURNS=21, ASG=22, COMMA=23, ID=24, RE=25, 
		BR=26, WS=27, ANYCHAR=28;
	public static final int
		RULE_grammarSpec = 0, RULE_ruleSpec = 1, RULE_returnsSpec = 2, RULE_oneReturn = 3, 
		RULE_body = 4, RULE_literal = 5, RULE_block = 6, RULE_str = 7, RULE_strChar = 8;
	public static final String[] ruleNames = {
		"grammarSpec", "ruleSpec", "returnsSpec", "oneReturn", "body", "literal", 
		"block", "str", "strChar"
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

	@Override
	public String getGrammarFileName() { return "Grammar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public GrammarParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class GrammarSpecContext extends ParserRuleContext {
		public TerminalNode GRAMMAR() { return getToken(GrammarParser.GRAMMAR, 0); }
		public TerminalNode ID() { return getToken(GrammarParser.ID, 0); }
		public TerminalNode SEMI() { return getToken(GrammarParser.SEMI, 0); }
		public TerminalNode EOF() { return getToken(GrammarParser.EOF, 0); }
		public TerminalNode HEADER() { return getToken(GrammarParser.HEADER, 0); }
		public TerminalNode MEMBERS() { return getToken(GrammarParser.MEMBERS, 0); }
		public List<RuleSpecContext> ruleSpec() {
			return getRuleContexts(RuleSpecContext.class);
		}
		public RuleSpecContext ruleSpec(int i) {
			return getRuleContext(RuleSpecContext.class,i);
		}
		public GrammarSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_grammarSpec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterGrammarSpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitGrammarSpec(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitGrammarSpec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GrammarSpecContext grammarSpec() throws RecognitionException {
		GrammarSpecContext _localctx = new GrammarSpecContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_grammarSpec);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(18);
			match(GRAMMAR);
			setState(19);
			match(ID);
			setState(20);
			match(SEMI);
			setState(22);
			_la = _input.LA(1);
			if (_la==HEADER) {
				{
				setState(21);
				match(HEADER);
				}
			}

			setState(25);
			_la = _input.LA(1);
			if (_la==MEMBERS) {
				{
				setState(24);
				match(MEMBERS);
				}
			}

			setState(28); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(27);
				ruleSpec();
				}
				}
				setState(30); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==ID );
			setState(32);
			match(EOF);
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

	public static class RuleSpecContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(GrammarParser.ID, 0); }
		public TerminalNode COLON() { return getToken(GrammarParser.COLON, 0); }
		public List<BodyContext> body() {
			return getRuleContexts(BodyContext.class);
		}
		public BodyContext body(int i) {
			return getRuleContext(BodyContext.class,i);
		}
		public TerminalNode SEMI() { return getToken(GrammarParser.SEMI, 0); }
		public ReturnsSpecContext returnsSpec() {
			return getRuleContext(ReturnsSpecContext.class,0);
		}
		public List<TerminalNode> OR() { return getTokens(GrammarParser.OR); }
		public TerminalNode OR(int i) {
			return getToken(GrammarParser.OR, i);
		}
		public RuleSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ruleSpec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterRuleSpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitRuleSpec(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitRuleSpec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RuleSpecContext ruleSpec() throws RecognitionException {
		RuleSpecContext _localctx = new RuleSpecContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_ruleSpec);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(34);
			match(ID);
			setState(36);
			_la = _input.LA(1);
			if (_la==RETURNS) {
				{
				setState(35);
				returnsSpec();
				}
			}

			setState(38);
			match(COLON);
			setState(39);
			body();
			setState(44);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OR) {
				{
				{
				setState(40);
				match(OR);
				setState(41);
				body();
				}
				}
				setState(46);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(47);
			match(SEMI);
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

	public static class ReturnsSpecContext extends ParserRuleContext {
		public TerminalNode RETURNS() { return getToken(GrammarParser.RETURNS, 0); }
		public TerminalNode LSQ() { return getToken(GrammarParser.LSQ, 0); }
		public List<OneReturnContext> oneReturn() {
			return getRuleContexts(OneReturnContext.class);
		}
		public OneReturnContext oneReturn(int i) {
			return getRuleContext(OneReturnContext.class,i);
		}
		public TerminalNode RSQ() { return getToken(GrammarParser.RSQ, 0); }
		public List<TerminalNode> COMMA() { return getTokens(GrammarParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GrammarParser.COMMA, i);
		}
		public ReturnsSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnsSpec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterReturnsSpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitReturnsSpec(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitReturnsSpec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnsSpecContext returnsSpec() throws RecognitionException {
		ReturnsSpecContext _localctx = new ReturnsSpecContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_returnsSpec);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(49);
			match(RETURNS);
			setState(50);
			match(LSQ);
			setState(51);
			oneReturn();
			setState(56);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(52);
				match(COMMA);
				setState(53);
				oneReturn();
				}
				}
				setState(58);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(59);
			match(RSQ);
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

	public static class OneReturnContext extends ParserRuleContext {
		public Token type;
		public Token name;
		public List<TerminalNode> ID() { return getTokens(GrammarParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(GrammarParser.ID, i);
		}
		public OneReturnContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_oneReturn; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterOneReturn(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitOneReturn(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitOneReturn(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OneReturnContext oneReturn() throws RecognitionException {
		OneReturnContext _localctx = new OneReturnContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_oneReturn);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(61);
			((OneReturnContext)_localctx).type = match(ID);
			setState(62);
			((OneReturnContext)_localctx).name = match(ID);
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
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public List<TerminalNode> ID() { return getTokens(GrammarParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(GrammarParser.ID, i);
		}
		public BodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_body; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitBody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BodyContext body() throws RecognitionException {
		BodyContext _localctx = new BodyContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_body);
		int _la;
		try {
			setState(79);
			switch (_input.LA(1)) {
			case QUOTEDWS:
			case APO:
			case LPAREN:
				enterOuterAlt(_localctx, 1);
				{
				setState(64);
				literal(0);
				setState(66);
				_la = _input.LA(1);
				if (_la==ACTION) {
					{
					setState(65);
					block();
					}
				}

				}
				break;
			case ID:
				enterOuterAlt(_localctx, 2);
				{
				setState(69); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(68);
					match(ID);
					}
					}
					setState(71); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==ID );
				setState(74);
				_la = _input.LA(1);
				if (_la==ACTION) {
					{
					setState(73);
					block();
					}
				}

				}
				break;
			case ACTION:
			case OR:
			case SEMI:
				enterOuterAlt(_localctx, 3);
				{
				setState(77);
				_la = _input.LA(1);
				if (_la==ACTION) {
					{
					setState(76);
					block();
					}
				}

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

	public static class LiteralContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(GrammarParser.LPAREN, 0); }
		public List<LiteralContext> literal() {
			return getRuleContexts(LiteralContext.class);
		}
		public LiteralContext literal(int i) {
			return getRuleContext(LiteralContext.class,i);
		}
		public TerminalNode RPAREN() { return getToken(GrammarParser.RPAREN, 0); }
		public List<TerminalNode> APO() { return getTokens(GrammarParser.APO); }
		public TerminalNode APO(int i) {
			return getToken(GrammarParser.APO, i);
		}
		public StrContext str() {
			return getRuleContext(StrContext.class,0);
		}
		public StrCharContext strChar() {
			return getRuleContext(StrCharContext.class,0);
		}
		public TerminalNode QUOTEDWS() { return getToken(GrammarParser.QUOTEDWS, 0); }
		public List<TerminalNode> ANYCHAR() { return getTokens(GrammarParser.ANYCHAR); }
		public TerminalNode ANYCHAR(int i) {
			return getToken(GrammarParser.ANYCHAR, i);
		}
		public List<TerminalNode> DOT() { return getTokens(GrammarParser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(GrammarParser.DOT, i);
		}
		public TerminalNode OR() { return getToken(GrammarParser.OR, 0); }
		public List<TerminalNode> PLUS() { return getTokens(GrammarParser.PLUS); }
		public TerminalNode PLUS(int i) {
			return getToken(GrammarParser.PLUS, i);
		}
		public List<TerminalNode> AST() { return getTokens(GrammarParser.AST); }
		public TerminalNode AST(int i) {
			return getToken(GrammarParser.AST, i);
		}
		public List<TerminalNode> QUE() { return getTokens(GrammarParser.QUE); }
		public TerminalNode QUE(int i) {
			return getToken(GrammarParser.QUE, i);
		}
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		return literal(0);
	}

	private LiteralContext literal(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		LiteralContext _localctx = new LiteralContext(_ctx, _parentState);
		LiteralContext _prevctx = _localctx;
		int _startState = 10;
		enterRecursionRule(_localctx, 10, RULE_literal, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(103);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				{
				setState(82);
				match(LPAREN);
				setState(83);
				literal(0);
				setState(84);
				match(RPAREN);
				}
				break;
			case 2:
				{
				setState(86);
				match(APO);
				setState(87);
				str();
				setState(88);
				match(APO);
				}
				break;
			case 3:
				{
				setState(90);
				match(APO);
				setState(91);
				strChar();
				setState(92);
				match(APO);
				}
				break;
			case 4:
				{
				setState(94);
				match(QUOTEDWS);
				}
				break;
			case 5:
				{
				setState(95);
				match(APO);
				setState(96);
				match(ANYCHAR);
				setState(97);
				match(APO);
				setState(98);
				match(DOT);
				setState(99);
				match(DOT);
				setState(100);
				match(APO);
				setState(101);
				match(ANYCHAR);
				setState(102);
				match(APO);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(116);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(114);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
					case 1:
						{
						_localctx = new LiteralContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_literal);
						setState(105);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(106);
						match(OR);
						setState(107);
						literal(7);
						}
						break;
					case 2:
						{
						_localctx = new LiteralContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_literal);
						setState(108);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(110); 
						_errHandler.sync(this);
						_alt = 1;
						do {
							switch (_alt) {
							case 1:
								{
								{
								setState(109);
								_la = _input.LA(1);
								if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PLUS) | (1L << AST) | (1L << QUE))) != 0)) ) {
								_errHandler.recoverInline(this);
								} else {
									consume();
								}
								}
								}
								break;
							default:
								throw new NoViableAltException(this);
							}
							setState(112); 
							_errHandler.sync(this);
							_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
						} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
						}
						break;
					}
					} 
				}
				setState(118);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class BlockContext extends ParserRuleContext {
		public TerminalNode ACTION() { return getToken(GrammarParser.ACTION, 0); }
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_block);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(119);
			match(ACTION);
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

	public static class StrContext extends ParserRuleContext {
		public StrContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_str; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterStr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitStr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitStr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StrContext str() throws RecognitionException {
		StrContext _localctx = new StrContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_str);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(121);
			_la = _input.LA(1);
			if ( _la <= 0 || ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << APO) | (1L << RE) | (1L << BR))) != 0)) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(123); 
			_errHandler.sync(this);
			_alt = 1+1;
			do {
				switch (_alt) {
				case 1+1:
					{
					{
					setState(122);
					_la = _input.LA(1);
					if ( _la <= 0 || ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << APO) | (1L << RE) | (1L << BR))) != 0)) ) {
					_errHandler.recoverInline(this);
					} else {
						consume();
					}
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(125); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			} while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
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

	public static class StrCharContext extends ParserRuleContext {
		public StrCharContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_strChar; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterStrChar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitStrChar(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitStrChar(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StrCharContext strChar() throws RecognitionException {
		StrCharContext _localctx = new StrCharContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_strChar);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(129);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				{
				setState(127);
				_la = _input.LA(1);
				if ( _la <= 0 || ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << APO) | (1L << RE) | (1L << BR))) != 0)) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				}
				break;
			case 2:
				{
				setState(128);
				match(COLON);
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 5:
			return literal_sempred((LiteralContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean literal_sempred(LiteralContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 6);
		case 1:
			return precpred(_ctx, 7);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\36\u0086\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\3\2\3"+
		"\2\3\2\3\2\5\2\31\n\2\3\2\5\2\34\n\2\3\2\6\2\37\n\2\r\2\16\2 \3\2\3\2"+
		"\3\3\3\3\5\3\'\n\3\3\3\3\3\3\3\3\3\7\3-\n\3\f\3\16\3\60\13\3\3\3\3\3\3"+
		"\4\3\4\3\4\3\4\3\4\7\49\n\4\f\4\16\4<\13\4\3\4\3\4\3\5\3\5\3\5\3\6\3\6"+
		"\5\6E\n\6\3\6\6\6H\n\6\r\6\16\6I\3\6\5\6M\n\6\3\6\5\6P\n\6\5\6R\n\6\3"+
		"\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7"+
		"\3\7\3\7\3\7\3\7\5\7j\n\7\3\7\3\7\3\7\3\7\3\7\6\7q\n\7\r\7\16\7r\7\7u"+
		"\n\7\f\7\16\7x\13\7\3\b\3\b\3\t\3\t\6\t~\n\t\r\t\16\t\177\3\n\3\n\5\n"+
		"\u0084\n\n\3\n\3\177\3\f\13\2\4\6\b\n\f\16\20\22\2\4\3\2\22\24\5\2\3\3"+
		"\f\f\33\34\u0091\2\24\3\2\2\2\4$\3\2\2\2\6\63\3\2\2\2\b?\3\2\2\2\nQ\3"+
		"\2\2\2\fi\3\2\2\2\16y\3\2\2\2\20{\3\2\2\2\22\u0083\3\2\2\2\24\25\7\b\2"+
		"\2\25\26\7\32\2\2\26\30\7\13\2\2\27\31\7\5\2\2\30\27\3\2\2\2\30\31\3\2"+
		"\2\2\31\33\3\2\2\2\32\34\7\4\2\2\33\32\3\2\2\2\33\34\3\2\2\2\34\36\3\2"+
		"\2\2\35\37\5\4\3\2\36\35\3\2\2\2\37 \3\2\2\2 \36\3\2\2\2 !\3\2\2\2!\""+
		"\3\2\2\2\"#\7\2\2\3#\3\3\2\2\2$&\7\32\2\2%\'\5\6\4\2&%\3\2\2\2&\'\3\2"+
		"\2\2\'(\3\2\2\2()\7\t\2\2).\5\n\6\2*+\7\n\2\2+-\5\n\6\2,*\3\2\2\2-\60"+
		"\3\2\2\2.,\3\2\2\2./\3\2\2\2/\61\3\2\2\2\60.\3\2\2\2\61\62\7\13\2\2\62"+
		"\5\3\2\2\2\63\64\7\27\2\2\64\65\7\25\2\2\65:\5\b\5\2\66\67\7\31\2\2\67"+
		"9\5\b\5\28\66\3\2\2\29<\3\2\2\2:8\3\2\2\2:;\3\2\2\2;=\3\2\2\2<:\3\2\2"+
		"\2=>\7\26\2\2>\7\3\2\2\2?@\7\32\2\2@A\7\32\2\2A\t\3\2\2\2BD\5\f\7\2CE"+
		"\5\16\b\2DC\3\2\2\2DE\3\2\2\2ER\3\2\2\2FH\7\32\2\2GF\3\2\2\2HI\3\2\2\2"+
		"IG\3\2\2\2IJ\3\2\2\2JL\3\2\2\2KM\5\16\b\2LK\3\2\2\2LM\3\2\2\2MR\3\2\2"+
		"\2NP\5\16\b\2ON\3\2\2\2OP\3\2\2\2PR\3\2\2\2QB\3\2\2\2QG\3\2\2\2QO\3\2"+
		"\2\2R\13\3\2\2\2ST\b\7\1\2TU\7\21\2\2UV\5\f\7\2VW\7\20\2\2Wj\3\2\2\2X"+
		"Y\7\f\2\2YZ\5\20\t\2Z[\7\f\2\2[j\3\2\2\2\\]\7\f\2\2]^\5\22\n\2^_\7\f\2"+
		"\2_j\3\2\2\2`j\7\6\2\2ab\7\f\2\2bc\7\36\2\2cd\7\f\2\2de\7\r\2\2ef\7\r"+
		"\2\2fg\7\f\2\2gh\7\36\2\2hj\7\f\2\2iS\3\2\2\2iX\3\2\2\2i\\\3\2\2\2i`\3"+
		"\2\2\2ia\3\2\2\2jv\3\2\2\2kl\f\b\2\2lm\7\n\2\2mu\5\f\7\tnp\f\t\2\2oq\t"+
		"\2\2\2po\3\2\2\2qr\3\2\2\2rp\3\2\2\2rs\3\2\2\2su\3\2\2\2tk\3\2\2\2tn\3"+
		"\2\2\2ux\3\2\2\2vt\3\2\2\2vw\3\2\2\2w\r\3\2\2\2xv\3\2\2\2yz\7\7\2\2z\17"+
		"\3\2\2\2{}\n\3\2\2|~\n\3\2\2}|\3\2\2\2~\177\3\2\2\2\177\u0080\3\2\2\2"+
		"\177}\3\2\2\2\u0080\21\3\2\2\2\u0081\u0084\n\3\2\2\u0082\u0084\7\t\2\2"+
		"\u0083\u0081\3\2\2\2\u0083\u0082\3\2\2\2\u0084\23\3\2\2\2\23\30\33 &."+
		":DILOQirtv\177\u0083";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}