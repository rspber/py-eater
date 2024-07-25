package pyeater.value;

public class ValueName extends Value {

	private String name;

	public void setName(final String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public ValueName(final CaseValue type, final String name) {
		super(type);
		this.name = name;
	}

	public ValueName(final String name) {
		super(CaseValue.ValueName);
		this.name = name;
	}

	@Override
	public String toJava(final String pfx) {
		return name;
	}

}
