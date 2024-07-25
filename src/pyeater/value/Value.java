package pyeater.value;

public abstract class Value {

	public final CaseValue type;

	public abstract String toJava(final String pfx);

	public Value(final CaseValue type) {
		this.type = type;
	}

}
