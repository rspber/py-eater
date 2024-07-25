package pyeater.value;

public class AnyName {

	private String name;

	public void setName(final String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public AnyName(final String name) {
		this.name = name;
	}

	public int recLen() {
		return 0;
	}

	public boolean eqName(final String name) {
		return this.name.equals(name);
	}

//	@Override
	public String toJava() {
		return name;
	}

}
