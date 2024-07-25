package pyeater.value;

public class Value3Dot extends Value {

	public Value3Dot() {
		super(CaseValue.Value3Dot);
	}

	@Override
	public String toJava(final String pfx) {
		return "...";
	}

}
