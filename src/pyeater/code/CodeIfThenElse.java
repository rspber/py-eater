package pyeater.code;

import java.util.List;

import pyeater.util.CodeOut;

public class CodeIfThenElse extends Code {

	public final CodeIfThen[] ifthen;
	public final Code[] elso;

	public CodeIfThenElse(final List<CodeIfThen> ifthen, final List<Code> elso) {
		super(CaseCode.CodeIfThenElse);
		this.ifthen = ifthen.toArray(new CodeIfThen[ifthen.size()]);
		this.elso = elso != null ? elso.toArray(new Code[elso.size()]) : null;
	}

	public CodeIfThenElse(final CodeIfThen ifthen, final List<Code> elso) {
		super(CaseCode.CodeIfThenElse);
		this.ifthen = new CodeIfThen[] {ifthen};
		this.elso = elso != null ? elso.toArray(new Code[elso.size()]) : null;
	}

	private String ifthenToJava(final String pfx) {
		if( ifthen != null ) {
			final StringBuilder sb = new StringBuilder();
			int i = 0;
			for( final CodeIfThen eift : ifthen ) {
				if( i++ > 0 ) {
					sb.append("\n");
					sb.append(pfx);
					sb.append("else {\n");
					sb.append(eift.toJava(pfx + "\t"));
					sb.append("\n");
					sb.append(pfx);
					sb.append("}");
				}
				else {
					sb.append(eift.toJava(pfx));
				}
			}
			return sb.toString();
		}
		return "";
	}

	@Override
	public String toJava(final String pfx) {
		return ifthenToJava(pfx) + (elso != null ? "\n" + pfx + "else {\n" + CodeOut.toJava(pfx, elso) : "") + pfx + "}";
	}

}
