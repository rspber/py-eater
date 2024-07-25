package pyeater.value;

public class BackElvisFor extends Value {

	public final Value expr;
	public final Value elvisValue;

	public BackElvisFor(final Value expr, final Value elvisValue) {
		super(CaseValue.BackElvisFor);
		this.expr = expr;
		this.elvisValue = elvisValue;
	}

	@Override
	public String toJava(final String pfx) {
		return "for " + (expr != null ? expr.toJava(pfx) : "") + " in " +
				(elvisValue != null ? elvisValue.toJava(pfx) : "");
	}

}
