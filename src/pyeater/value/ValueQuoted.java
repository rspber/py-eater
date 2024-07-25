package pyeater.value;

public class ValueQuoted extends Value {

	public final String value;

	public ValueQuoted(final String q) {
		super(CaseValue.ValueQuoted);
		this.value = q;
	}

	@Override
	public String toJava(final String pfx) {
		return value != null ? "\"" + value + "\"" : "null";
	}

}
