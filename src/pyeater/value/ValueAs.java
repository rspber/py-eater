package pyeater.value;

import java.util.List;

public class ValueAs extends Value {

	public final Value value;
	public final AnyName[] as;

	public ValueAs(final Value value, final List<AnyName> as) {
		super(CaseValue.ValueAs);
		this.value = value;
		this.as = as != null ? as.toArray(new AnyName[as.size()]) : null;
	}

	private String asToJava(final String pfx) {
		final StringBuilder sb = new StringBuilder();
		if( as != null ) {
			for( final AnyName n : as ) {
				if( sb.length() > 0 ) {
					sb.append(", ");
				}
				sb.append(n.toJava());
			}
		}
		return sb.toString();
	}

	@Override
	public String toJava(final String pfx) {
		return (value != null ? value.toJava(pfx) : "EMPTY") + " as " + asToJava(pfx);
	}

}
