package pyeater.code;

import java.util.List;

import pyeater.util.ValueOut;
import pyeater.value.Value;

public class CodeReturn extends Code {

	public final Value[] expr;

	public CodeReturn(final List<Value> expr) {
		super(CaseCode.CodeReturn);
		this.expr = expr != null ? expr.toArray(new Value[expr.size()]): null;
	}

	@Override
	public String toJava(final String pfx) {
		return pfx + "return" + (expr != null ? " " + ValueOut.toJava(pfx, expr, "") : "") + ";";
	}

}
