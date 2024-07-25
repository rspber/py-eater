package pyeater.code;

import pyeater.value.AnyName;

public class CodeImport extends Code {

	public final AnyName from;
	public final AnyName lib;

	public CodeImport(final AnyName from, final AnyName lib) {
		super(CaseCode.CodeImport);
		this.from = from;
		this.lib = lib;
	}

	@Override
	public String toJava(final String pfx) {
		return pfx + "import " + (from != null ? from.toJava() + "." : "") + (lib != null ? lib.toJava() : "") + ";" ;
	}

}
