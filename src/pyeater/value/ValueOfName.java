package pyeater.value;

public class ValueOfName extends Value {

	public final AnyName name;

	public ValueOfName(final AnyName name) {
		super(CaseValue.ValueOfName);
		this.name = name;
	}

	@Override
	public String toJava(final String pfx) {
		return name != null ? name.toJava() : "";
	}

}
