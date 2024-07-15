package pyeater.value;

import java.util.List;

public class Params extends Value {

	final ValueTypedDefault[] values;

	public Params(final List<ValueTypedDefault> list) {
		this.values = list != null ? list.toArray(new ValueTypedDefault[list.size()]) : null;
	}
}
