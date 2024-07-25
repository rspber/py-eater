package pyeater.value;

public class ValueFormatted extends Value {

	public final Value value;

	public ValueFormatted(final Value value) {
		super(CaseValue.ValueFormatted);
		this.value = value;
	}

	@Override
	public String toJava(final String pfx) {
		return "f" + (value != null ? value.toJava(pfx) : "");
	}

}
