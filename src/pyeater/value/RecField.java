package pyeater.value;

public class RecField extends ValueName {

	final Value obj;

	public RecField(final Value obj, final String name) {
		super(name);
		this.obj = obj;
	}
}
