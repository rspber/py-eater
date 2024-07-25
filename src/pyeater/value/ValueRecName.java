package pyeater.value;

public class ValueRecName extends ValueName {

	public final Value rec;

	public ValueRecName(final Value rec, final String name) {
		super(CaseValue.ValueRecName, name);
		this.rec = rec;
	}

	@Override
	public String toJava(final String pfx) {
		return (rec != null ? rec.toJava(pfx) + "." : "") + super.toJava(pfx);
	}
}
