/**
 * 
 */
package com.github.newtonjose.aqlanalyzer.aqlparser;

import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.tree.TerminalNode;

import com.newtonjose.aqlparser.AqlBaseVisitor;
import com.newtonjose.aqlparser.AqlParser.AsIdentifierContext;
import com.newtonjose.aqlparser.AqlParser.ContainsContext;
import com.newtonjose.aqlparser.AqlParser.EhrContainsContext;
import com.newtonjose.aqlparser.AqlParser.FromContext;
import com.newtonjose.aqlparser.AqlParser.FromEHRContext;
import com.newtonjose.aqlparser.AqlParser.IdentifiedPathContext;
import com.newtonjose.aqlparser.AqlParser.IdentifiedPathSeqContext;
import com.newtonjose.aqlparser.AqlParser.OrderByContext;
import com.newtonjose.aqlparser.AqlParser.QueryContext;
import com.newtonjose.aqlparser.AqlParser.SelectContext;
import com.newtonjose.aqlparser.AqlParser.SelectVarContext;
import com.newtonjose.aqlparser.AqlParser.WhereContext;

/**
 * @author jacobo
 *
 */
public class AqlQueryVisitor extends AqlBaseVisitor<String> {

	String query = "";
	String select = "";
	String from = "";
	String where = "";
	String orderBy = "";

	@Override
	public String visitQuery(QueryContext ctx) {
		// super.visitQuery(ctx);

		// Need to visit from first
		this.visitFrom(ctx.from());
		this.visitSelect(ctx.select());
		if (ctx.where() != null) {
			this.visitWhere(ctx.where());
		}
		if (ctx.orderBy() != null) {
			this.visitOrderBy(ctx.orderBy());
		}

		query = (select + from + where + orderBy).trim() + ";";
		return query;
	}

	@Override
	public String visitSelect(SelectContext ctx) {
		select += "SELECT {";
		final String s = super.visitSelect(ctx);
		select += "} ";
		return s;
	}

	@Override
	public String visitIdentifiedPathSeq(IdentifiedPathSeqContext ctx) {
		this.visit(ctx.selectVar(0));

		for (int i = 1; i < ctx.selectVar().size(); i++) {
			select += ", ";
			this.visit(ctx.selectVar(i));
		}
		// return super.visitIdentifiedPathSeq(ctx);
		return defaultResult();
	}

	@Override
	public String visitSelectVar(SelectVarContext ctx) {
		ctx.identifiedPath();
		ctx.asIdentifier();
		return super.visitSelectVar(ctx);
	}

	@Override
	public String visitIdentifiedPath(IdentifiedPathContext ctx) {
		if (!mapa.containsKey(ctx.IDENTIFIER().getText())) {
			throw new RuntimeException(ctx.IDENTIFIER().getText()
					+ " is not in the FROM clause!");
		}
		select += ctx.IDENTIFIER();
		ctx.predicate(); // can we have a predicate in a selectVar?
		if (mapa.get(ctx.IDENTIFIER().getText()).equals("EHR")) {
			if (ctx.objectPath().pathPart(0).IDENTIFIER().getText()
					.equals("ehr_id")) {
				select += ".id";
			}
		}
		select += " ";
		// return super.visitIdentifiedPath(ctx);
		return defaultResult();
	}

	@Override
	public String visitAsIdentifier(AsIdentifierContext ctx) {
		select += "as " + ctx.IDENTIFIER().getText() + " ";
		return defaultResult();
	}

	@Override
	public String visitFrom(FromContext ctx) {
		from += "FROM ";
		return super.visitFrom(ctx);
	}

	@Override
	public String visitEhrContains(EhrContainsContext ctx) {
		this.visit(ctx.fromEHR());
		if (ctx.contains() != null) {
			from += "JOIN CDR_COMPOSITION c ON e.id = c.ehr_id ";
			this.visit(ctx.contains());
		}
		return defaultResult();
	}

	private Map<String, String> mapa = new HashMap<>();

	@Override
	public String visitFromEHR(FromEHRContext ctx) {
		from += "CDR_EHR ";
		if (ctx.IDENTIFIER() != null) {
			mapa.put(ctx.IDENTIFIER().getText(), "EHR");
			from += ctx.IDENTIFIER().getText() + " ";
		}
		return super.visitFromEHR(ctx);
	}

	@Override
	public String visitContains(ContainsContext ctx) {
		from += "CONTAINS ";
		return super.visitContains(ctx);
	}

	@Override
	public String visitWhere(WhereContext ctx) {
		where += "WHERE ";
		return super.visitWhere(ctx);
	}

	@Override
	public String visitOrderBy(OrderByContext ctx) {
		orderBy += "ORDER BY ";
		return super.visitOrderBy(ctx);
	}

	@Override
	public String visitTerminal(TerminalNode node) {
		// TODO Auto-generated method stub
		return node.getText();
	}

	@Override
	protected String aggregateResult(String aggregate, String nextResult) {
		StringBuilder sb = new StringBuilder(aggregate);
		sb.append(" ");
		sb.append(nextResult);

		return sb.toString();
	}

	@Override
	protected String defaultResult() {
		return "";
	}

}