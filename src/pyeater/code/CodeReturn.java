package pyeater.code;

import java.util.List;

import pyeater.value.Value;

public class CodeReturn extends Value {

	final Value[] expr;
	
	public CodeReturn(final List<Value> expr) {
		this.expr = expr != null ? expr.toArray(new Value[expr.size()]): null;
	}

}
