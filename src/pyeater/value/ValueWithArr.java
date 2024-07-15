package pyeater.value;

public class ValueWithArr extends Value {

	final Value name;
	final Value values;

	public ValueWithArr(final Value name, final Value values) {
		this.name = name;
		this.values = values;
	}
}
