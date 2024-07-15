package pyeater.code;

import java.util.List;

import pyeater.value.Value;

public class CodeFor extends Value {

	final Value[] ffor;
	final Value[] expr;
	final Value[] code;
	final Value[] elso;

	public CodeFor(final List<Value> ffor, final List<Value> expr, final List<Value> code, final List<Value> elso) {
		this.ffor = ffor != null ? ffor.toArray(new Value[ffor.size()]) : null;
		this.expr = expr != null ? expr.toArray(new Value[expr.size()]) : null;
		this.code = code != null ? code.toArray(new Value[code.size()]) : null;
		this.elso = elso != null ? elso.toArray(new Value[elso.size()]) : null;
	}

}
