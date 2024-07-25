package pyeater.value;

import java.util.List;

import pyeater.util.ValueOut;

public class ValueCall extends Value {

	public final Value name;
	public final ValueTypedDefault[] params;

	public ValueCall(final Value name, final List<ValueTypedDefault> params) {
		super(CaseValue.ValueCall);
		this.name = name;
		this.params = params != null ? params.toArray(new ValueTypedDefault[params.size()]) : null;
	}

	@Override
	public String toJava(final String pfx) {
		return (name != null ? name.toJava(pfx) : "EMPTY") + "(" + ValueOut.toJava(pfx, params) + ")";
	}

}
