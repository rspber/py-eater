package pyeater.code;

import java.util.List;

import pyeater.util.CodeOut;
import pyeater.value.RefNameTypedDefault;

public class CodeFunc extends Code {

	public final Annotation[] annotations;
	public final String name;
	public final String self;
	public final RefNameTypedDefault[] args;
	public final Code[] code;

	private boolean isStaticMethod() {
		if( annotations != null ) {
			for( final Annotation annotation : annotations ) {
				if( annotation.name.eqName("staticmethod") ) {
					return true;
				}
			}
		}
		return false;
	}

	public CodeFunc(final Annotation[] annotations, final boolean inClass, final String name, final List<RefNameTypedDefault> args, final List<Code> code) {
		super(CaseCode.CodeFunc);
		this.annotations = annotations;
		this.name = name;
		if( inClass ) {
			if( args.size() > 0 && !isStaticMethod() ) {
				final RefNameTypedDefault t = args.remove(0);
				if( t.stars == 0 && t.defualt == null) {
					self = t.name;	// ? type
				}
				else {
					throw new RuntimeException("self is too complicated");
				}
			}
			else {
				self = null;
			}
		}
		else {
			self = null;
		}
		this.args = args.toArray(new RefNameTypedDefault[args.size()]);
		this.code = code.toArray(new Code[code.size()]);
	}

	String args(final String pfx) {
		final StringBuilder sb = new StringBuilder();
		sb.append("(");
		for( final RefNameTypedDefault arg : args ) {
			if( sb.length() > 1 ) {
				sb.append(", ");
			}
			sb.append(arg.toJava(pfx));
		}
		sb.append(")");
		return sb.toString();
	}

	@Override
	public String toJava(final String pfx) {
		return
			CodeOut.annotationsToJava(pfx, annotations) +
			pfx + "void " + name + args(pfx) + " {\n" + CodeOut.toJava(pfx, code) + pfx + "}";
	}

}
