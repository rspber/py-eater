package pyeater.code;

import java.util.ArrayList;
import java.util.List;

import pyeater.util.CodeOut;
import pyeater.util.ValueOut;
import pyeater.value.ValueTypedDefault;

public class CodeClass extends Code {

	public final Annotation[] annotations;
	public final List<String> publics;
	public final String name;
	public final ValueTypedDefault[] extents;
	public final List<Code> codeList;

	public CodeClass(final Annotation[] annotations,
			final String name,
			final List<ValueTypedDefault> extents,
			final List<Code> codeList
	) {
		super(CaseCode.CodeClass);
		this.annotations = annotations;
		this.publics = new ArrayList<>();
		this.name = name;
		this.extents = extents != null ? extents.toArray(new ValueTypedDefault[extents.size()]) : null;
		this.codeList = codeList != null ? codeList : new ArrayList<>();
	}

	private String pubToJava() {
		return publics.size() > 0 ? String.join(" ", publics) + " " : "";
	}

	private String extToJava(final String pfx) {
		return ValueOut.toJava(pfx, extents, ", ");
	}

	@Override
	public String toJava(final String pfx) {
		return
			CodeOut.annotationsToJava(pfx, annotations) +
			pfx + pubToJava() + "class " + name + " " +
			(extents != null ? "extends " + extToJava(pfx) : "") +
			" {\n" +
				CodeOut.toJava(pfx, codeList) +
			pfx + "}";
	}

}
