package pyeater.code;

import java.util.List;

import pyeater.value.Value;

public class CodeClass extends Value {

	final String name;
	final Value[] ants;
	final Value[] code;

	public CodeClass(final String name, final List<Value> ants, final List<Value> code) {
		this.name = name;
		this.ants = ants.toArray(new Value[ants.size()]);
		this.code = code.toArray(new Value[code.size()]);
	}

}
