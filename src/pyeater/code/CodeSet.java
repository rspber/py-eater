package pyeater.code;

import pyeater.value.Value;

public class CodeSet extends Code {

	public final Value lvalue;
	public final Value rvalue;

	public CodeSet(final Value lvalue, final Value rvalue) {
		super(CaseCode.CodeSet);
		this.lvalue = lvalue;
		this.rvalue = rvalue;
	}

	@Override
	public String toJava(final String pfx) {
		return pfx + (lvalue != null ? lvalue.toJava(pfx) : "EMPTY") + " = " + (rvalue != null ? rvalue.toJava(pfx) : "EMPTY") + ";";
	}

}
