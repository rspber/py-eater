package pyeater.value;

public class ValueRegExp extends Value {

	public final Value value;

	public ValueRegExp(final Value value) {
		super(CaseValue.ValueRegExp);
		this.value = value;
	}

	@Override
	public String toJava(final String pfx) {
		return value != null ? "r" + value.toJava(pfx) : "";
	}

}
