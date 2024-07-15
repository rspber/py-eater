package pyeater.code;

import java.util.List;

import pyeater.value.Value;

public class CodeImport extends Value {

	final Value[] imps;
	
	public CodeImport(final List<Value> imps) {
		this.imps = imps.toArray(new Value[imps.size()]);
	}

}
