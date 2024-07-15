package pyeater.code;

import java.util.List;

import pyeater.value.Value;

public class CodeFrom extends Value {

	final String[] from;
	final Value[] imps;
	
	public CodeFrom(final List<String> from, final List<Value> imps) {
		this.from = from.toArray(new String[from.size()]);
		this.imps = imps != null ? imps.toArray(new Value[imps.size()]) : null;
	}

}
