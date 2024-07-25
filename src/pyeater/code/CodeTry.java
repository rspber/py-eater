package pyeater.code;

import java.util.List;

import pyeater.util.CodeOut;

public class CodeTry extends Code {

	public final Code[] code;
	public final CodeExcept[] excepts;
	public final Code[] finaly;

	public CodeTry(final List<Code> code, final List<CodeExcept> excepts, final List<Code> finaly) {
		super(CaseCode.CodeTry);
		this.code = code.toArray(new Code[code.size()]);
		this.excepts = excepts != null ? excepts.toArray(new CodeExcept[excepts.size()]) : null;
		this.finaly = finaly != null ? finaly.toArray(new Code[finaly.size()]) : null;
	}

	public String excepts(final String pfx) {
		if( excepts != null ) {
			final StringBuilder sb = new StringBuilder();
			for( final CodeExcept excpt : excepts ) {
				sb.append(excpt.toJava(pfx));
			}
			return sb.toString();
		}
		return "";
	}

	@Override
	public String toJava(final String pfx) {
		return pfx + "try {\n" + CodeOut.toJava(pfx, code) + pfx + "}" +
		excepts(pfx) +
		(finaly != null ? "\n" + pfx + "finally {\n" + CodeOut.toJava(pfx, finaly) + pfx + "}" : "");
	}

}
