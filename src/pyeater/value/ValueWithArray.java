package pyeater.value;

import java.util.List;

import pyeater.util.ValueOut;

public class ValueWithArray extends Value {

	public final Value name;
	public final Value[] values;

	public ValueWithArray(final Value name, final List<Value> values) {
		super(CaseValue.ValueWithArray);
		this.name = name;
		this.values = values != null ? values.toArray(new Value[values.size()]) : null;
	}

	@Override
	public String toJava(final String pfx) {
		return (name != null ? name.toJava(pfx) : "EMPTY") + "[" + ValueOut.toJava(pfx, values, ", ") + "]";
	}

}
