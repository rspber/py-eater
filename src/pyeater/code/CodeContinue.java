package pyeater.code;

public class CodeContinue extends Code {

	public CodeContinue() {
		super(CaseCode.CodeContinue);
	}

	@Override
	public String toJava(final String pfx) {
		return pfx + "continue;";
	}

}
