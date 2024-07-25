package pyeater.value;

import java.util.List;

import pyeater.util.ValueOut;

public class ValuesInArray extends Value {

	public final Value[] values;

	public ValuesInArray(final List<Value> values) {
		super(CaseValue.ValuesInArray);
		this.values = values != null ? values.toArray(new Value[values.size()]) : null;
	}

	@Override
	public String toJava(final String pfx) {
		return "[ " + ValueOut.toJava(pfx, values, ", ") + "]";
	}

}
