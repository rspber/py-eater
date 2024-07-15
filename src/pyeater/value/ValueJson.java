package pyeater.value;

import java.util.List;

public class ValueJson extends Value {

	final NamedValue[] fields;

	public ValueJson( final List<NamedValue> fields) {
		this.fields = fields != null ? fields.toArray(new NamedValue[fields.size()]) : null;
	}
}
