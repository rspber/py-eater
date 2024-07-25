package pyeater.code;

import java.util.List;

import pyeater.util.ValueOut;
import pyeater.value.Value;

public class CodeDel extends Code {

	public final Value[] expr;
	
	public CodeDel(final List<Value> expr) {
		super(CaseCode.CodeDel);
		this.expr = expr.toArray(new Value[expr.size()]);
	}

	@Override
	public String toJava(final String pfx) {
		return pfx + "del " + ValueOut.toJava(pfx, expr, pfx) + ";";
	}

}
