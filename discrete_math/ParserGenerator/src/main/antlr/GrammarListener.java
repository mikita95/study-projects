// Generated from src/main/antlr/Grammar.g4 by ANTLR 4.5.3
package main.antlr;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link GrammarParser}.
 */
public interface GrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link GrammarParser#grammarSpec}.
	 * @param ctx the parse tree
	 */
	void enterGrammarSpec(GrammarParser.GrammarSpecContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#grammarSpec}.
	 * @param ctx the parse tree
	 */
	void exitGrammarSpec(GrammarParser.GrammarSpecContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#ruleSpec}.
	 * @param ctx the parse tree
	 */
	void enterRuleSpec(GrammarParser.RuleSpecContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#ruleSpec}.
	 * @param ctx the parse tree
	 */
	void exitRuleSpec(GrammarParser.RuleSpecContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#returnsSpec}.
	 * @param ctx the parse tree
	 */
	void enterReturnsSpec(GrammarParser.ReturnsSpecContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#returnsSpec}.
	 * @param ctx the parse tree
	 */
	void exitReturnsSpec(GrammarParser.ReturnsSpecContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#oneReturn}.
	 * @param ctx the parse tree
	 */
	void enterOneReturn(GrammarParser.OneReturnContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#oneReturn}.
	 * @param ctx the parse tree
	 */
	void exitOneReturn(GrammarParser.OneReturnContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#body}.
	 * @param ctx the parse tree
	 */
	void enterBody(GrammarParser.BodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#body}.
	 * @param ctx the parse tree
	 */
	void exitBody(GrammarParser.BodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(GrammarParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(GrammarParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(GrammarParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(GrammarParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#str}.
	 * @param ctx the parse tree
	 */
	void enterStr(GrammarParser.StrContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#str}.
	 * @param ctx the parse tree
	 */
	void exitStr(GrammarParser.StrContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#strChar}.
	 * @param ctx the parse tree
	 */
	void enterStrChar(GrammarParser.StrCharContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#strChar}.
	 * @param ctx the parse tree
	 */
	void exitStrChar(GrammarParser.StrCharContext ctx);
}