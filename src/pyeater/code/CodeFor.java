package pyeater.code;

import java.util.List;

import pyeater.util.CodeOut;
import pyeater.util.ValueOut;
import pyeater.value.Value;

public class CodeFor extends Code {

	public final Value[] ffor;
	public final Value[] expr;
	public final Code[] code;
	public final Code[] elso;

	public CodeFor(final List<Value> ffor, final List<Value> expr, final List<Code> code, final List<Code> elso) {
		super(CaseCode.CodeFor);
		this.ffor = ffor != null ? ffor.toArray(new Value[ffor.size()]) : null;
		this.expr = expr != null ? expr.toArray(new Value[expr.size()]) : null;
		this.code = code != null ? code.toArray(new Code[code.size()]) : null;
		this.elso = elso != null ? elso.toArray(new Code[elso.size()]) : null;
	}

	@Override
	public String toJava(final String pfx) {
		return pfx + "for (" + (ffor != null ? ValueOut.toJava(pfx, ffor, ",") : "") +
				(expr != null ? " in " + ValueOut.toJava(pfx, expr, ",") : "") + ") {\n" +
				CodeOut.toJava(pfx, code) + pfx + "}" +
				(elso != null ? "\n" + pfx + "else {\n" + CodeOut.toJava(pfx, elso) + pfx + "}" : "");
	}

}
