package pyeater.value;

public class ValueJsonField extends Value {

	public final Value name;
	public final Value value;

	public ValueJsonField(final Value name, final Value value) {
		super(CaseValue.ValueJsonField);
		this.name = name;
		this.value = value;
	}

	@Override
	public String toJava(final String pfx) {
		return (name != null ? name.toJava(pfx) : "") + " : " + (value != null ? value.toJava(pfx) : "");
	}

}
