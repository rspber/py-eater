package pyeater.py;

import java.util.Map;
import java.util.TreeMap;

public class Directory {

	final Directory parent;
	final String name;
	final Map<String, PyCode> m_py = new TreeMap<>();

	public Directory(final Directory parent, final String name) {
		this.parent = parent;
		this.name = name;
	}

	public Directory mkdir(final String name) {
		return new Directory(this, name);
	}

	public void addPy(final PyCode pyCode) {
		m_py.put(pyCode.name,  pyCode);
	}
}
