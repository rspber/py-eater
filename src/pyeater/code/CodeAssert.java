package pyeater.code;

import pyeater.value.Value;

public class CodeAssert extends Code {

	public final Value expr;
	public final Value msg;

	public CodeAssert(final Value expr, final Value msg) {
		super(CaseCode.CodeAssert);
		this.expr = expr;
		this.msg = msg;
	}

	@Override
	public String toJava(final String pfx) {
		return pfx + "assert " + (expr != null ? expr.toJava(pfx) : "") + (msg != null ? ", " + msg.toJava(pfx) : "") + ";";
	}

}
