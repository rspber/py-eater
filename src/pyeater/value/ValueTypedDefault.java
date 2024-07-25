package pyeater.value;

public class ValueTypedDefault {

	public final Value value;
	public final Value type;;
	public final Value defualt;

	public ValueTypedDefault(final Value value, final Value type, final Value defualt) {
		this.value = value;
		this.type = type;
		this.defualt = defualt;
	}

	public String toJava(final String pfx) {
		return
			(type != null ? type.toJava(pfx) + " " : "") +
			(value != null ? value.toJava(pfx) : "EMPTY") +
			(defualt != null ? " = " + defualt.toJava(pfx) : "");
	}
}
