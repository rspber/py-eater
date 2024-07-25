package pyeater.value;

public class ValueNumber extends Value {

	public final String value;

	public ValueNumber(final String value) {
		super(CaseValue.ValueNumber);
		this.value = value;
	}

	@Override
	public String toJava(final String pfx) {
		return value;
	}

}
