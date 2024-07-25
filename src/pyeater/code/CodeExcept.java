package pyeater.code;

import java.util.List;

import pyeater.util.CodeOut;
import pyeater.value.AnyName;

public class CodeExcept extends Code {

	public final AnyName[] excpts;
	public final String as;
	public final Code[] code;
	public final Code[] elso;

	public CodeExcept(final List<AnyName> excpts, final String as, final List<Code> code, final List<Code> elso) {
		super(CaseCode.CodeExcept);
		this.excpts = excpts.toArray(new AnyName[excpts.size()]);
		this.as = as;
		this.code = code.toArray(new Code[code.size()]);
		this.elso = elso != null ? elso.toArray(new Code[elso.size()]) : null;
	}

	public String excepts(final String pfx ) {
		if( excpts != null ) {
			final StringBuilder sb = new StringBuilder();
			sb.append("\n");
			sb.append(pfx);
			sb.append("catch (");
			int i = 0;
			for( final AnyName excpt : excpts ) {
				if( i++ > 0 ) {
					sb.append(" | ");
				}
				sb.append(excpt.toJava());
			}
			sb.append(")");
			return sb.toString();
		}
		return "";
	}

	@Override
	public String toJava(final String pfx) {
		return pfx + excepts(pfx) + (as != null ? " as " + as : "") + " {\n" + CodeOut.toJava(pfx, code) + pfx + "}" +
				(elso != null ? "\n" + pfx + "else {\n" + CodeOut.toJava(pfx, elso) + pfx + "}" : "");
	}

}
