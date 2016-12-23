/*
 * Copyright (C) 2016, Ulrich Wolffgang <u.wol@wwu.de>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.procedure.inspect;

import io.proleap.cobol.asg.metamodel.CobolDivisionElement;
import io.proleap.cobol.asg.metamodel.call.Call;

public interface BeforeAfter extends CobolDivisionElement {

	enum Type {
		AFTER, BEFORE
	}

	Call getDataItemCall();

	Type getType();

	void setDataItemCall(Call dataItemCall);

	void setType(Type type);
}
