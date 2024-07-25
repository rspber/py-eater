package pyeater.value;

public class RefNameTypedDefault {

	public final int stars;
	public final String name;
	public final Value type;
	public final Value defualt;

	public RefNameTypedDefault(final int stars, final String name, final Value type,final Value defualt) {
		this.stars = stars;
		this.name = name;
		this.type = type;
		this.defualt = defualt;
	}

//	@Override
	public String toJava(final String pfx) {
		final StringBuilder sb = new StringBuilder();
		if( type != null ) {
			sb.append(type.toJava(pfx));
			sb.append(" ");
		}
		for( int i = stars; --i >= 0; ) {
			sb.append("*");
		}
		sb.append(name);
		if( defualt != null ) {
			sb.append(" = ");
			sb.append(defualt.toJava(pfx));
		}
		return sb.toString();
	}

}
