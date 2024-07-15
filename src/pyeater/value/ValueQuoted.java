package pyeater.value;

import java.util.List;

public class ValueQuoted extends Value {

	final String[] value;

	public ValueQuoted(final Object q) {
		if( q instanceof List ) {
			List<String> list = (List<String>)q;
			this.value = list != null ? list.toArray(new String[list.size()]) : null;
		}
		else {
			this.value = new String[] {(String)q};
		}
	}

}
