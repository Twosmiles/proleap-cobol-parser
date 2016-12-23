/*
 * Copyright (C) 2016, Ulrich Wolffgang <u.wol@wwu.de>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.procedure.merge.impl;

import java.util.ArrayList;
import java.util.List;

import io.proleap.cobol.Cobol85Parser.MergeGivingContext;
import io.proleap.cobol.Cobol85Parser.MergeGivingPhraseContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.call.Call;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;
import io.proleap.cobol.asg.metamodel.procedure.merge.Giving;
import io.proleap.cobol.asg.metamodel.procedure.merge.Givings;

public class GivingsImpl extends CobolDivisionElementImpl implements Givings {

	protected final MergeGivingPhraseContext ctx;

	protected List<Giving> givings = new ArrayList<Giving>();

	public GivingsImpl(final ProgramUnit programUnit, final MergeGivingPhraseContext ctx) {
		super(programUnit, ctx);

		this.ctx = ctx;
	}

	@Override
	public Giving addGiving(final MergeGivingContext ctx) {
		Giving result = (Giving) getASGElement(ctx);

		if (result == null) {
			result = new GivingImpl(programUnit, ctx);

			// file call
			final Call fileCall = createCall(ctx.fileName());
			result.setFileCall(fileCall);

			// close procedure
			final Giving.CloseProcedure closeProcedure;

			if (ctx.WITH() != null && ctx.REMOVE() != null && ctx.CRUNCH() != null) {
				closeProcedure = Giving.CloseProcedure.WITH_REMOVE_CRUNCH;
			} else if (ctx.NO() != null && ctx.REWIND() != null) {
				closeProcedure = Giving.CloseProcedure.NO_REWIND;
			} else if (ctx.LOCK() != null) {
				closeProcedure = Giving.CloseProcedure.LOCK;
			} else if (ctx.SAVE() != null) {
				closeProcedure = Giving.CloseProcedure.SAVE;
			} else if (ctx.RELEASE() != null) {
				closeProcedure = Giving.CloseProcedure.RELEASE;
			} else if (ctx.CRUNCH() != null) {
				closeProcedure = Giving.CloseProcedure.CRUNCH;
			} else {
				closeProcedure = null;
			}

			result.setCloseProcedure(closeProcedure);

			givings.add(result);
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public List<Giving> getGivings() {
		return givings;
	}

}
