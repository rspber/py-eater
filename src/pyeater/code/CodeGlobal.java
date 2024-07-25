package pyeater.code;

import java.util.List;

public class CodeGlobal extends Code {

	public final String[] names;

	public CodeGlobal(final List<String> names) {
		super(CaseCode.CodeGlobal);
		this.names = names.toArray(new String[names.size()]);
	}

	@Override
	public String toJava(final String pfx) {
		return pfx + (names != null ? String.join(", ", names) : "EMPTY") + ";";
	}

}
