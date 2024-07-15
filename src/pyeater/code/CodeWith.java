package pyeater.code;

import java.util.List;

import pyeater.value.Value;

public class CodeWith extends Value {

	final Value[] with;
	final Value[] code;

	public CodeWith(final List<Value> with, final List<Value> code) {
		this.with = with.toArray(new Value[with.size()]);
		this.code = code.toArray(new Value[code.size()]);
	}

}
