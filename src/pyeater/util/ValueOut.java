package pyeater.util;

import pyeater.value.Value;
import pyeater.value.ValueTypedDefault;

public class ValueOut {

	public static String toJava(final String pfx, final Value[] values, final String sep) {
		if( values != null ) {
			final StringBuilder sb = new StringBuilder();
			for( final Value value : values ) {
				if( sb.length() > 0 ) {
					sb.append(sep);
				}
				sb.append(value != null ? value.toJava(pfx) : "EMPTY");
			}
			return sb.toString();
		}
		else {
			return "";
		}
	}

	public static String toJava(final String pfx, final ValueTypedDefault[] values, final String sep) {
		if( values != null ) {
			final StringBuilder sb = new StringBuilder();
			for( final ValueTypedDefault value : values ) {
				if( sb.length() > 0 ) {
					sb.append(sep);
				}
				sb.append(value.value.toJava(pfx));
			}
			return sb.toString();
		}
		else {
			return "";
		}
	}

	public static String toJava(final String pfx, final ValueTypedDefault[] params) {
		final StringBuilder sb = new StringBuilder();
		if( params != null ) {
			int i = 0;
			for( final ValueTypedDefault param : params ) {
				if( i++ > 0 ) {
					sb.append(", ");
				}
				sb.append(param.toJava(pfx));
			}
		}
		return sb.toString();
	}

}
