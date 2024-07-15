package pyeater.code;

import java.util.List;

import pyeater.value.Value;

public class CodeClass extends Value {

	final String name;
	final Value[] exts;
	final Value[] code;

	public CodeClass(final String name, final List<Value> exts, final List<Value> code) {
		this.name = name;
		this.exts = exts.toArray(new Value[exts.size()]);
		this.code = code.toArray(new Value[code.size()]);
	}

}
