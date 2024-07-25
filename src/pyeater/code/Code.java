package pyeater.code;

public abstract class Code {

	public final CaseCode type;

	public abstract String toJava(final String pfx);

	public Code(final CaseCode type) {
		this.type = type;
	}

}
