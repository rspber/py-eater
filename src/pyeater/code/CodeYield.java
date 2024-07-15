package pyeater.code;

import pyeater.value.Value;

public class CodeYield extends Value {

	final Value expr;
	
	public CodeYield(final Value expr) {
		this.expr = expr;
	}

}
