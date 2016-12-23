/*
 * Copyright (C) 2016, Ulrich Wolffgang <u.wol@wwu.de>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.data.report.impl;

import io.proleap.cobol.Cobol85Parser.ReportGroupDescriptionEntryFormat1Context;
import io.proleap.cobol.Cobol85Parser.ReportGroupNextGroupClauseContext;
import io.proleap.cobol.Cobol85Parser.ReportGroupTypeClauseContext;
import io.proleap.cobol.asg.metamodel.IntegerLiteral;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.call.Call;
import io.proleap.cobol.asg.metamodel.data.report.NextGroupClause;
import io.proleap.cobol.asg.metamodel.data.report.ReportGroupDescriptionEntryVertical;
import io.proleap.cobol.asg.metamodel.data.report.TypeClause;

public class ReportGroupDescriptionEntryVerticalImpl extends ReportGroupDescriptionEntryImpl
		implements ReportGroupDescriptionEntryVertical {

	protected final ReportGroupDescriptionEntryFormat1Context ctx;

	protected NextGroupClause nextGroupClause;

	protected TypeClause typeClause;

	public ReportGroupDescriptionEntryVerticalImpl(final String name, final ProgramUnit programUnit,
			final ReportGroupDescriptionEntryFormat1Context ctx) {
		super(name, programUnit, ctx);

		this.ctx = ctx;
	}

	@Override
	public NextGroupClause addNextGroupClause(final ReportGroupNextGroupClauseContext ctx) {
		NextGroupClause result = (NextGroupClause) getASGElement(ctx);

		if (result == null) {
			result = new NextGroupClauseImpl(programUnit, ctx);

			/*
			 * type and integerLiteral
			 */
			final NextGroupClause.Type type;
			final IntegerLiteral integerLiteral;

			if (ctx.reportGroupNextGroupNextPage() != null) {
				type = NextGroupClause.Type.NEXT_PAGE;
				integerLiteral = null;
			} else if (ctx.reportGroupNextGroupPlus() != null) {
				type = NextGroupClause.Type.PLUS;
				integerLiteral = createIntegerLiteral(ctx.reportGroupNextGroupPlus().integerLiteral());
			} else {
				type = NextGroupClause.Type.ABSOLUTE;
				integerLiteral = createIntegerLiteral(ctx.integerLiteral());
			}

			result.setIntegerLiteral(integerLiteral);
			result.setType(type);

			nextGroupClause = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public TypeClause addTypeClause(final ReportGroupTypeClauseContext ctx) {
		TypeClause result = (TypeClause) getASGElement(ctx);

		if (result == null) {
			result = new TypeClauseImpl(programUnit, ctx);

			/*
			 * type and data
			 */
			final TypeClause.Type type;
			final Call dataCall;

			if (ctx.reportGroupTypeReportHeading() != null) {
				type = TypeClause.Type.REPORT_HEADING;
				dataCall = null;
			} else if (ctx.reportGroupTypePageHeading() != null) {
				type = TypeClause.Type.PAGE_HEADING;
				dataCall = null;
			} else if (ctx.reportGroupTypeControlHeading() != null) {
				type = TypeClause.Type.CONTROL_HEADING;
				dataCall = createCall(ctx.reportGroupTypeControlHeading().dataName());
			} else if (ctx.reportGroupTypeDetail() != null) {
				type = TypeClause.Type.DETAIL;
				dataCall = null;
			} else if (ctx.reportGroupTypeControlFooting() != null) {
				type = TypeClause.Type.CONTROL_FOOTING;
				dataCall = createCall(ctx.reportGroupTypeControlFooting().dataName());
			} else if (ctx.reportGroupTypePageFooting() != null) {
				type = TypeClause.Type.PAGE_FOOTING;
				dataCall = null;
			} else if (ctx.reportGroupTypeReportFooting() != null) {
				type = TypeClause.Type.REPORT_FOOTING;
				dataCall = null;
			} else {
				type = null;
				dataCall = null;
			}

			result.setType(type);
			result.setDataCall(dataCall);

			typeClause = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public NextGroupClause getNextGroupClause() {
		return nextGroupClause;
	}

	@Override
	public Type getType() {
		return Type.VERTICAL;
	}

	@Override
	public TypeClause getTypeClause() {
		return typeClause;
	}

}
