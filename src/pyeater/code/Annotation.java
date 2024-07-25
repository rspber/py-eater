package pyeater.code;

import java.util.List;

import pyeater.value.AnyName;
import pyeater.value.ValueTypedDefault;

public class Annotation {

	public final AnyName name;
	public final ValueTypedDefault[] params;

	public Annotation(final AnyName name, final List<ValueTypedDefault> params) {
		this.name = name;
		this.params = params != null ? params.toArray(new ValueTypedDefault[params.size()]) : null;
	}

	public String toJava(final String pfx) {
		return pfx + "@" + "...";
	}
}
