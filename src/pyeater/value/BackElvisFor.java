package pyeater.value;

public class BackElvisFor extends Value {

	final Value expr;
	final Value elvisValue;

	public BackElvisFor(final Value expr, final Value elvisValue) {
		this.expr = expr;
		this.elvisValue = elvisValue;
	}
}
