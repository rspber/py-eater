package pyeater.code;

import java.util.List;

import pyeater.value.Value;

public class CodeTry extends Value {

	final Value[] code;
	final CodeExcept[] excepts;
	final Value[] finaly;

	public CodeTry(final List<Value> code, final List<CodeExcept> excepts, final List<Value> finaly) {
		this.code = code.toArray(new Value[code.size()]);
		this.excepts = excepts != null ? excepts.toArray(new CodeExcept[excepts.size()]) : null;
		this.finaly = finaly != null ? finaly.toArray(new Value[finaly.size()]) : null;
	}

}
