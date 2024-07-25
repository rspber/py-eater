package pyeater.code;

import pyeater.value.Value;

// expression as code
public class CodeOfValue extends Code {

	public final Value expr;

	public CodeOfValue(final Value expr) {
		super(CaseCode.CodeOfValue);
		this.expr = expr;
	}

	@Override
	public String toJava(final String pfx) {
		return pfx + (expr != null ? expr.toJava(pfx) : "EMPTY") + ";";
	}

}
