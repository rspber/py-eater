package pyeater.value;

import java.util.List;

import pyeater.util.ValueOut;

public class Values extends Value {

	public final Value[] values;

	public Values(final List<Value> list) {
		super(CaseValue.Values);
		this.values = list.toArray(new Value[list.size()]);
	}

	@Override
	public String toJava(final String pfx) {
		return ValueOut.toJava(pfx, values, ", ");
	}

}
