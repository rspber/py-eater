package pyeater.code;

import java.util.List;

import pyeater.util.CodeOut;
import pyeater.value.Value;

public class CodeIfThen extends Code {

	public final Value expr;
	public final Code[] code;

	public CodeIfThen(final Value expr, final List<Code> code) {
		super(CaseCode.CodeIfThen);
		this.expr = expr;
		this.code = code.toArray(new Code[code.size()]);
	}

	@Override
	public String toJava(final String pfx) {
		return pfx + "if (" + (expr != null ? expr.toJava(pfx) : "") + ") {\n" + CodeOut.toJava(pfx, code) + pfx + "}";
	}

}
