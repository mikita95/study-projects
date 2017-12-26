// Generated from src/GoodLanguage.g4 by ANTLR 4.5.3
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link GoodLanguageParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface GoodLanguageVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link GoodLanguageParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(GoodLanguageParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link GoodLanguageParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(GoodLanguageParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GoodLanguageParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(GoodLanguageParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link GoodLanguageParser#variables}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariables(GoodLanguageParser.VariablesContext ctx);
	/**
	 * Visit a parse tree produced by {@link GoodLanguageParser#lvalueblock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLvalueblock(GoodLanguageParser.LvalueblockContext ctx);
	/**
	 * Visit a parse tree produced by {@link GoodLanguageParser#lvalue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLvalue(GoodLanguageParser.LvalueContext ctx);
	/**
	 * Visit a parse tree produced by {@link GoodLanguageParser#rvalue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRvalue(GoodLanguageParser.RvalueContext ctx);
	/**
	 * Visit a parse tree produced by {@link GoodLanguageParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtom(GoodLanguageParser.AtomContext ctx);
	/**
	 * Visit a parse tree produced by {@link GoodLanguageParser#rvalueblock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRvalueblock(GoodLanguageParser.RvalueblockContext ctx);
	/**
	 * Visit a parse tree produced by {@link GoodLanguageParser#function}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction(GoodLanguageParser.FunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GoodLanguageParser#read}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRead(GoodLanguageParser.ReadContext ctx);
	/**
	 * Visit a parse tree produced by {@link GoodLanguageParser#write}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWrite(GoodLanguageParser.WriteContext ctx);
	/**
	 * Visit a parse tree produced by {@link GoodLanguageParser#writable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWritable(GoodLanguageParser.WritableContext ctx);
	/**
	 * Visit a parse tree produced by {@link GoodLanguageParser#line}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLine(GoodLanguageParser.LineContext ctx);
	/**
	 * Visit a parse tree produced by {@link GoodLanguageParser#body}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBody(GoodLanguageParser.BodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link GoodLanguageParser#forStructure}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStructure(GoodLanguageParser.ForStructureContext ctx);
	/**
	 * Visit a parse tree produced by {@link GoodLanguageParser#forAtom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForAtom(GoodLanguageParser.ForAtomContext ctx);
	/**
	 * Visit a parse tree produced by {@link GoodLanguageParser#forBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForBlock(GoodLanguageParser.ForBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link GoodLanguageParser#booleanExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanExpression(GoodLanguageParser.BooleanExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GoodLanguageParser#booleanTerm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanTerm(GoodLanguageParser.BooleanTermContext ctx);
	/**
	 * Visit a parse tree produced by {@link GoodLanguageParser#booleanAtom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanAtom(GoodLanguageParser.BooleanAtomContext ctx);
	/**
	 * Visit a parse tree produced by {@link GoodLanguageParser#compare}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompare(GoodLanguageParser.CompareContext ctx);
	/**
	 * Visit a parse tree produced by {@link GoodLanguageParser#whileStructure}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStructure(GoodLanguageParser.WhileStructureContext ctx);
	/**
	 * Visit a parse tree produced by {@link GoodLanguageParser#ifStructure}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStructure(GoodLanguageParser.IfStructureContext ctx);
	/**
	 * Visit a parse tree produced by {@link GoodLanguageParser#returnStructure}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStructure(GoodLanguageParser.ReturnStructureContext ctx);
	/**
	 * Visit a parse tree produced by {@link GoodLanguageParser#booleanValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanValue(GoodLanguageParser.BooleanValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link GoodLanguageParser#swap}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwap(GoodLanguageParser.SwapContext ctx);
	/**
	 * Visit a parse tree produced by {@link GoodLanguageParser#sqrt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSqrt(GoodLanguageParser.SqrtContext ctx);
	/**
	 * Visit a parse tree produced by {@link GoodLanguageParser#method}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethod(GoodLanguageParser.MethodContext ctx);
}