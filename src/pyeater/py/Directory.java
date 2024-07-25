package pyeater.py;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Directory {

	public final Directory parent;
	public final String name;

	public final Map<String, PyCode> m_py = new TreeMap<>();

	public final Map<String, Directory> m_dir = new TreeMap<>();

	public Directory(final Directory parent, final String name) {
		this.parent = parent;
		this.name = name;
	}

	public Directory mkdir(final String name) {
		final Directory dir = new Directory(this, name);
		m_dir.put(name,  dir);
		return dir;
	}

	public void addPy(final PyCode pyCode) {
		m_py.put(pyCode.name, pyCode);
	}

	public void initial_converts() {
		m_py.forEach( (name, py) -> py.initial_converts());
		m_dir.forEach( (name, dir) -> dir.initial_converts());
	}

	public String getPackage() {
		Directory dir = this;
		final List<String> list = new ArrayList<>();
		do {
			list.add(0, dir.name);
			dir = dir.parent;
		}
		while(dir != null);
		return String.join(".", list);
	}

	public void toJava(final File path) {
		if( m_py.size() > 0 ) {
			path.mkdirs();
			m_py.forEach( (name, py) -> py.toJava(path, getPackage()));
		}
		m_dir.forEach( (name, dir) -> dir.toJava(new File(path, name)));
	}

}
