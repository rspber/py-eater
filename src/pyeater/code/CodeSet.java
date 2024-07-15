package pyeater.code;

import pyeater.value.Value;

public class CodeSet extends Value {

	final Value lvalue;
	final Value rvalue;
	
	public CodeSet(final Value lvalue, final Value rvalue) {
		this.lvalue = lvalue;
		this.rvalue = rvalue;
	}

}
