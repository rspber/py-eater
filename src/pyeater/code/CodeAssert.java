package pyeater.code;

import pyeater.value.Value;

public class CodeAssert extends Value {

	final Value expr;
	final Value msg;

	public CodeAssert(final Value expr, final Value msg) {
		this.expr = expr;
		this.msg = msg;
	}

}
