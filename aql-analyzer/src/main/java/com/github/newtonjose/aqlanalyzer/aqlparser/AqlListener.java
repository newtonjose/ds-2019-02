/**
 * 
 */
package com.github.newtonjose.aqlanalyzer.aqlparser;


import com.newtonjose.aqlparser.AqlBaseListener;
import com.newtonjose.aqlparser.AqlParser.ArchetypedClassExprContext;
import com.newtonjose.aqlparser.AqlParser.ContainsContext;
import com.newtonjose.aqlparser.AqlParser.EhrContainsContext;
import com.newtonjose.aqlparser.AqlParser.FromContext;
import com.newtonjose.aqlparser.AqlParser.FromEHRContext;
import com.newtonjose.aqlparser.AqlParser.OrderByContext;
import com.newtonjose.aqlparser.AqlParser.SelectExprContext;
import com.newtonjose.aqlparser.AqlParser.SelectVarContext;
import com.newtonjose.aqlparser.AqlParser.SimpleClassExprContext;
import com.newtonjose.aqlparser.AqlParser.WhereContext;


/**
 * @author jacobo
 *
 */
public class AqlListener extends AqlBaseListener {

	/**
	 * 
	 */
	public AqlListener() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void enterSelectExpr(SelectExprContext ctx) {
		System.out.println("SELECT");
		super.enterSelectExpr(ctx);
	}

	@Override
	public void enterSelectVar(SelectVarContext ctx) {
		System.out.println("\tselectVar");
		super.enterSelectVar(ctx);
	}

	@Override
	public void enterFrom(FromContext ctx) {
		System.out.println("FROM");
		super.enterFrom(ctx);
	}

	@Override
	public void enterEhrContains(EhrContainsContext ctx) {
		System.out.println("\tEHR CONTAINS");
		super.enterEhrContains(ctx);
	}

	@Override
	public void enterFromEHR(FromEHRContext ctx) {
		System.out.println("\t\tFROM EHR: " + ctx.IDENTIFIER().getText());
		super.enterFromEHR(ctx);
	}

	@Override
	public void enterContains(ContainsContext ctx) {
		System.out.println("\t\tCONTAINS");
		super.enterContains(ctx);
	}

	@Override
	public void enterSimpleClassExpr(SimpleClassExprContext ctx) {
		System.out.println("\t\t\tSIMPLE CLASS EXPRESSION");
		super.enterSimpleClassExpr(ctx);
	}

	@Override
	public void enterArchetypedClassExpr(ArchetypedClassExprContext ctx) {
		System.out.println("\t\t\t\t" + ctx.IDENTIFIER(0));
		super.enterArchetypedClassExpr(ctx);
	}

	@Override
	public void enterWhere(WhereContext ctx) {
		System.out.println("WHERE");
		super.enterWhere(ctx);
	}

	@Override
	public void enterOrderBy(OrderByContext ctx) {
		System.out.println("ORDER BY");
		super.enterOrderBy(ctx);
	}

}
