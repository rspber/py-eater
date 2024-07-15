package pyeater.value;

public class ValueCall extends Value {

	final Value name;
	final Value params;

	public ValueCall(final Value name, final Value params) {
		this.name = name;
		this.params = params;
	}
}
