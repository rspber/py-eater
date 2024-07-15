package pyeater.code;

import pyeater.value.Value;

public class CodeRaise extends Value {

	final Value msg;
	
	public CodeRaise(final Value msg) {
		this.msg = msg;
	}

}
