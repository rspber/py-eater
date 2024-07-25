package pyeater.code;

import java.util.List;

import pyeater.util.CodeOut;
import pyeater.value.Value;

public class CodeWhile extends Code {

	public final Value expr;
	public final Code[] code;
	public final Code[] elso;

	public CodeWhile(final Value expr, final List<Code> code, final List<Code> elso) {
		super(CaseCode.CodeWhile);
		this.expr = expr;
		this.code = code.toArray(new Code[code.size()]);
		this.elso = elso != null ? elso.toArray(new Code[elso.size()]) : null;
	}

	@Override
	public String toJava(final String pfx) {
		return pfx + "while (" + (expr != null ? expr.toJava(pfx) : "") + ") {\n" + CodeOut.toJava(pfx, code) + pfx + "}" +
		(elso != null ? "\n" + pfx + "else {\n" + CodeOut.toJava(pfx, elso) + pfx + "}" : "");
	}

}
