package pyeater.code;

import java.util.List;

import pyeater.value.Value;

public class CodeIfThen extends Value {

	final Value expr;
	final Value[] code;

	public CodeIfThen(final Value expr, final List<Value> code) {
		this.expr = expr;
		this.code = code.toArray(new Value[code.size()]);
	}

}
