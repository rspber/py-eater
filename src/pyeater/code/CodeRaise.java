package pyeater.code;

import pyeater.value.Value;

public class CodeRaise extends Code {

	public final Value expr;
	public final Value from;

	public CodeRaise(final Value expr, final Value from) {
		super(CaseCode.CodeRaise);
		this.expr = expr;
		this.from = from;
	}

	@Override
	public String toJava(final String pfx) {
		return pfx + "raise " + (expr != null ? expr.toJava(pfx) : "EMPTY") + (from != null ? " from " + from.toJava(pfx) : "") + ";";
	}

}
