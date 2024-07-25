package pyeater.value;

public class ValueNo extends Value {

	public ValueNo() {
		super(CaseValue.ValueNo);
	}

	@Override
	public String toJava(String pfx) {
		return ":";
	}

}
