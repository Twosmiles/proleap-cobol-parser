/*
 * Copyright (C) 2016, Ulrich Wolffgang <u.wol@wwu.de>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.data.report;

import java.util.List;

import io.proleap.cobol.Cobol85Parser.ReportGroupLineNumberClauseContext;
import io.proleap.cobol.Cobol85Parser.ReportGroupUsageClauseContext;
import io.proleap.cobol.asg.metamodel.CobolDivisionElement;
import io.proleap.cobol.asg.metamodel.Declaration;

public interface ReportGroupDescriptionEntry extends CobolDivisionElement, Declaration {

	enum Type {
		PRINTABLE, SINGLE, VERTICAL
	}

	UsageClause addGroupUsageClause(ReportGroupUsageClauseContext ctx);

	LineNumberClause addLineNumberClause(ReportGroupLineNumberClauseContext ctx);

	void addReportGroupDescriptionEntry(ReportGroupDescriptionEntry reportGroupDescriptionEntry);

	Integer getLevelNumber();

	LineNumberClause getLineNumberClause();

	ReportGroupDescriptionEntry getParentReportGroupDescriptionEntry();

	List<ReportGroupDescriptionEntry> getReportGroupDescriptionEntries();

	ReportGroupDescriptionEntry getReportGroupDescriptionEntry(String name);

	Type getType();

	UsageClause getUsageClause();

	void setLevelNumber(Integer levelNumber);

	void setParentReportGroupDescriptionEntry(ReportGroupDescriptionEntry parentReportGroupDescriptionEntry);

}
