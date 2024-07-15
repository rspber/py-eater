package pyeater.code;

import java.util.List;

import pyeater.value.Value;

public class CodeNonLocal extends Value {

	final String[] glob;
	
	public CodeNonLocal(final List<String> glob) {
		this.glob = glob.toArray(new String[glob.size()]);
	}

}
