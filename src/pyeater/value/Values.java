package pyeater.value;

import java.util.List;

public class Values extends Value {

	final Value[] values;

	public Values(final List<Value> list) {
		this.values = list.toArray(new Value[list.size()]);
	}
}
