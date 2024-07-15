package pyeater.py;

import java.util.List;

import pyeater.value.Value;

public class PyCode {

	final String name;
	final List<Value> code;

	public PyCode(final String name, final List<Value> code) {
		this.name = name;
		this.code = code;
	}

}
