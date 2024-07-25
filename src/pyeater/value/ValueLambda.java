package pyeater.value;

import java.util.List;

public class ValueLambda extends Value {

	public final RefNameTypedDefault[] params;
	public final Value body;

	public ValueLambda(final List<RefNameTypedDefault> params, final Value body) {
		super(CaseValue.ValueLambda);
		this.params = params != null  ? params.toArray( new RefNameTypedDefault[params.size()]) : null;
		this.body = body;
	}

	private String paramsToJava(final String pfx) {
		final StringBuilder sb = new StringBuilder();
		if( params != null ) {
			for( final RefNameTypedDefault param : params ) {
				sb.append(param.toJava(pfx));
			}
		}
		return sb.toString();
	}

	@Override
	public String toJava(final String pfx) {
		return "lambda " + paramsToJava(pfx) + (body != null ? " " + body.toJava(pfx) : "");
	}

}
