// Generated from src/main/antlr/Grammar.g4 by ANTLR 4.5.3
package main.antlr;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link GrammarParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface GrammarVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link GrammarParser#grammarSpec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGrammarSpec(GrammarParser.GrammarSpecContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#ruleSpec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRuleSpec(GrammarParser.RuleSpecContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#returnsSpec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnsSpec(GrammarParser.ReturnsSpecContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#oneReturn}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOneReturn(GrammarParser.OneReturnContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#body}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBody(GrammarParser.BodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(GrammarParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(GrammarParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#str}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStr(GrammarParser.StrContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#strChar}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStrChar(GrammarParser.StrCharContext ctx);
}