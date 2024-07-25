package pyeater.code;

public class CodeBreak extends Code {

	public CodeBreak() {
		super(CaseCode.CodeBreak);
	}

	@Override
	public String toJava(final String pfx) {
		return pfx + "break;";
	}

}
