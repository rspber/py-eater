package pyeater.code;

import java.util.List;

import pyeater.value.Value;

public class CodeAnnotation extends Value  {

	final Value name;
	final Value[] params;

	public CodeAnnotation(final Value name, final List<Value> params) {
		this.name = name;
		this.params = params != null ? params.toArray(new Value[params.size()]) : null;
	}

}
