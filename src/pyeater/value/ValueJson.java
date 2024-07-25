package pyeater.value;

import java.util.List;

public class ValueJson extends Value {

	public final ValueJsonField[] fields;

	public ValueJson( final List<ValueJsonField> fields) {
		super(CaseValue.ValueJson);
		this.fields = fields != null ? fields.toArray(new ValueJsonField[fields.size()]) : null;
	}

	@Override
	public String toJava(final String pfx) {
		final StringBuilder sb = new StringBuilder();
		sb.append("{");
		if( fields != null ) {
			int i = 0;
			for( final ValueJsonField field : fields ) {
				if( i++ > 0 ) {
					sb.append(", ");
				}
				sb.append(field.toJava(pfx));
			}
		}
		sb.append("}");
		return sb.toString();
	}

}
