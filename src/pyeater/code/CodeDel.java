package pyeater.code;

import java.util.List;

import pyeater.value.Value;

public class CodeDel extends Value {

	final Value[] expr;
	
	public CodeDel(final List<Value> expr) {
		this.expr = expr.toArray(new Value[expr.size()]);
	}

}
