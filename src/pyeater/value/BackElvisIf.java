package pyeater.value;

public class BackElvisIf extends Value {

	public final Value elvisThen;
	public final Value elvisElse;

	public BackElvisIf(final Value elvisThen, final Value elvisElse) {
		super(CaseValue.BackElvisIf);
		this.elvisThen = elvisThen;
		this.elvisElse = elvisElse;
	}

	@Override
	public String toJava(final String pfx) {
		return "if " + (elvisThen != null ? elvisThen.toJava(pfx) : "") +
			(elvisElse != null ? " else " + elvisElse.toJava(pfx) : "");
	}

}
