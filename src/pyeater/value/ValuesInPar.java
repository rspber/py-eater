package pyeater.value;

import java.util.List;

import pyeater.util.ValueOut;

public class ValuesInPar extends Value {

	public final Value[] values;

	public ValuesInPar(final List<Value> list) {
		super(CaseValue.ValuesInPar);
		this.values = list.toArray(new Value[list.size()]);
	}

	@Override
	public String toJava(final String pfx) {
		return "(" + ValueOut.toJava(pfx, values, ", ") + ")";
	}

}
