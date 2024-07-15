package pyeater.code;

import java.util.List;

import pyeater.value.Value;

public class CodeGlobal extends Value {

	final String[] glob;
	
	public CodeGlobal(final List<String> glob) {
		this.glob = glob.toArray(new String[glob.size()]);
	}

}
