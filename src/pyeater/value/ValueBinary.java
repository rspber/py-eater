package pyeater.value;

public class ValueBinary extends Value {

	public final Value value;

	public ValueBinary(final Value value) {
		super(CaseValue.ValueBinary);
		this.value = value;
	}

	@Override
	public String toJava(final String pfx) {
		return "b\\" + (value != null ? value.toJava(pfx) : "");
	}

}
