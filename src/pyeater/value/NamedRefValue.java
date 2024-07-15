package pyeater.value;

public class NamedRefValue {

	final int stars;
	final String name;
	final Value value;

	public NamedRefValue(final int stars, final String name, final Value value) {
		this.stars = stars;
		this.name = name;
		this.value = value;
	}
}
