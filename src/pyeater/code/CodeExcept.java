package pyeater.code;

import java.util.List;

import pyeater.value.Value;

public class CodeExcept {

	final Value[] excpts;
	final String as;
	final Value[] code;
	final Value[] elso;

	public CodeExcept(final List<Value> excpts, final String as, final List<Value> code, final List<Value> elso) {
		this.excpts = excpts.toArray(new Value[excpts.size()]);
		this.as = as;
		this.code = code.toArray(new Value[code.size()]);
		this.elso = elso != null ? elso.toArray(new Value[elso.size()]) : null;
	}

}
