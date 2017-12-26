// Generated from src/GoodLanguage.g4 by ANTLR 4.5.3
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link GoodLanguageParser}.
 */
public interface GoodLanguageListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link GoodLanguageParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(GoodLanguageParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link GoodLanguageParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(GoodLanguageParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link GoodLanguageParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(GoodLanguageParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GoodLanguageParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(GoodLanguageParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GoodLanguageParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(GoodLanguageParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link GoodLanguageParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(GoodLanguageParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link GoodLanguageParser#variables}.
	 * @param ctx the parse tree
	 */
	void enterVariables(GoodLanguageParser.VariablesContext ctx);
	/**
	 * Exit a parse tree produced by {@link GoodLanguageParser#variables}.
	 * @param ctx the parse tree
	 */
	void exitVariables(GoodLanguageParser.VariablesContext ctx);
	/**
	 * Enter a parse tree produced by {@link GoodLanguageParser#lvalueblock}.
	 * @param ctx the parse tree
	 */
	void enterLvalueblock(GoodLanguageParser.LvalueblockContext ctx);
	/**
	 * Exit a parse tree produced by {@link GoodLanguageParser#lvalueblock}.
	 * @param ctx the parse tree
	 */
	void exitLvalueblock(GoodLanguageParser.LvalueblockContext ctx);
	/**
	 * Enter a parse tree produced by {@link GoodLanguageParser#lvalue}.
	 * @param ctx the parse tree
	 */
	void enterLvalue(GoodLanguageParser.LvalueContext ctx);
	/**
	 * Exit a parse tree produced by {@link GoodLanguageParser#lvalue}.
	 * @param ctx the parse tree
	 */
	void exitLvalue(GoodLanguageParser.LvalueContext ctx);
	/**
	 * Enter a parse tree produced by {@link GoodLanguageParser#rvalue}.
	 * @param ctx the parse tree
	 */
	void enterRvalue(GoodLanguageParser.RvalueContext ctx);
	/**
	 * Exit a parse tree produced by {@link GoodLanguageParser#rvalue}.
	 * @param ctx the parse tree
	 */
	void exitRvalue(GoodLanguageParser.RvalueContext ctx);
	/**
	 * Enter a parse tree produced by {@link GoodLanguageParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterAtom(GoodLanguageParser.AtomContext ctx);
	/**
	 * Exit a parse tree produced by {@link GoodLanguageParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitAtom(GoodLanguageParser.AtomContext ctx);
	/**
	 * Enter a parse tree produced by {@link GoodLanguageParser#rvalueblock}.
	 * @param ctx the parse tree
	 */
	void enterRvalueblock(GoodLanguageParser.RvalueblockContext ctx);
	/**
	 * Exit a parse tree produced by {@link GoodLanguageParser#rvalueblock}.
	 * @param ctx the parse tree
	 */
	void exitRvalueblock(GoodLanguageParser.RvalueblockContext ctx);
	/**
	 * Enter a parse tree produced by {@link GoodLanguageParser#function}.
	 * @param ctx the parse tree
	 */
	void enterFunction(GoodLanguageParser.FunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GoodLanguageParser#function}.
	 * @param ctx the parse tree
	 */
	void exitFunction(GoodLanguageParser.FunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GoodLanguageParser#read}.
	 * @param ctx the parse tree
	 */
	void enterRead(GoodLanguageParser.ReadContext ctx);
	/**
	 * Exit a parse tree produced by {@link GoodLanguageParser#read}.
	 * @param ctx the parse tree
	 */
	void exitRead(GoodLanguageParser.ReadContext ctx);
	/**
	 * Enter a parse tree produced by {@link GoodLanguageParser#write}.
	 * @param ctx the parse tree
	 */
	void enterWrite(GoodLanguageParser.WriteContext ctx);
	/**
	 * Exit a parse tree produced by {@link GoodLanguageParser#write}.
	 * @param ctx the parse tree
	 */
	void exitWrite(GoodLanguageParser.WriteContext ctx);
	/**
	 * Enter a parse tree produced by {@link GoodLanguageParser#writable}.
	 * @param ctx the parse tree
	 */
	void enterWritable(GoodLanguageParser.WritableContext ctx);
	/**
	 * Exit a parse tree produced by {@link GoodLanguageParser#writable}.
	 * @param ctx the parse tree
	 */
	void exitWritable(GoodLanguageParser.WritableContext ctx);
	/**
	 * Enter a parse tree produced by {@link GoodLanguageParser#line}.
	 * @param ctx the parse tree
	 */
	void enterLine(GoodLanguageParser.LineContext ctx);
	/**
	 * Exit a parse tree produced by {@link GoodLanguageParser#line}.
	 * @param ctx the parse tree
	 */
	void exitLine(GoodLanguageParser.LineContext ctx);
	/**
	 * Enter a parse tree produced by {@link GoodLanguageParser#body}.
	 * @param ctx the parse tree
	 */
	void enterBody(GoodLanguageParser.BodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link GoodLanguageParser#body}.
	 * @param ctx the parse tree
	 */
	void exitBody(GoodLanguageParser.BodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link GoodLanguageParser#forStructure}.
	 * @param ctx the parse tree
	 */
	void enterForStructure(GoodLanguageParser.ForStructureContext ctx);
	/**
	 * Exit a parse tree produced by {@link GoodLanguageParser#forStructure}.
	 * @param ctx the parse tree
	 */
	void exitForStructure(GoodLanguageParser.ForStructureContext ctx);
	/**
	 * Enter a parse tree produced by {@link GoodLanguageParser#forAtom}.
	 * @param ctx the parse tree
	 */
	void enterForAtom(GoodLanguageParser.ForAtomContext ctx);
	/**
	 * Exit a parse tree produced by {@link GoodLanguageParser#forAtom}.
	 * @param ctx the parse tree
	 */
	void exitForAtom(GoodLanguageParser.ForAtomContext ctx);
	/**
	 * Enter a parse tree produced by {@link GoodLanguageParser#forBlock}.
	 * @param ctx the parse tree
	 */
	void enterForBlock(GoodLanguageParser.ForBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link GoodLanguageParser#forBlock}.
	 * @param ctx the parse tree
	 */
	void exitForBlock(GoodLanguageParser.ForBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link GoodLanguageParser#booleanExpression}.
	 * @param ctx the parse tree
	 */
	void enterBooleanExpression(GoodLanguageParser.BooleanExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GoodLanguageParser#booleanExpression}.
	 * @param ctx the parse tree
	 */
	void exitBooleanExpression(GoodLanguageParser.BooleanExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GoodLanguageParser#booleanTerm}.
	 * @param ctx the parse tree
	 */
	void enterBooleanTerm(GoodLanguageParser.BooleanTermContext ctx);
	/**
	 * Exit a parse tree produced by {@link GoodLanguageParser#booleanTerm}.
	 * @param ctx the parse tree
	 */
	void exitBooleanTerm(GoodLanguageParser.BooleanTermContext ctx);
	/**
	 * Enter a parse tree produced by {@link GoodLanguageParser#booleanAtom}.
	 * @param ctx the parse tree
	 */
	void enterBooleanAtom(GoodLanguageParser.BooleanAtomContext ctx);
	/**
	 * Exit a parse tree produced by {@link GoodLanguageParser#booleanAtom}.
	 * @param ctx the parse tree
	 */
	void exitBooleanAtom(GoodLanguageParser.BooleanAtomContext ctx);
	/**
	 * Enter a parse tree produced by {@link GoodLanguageParser#compare}.
	 * @param ctx the parse tree
	 */
	void enterCompare(GoodLanguageParser.CompareContext ctx);
	/**
	 * Exit a parse tree produced by {@link GoodLanguageParser#compare}.
	 * @param ctx the parse tree
	 */
	void exitCompare(GoodLanguageParser.CompareContext ctx);
	/**
	 * Enter a parse tree produced by {@link GoodLanguageParser#whileStructure}.
	 * @param ctx the parse tree
	 */
	void enterWhileStructure(GoodLanguageParser.WhileStructureContext ctx);
	/**
	 * Exit a parse tree produced by {@link GoodLanguageParser#whileStructure}.
	 * @param ctx the parse tree
	 */
	void exitWhileStructure(GoodLanguageParser.WhileStructureContext ctx);
	/**
	 * Enter a parse tree produced by {@link GoodLanguageParser#ifStructure}.
	 * @param ctx the parse tree
	 */
	void enterIfStructure(GoodLanguageParser.IfStructureContext ctx);
	/**
	 * Exit a parse tree produced by {@link GoodLanguageParser#ifStructure}.
	 * @param ctx the parse tree
	 */
	void exitIfStructure(GoodLanguageParser.IfStructureContext ctx);
	/**
	 * Enter a parse tree produced by {@link GoodLanguageParser#returnStructure}.
	 * @param ctx the parse tree
	 */
	void enterReturnStructure(GoodLanguageParser.ReturnStructureContext ctx);
	/**
	 * Exit a parse tree produced by {@link GoodLanguageParser#returnStructure}.
	 * @param ctx the parse tree
	 */
	void exitReturnStructure(GoodLanguageParser.ReturnStructureContext ctx);
	/**
	 * Enter a parse tree produced by {@link GoodLanguageParser#booleanValue}.
	 * @param ctx the parse tree
	 */
	void enterBooleanValue(GoodLanguageParser.BooleanValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link GoodLanguageParser#booleanValue}.
	 * @param ctx the parse tree
	 */
	void exitBooleanValue(GoodLanguageParser.BooleanValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link GoodLanguageParser#swap}.
	 * @param ctx the parse tree
	 */
	void enterSwap(GoodLanguageParser.SwapContext ctx);
	/**
	 * Exit a parse tree produced by {@link GoodLanguageParser#swap}.
	 * @param ctx the parse tree
	 */
	void exitSwap(GoodLanguageParser.SwapContext ctx);
	/**
	 * Enter a parse tree produced by {@link GoodLanguageParser#sqrt}.
	 * @param ctx the parse tree
	 */
	void enterSqrt(GoodLanguageParser.SqrtContext ctx);
	/**
	 * Exit a parse tree produced by {@link GoodLanguageParser#sqrt}.
	 * @param ctx the parse tree
	 */
	void exitSqrt(GoodLanguageParser.SqrtContext ctx);
	/**
	 * Enter a parse tree produced by {@link GoodLanguageParser#method}.
	 * @param ctx the parse tree
	 */
	void enterMethod(GoodLanguageParser.MethodContext ctx);
	/**
	 * Exit a parse tree produced by {@link GoodLanguageParser#method}.
	 * @param ctx the parse tree
	 */
	void exitMethod(GoodLanguageParser.MethodContext ctx);
}