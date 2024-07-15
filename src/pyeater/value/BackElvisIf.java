package pyeater.value;

public class BackElvisIf extends Value {

	final Value elvisThen;
	final Value elvisElse;

	public BackElvisIf(final Value elvisThen, final Value elvisElse) {
		this.elvisThen = elvisThen;
		this.elvisElse = elvisElse;
	}
}
