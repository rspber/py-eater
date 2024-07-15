package pyeater.value;

import java.util.List;

public class ValueLambda extends Value {

	final NamedRefValue[] params;
	final Value body;

	public ValueLambda(final List<NamedRefValue> params, final Value body) {
		this.params = params != null  ? params.toArray( new NamedRefValue[params.size()]) : null;
		this.body = body;
	}
}
