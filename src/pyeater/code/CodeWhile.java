package pyeater.code;

import java.util.List;

import pyeater.value.Value;

public class CodeWhile extends Value {

	final Value expr;
	final Value[] code;
	final Value[] elso;

	public CodeWhile(final Value expr, final List<Value> code, final List<Value> elso) {
		this.expr = expr;
		this.code = code.toArray(new Value[code.size()]);
		this.elso = elso != null ? elso.toArray(new Value[elso.size()]) : null;
	}

}
