package pyeater.code;

import java.util.List;

import pyeater.util.CodeOut;
import pyeater.util.ValueOut;
import pyeater.value.Value;

public class CodeWith extends Code {

	public final Value[] with;
	public final Code[] code;

	public CodeWith(final List<Value> with, final List<Code> code) {
		super(CaseCode.CodeWith);
		this.with = with.toArray(new Value[with.size()]);
		this.code = code.toArray(new Code[code.size()]);
	}

	@Override
	public String toJava(final String pfx) {
		return pfx + "with (" + ValueOut.toJava(pfx, with, "") + ") {\n" + CodeOut.toJava(pfx, code) + pfx + "}";
	}

}
