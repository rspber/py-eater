package pyeater.code;

import java.util.List;

import pyeater.util.ValueOut;
import pyeater.value.Value;

public class CodeYield extends Code {

	public final Value expr;
	public final Value[] from;
	
	public CodeYield(final Value expr, final List<Value> from) {
		super(CaseCode.CodeYield);
		this.expr = expr;
		this.from = from != null ? from.toArray(new Value[from.size()]) : null;
	}

	@Override
	public String toJava(final String pfx) {
		return pfx + "yield " + (expr != null ? expr.toJava(pfx) : "EMPTY") +
			(from != null ? " from " + ValueOut.toJava(pfx, from, pfx) : "");
	}

}
