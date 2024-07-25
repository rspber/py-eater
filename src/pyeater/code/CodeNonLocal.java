package pyeater.code;

import java.util.List;

public class CodeNonLocal extends Code {

	public final String[] names;

	public CodeNonLocal(final List<String> names) {
		super(CaseCode.CodeNonLocal);
		this.names = names.toArray(new String[names.size()]);
	}

	@Override
	public String toJava(final String pfx) {
		return pfx + (names != null ? String.join(", ", names) : "EMPTY") + ";";
	}

}
