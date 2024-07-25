package pyeater.util;

import java.util.List;

import pyeater.code.Annotation;
import pyeater.code.Code;

public class CodeOut {

	public static String annotationsToJava(final String pfx, final Annotation[] annotations) {
		if( annotations != null ) {
			final StringBuilder sb = new StringBuilder();
			for( final Annotation annotation : annotations ) {
				sb.append(annotation.toJava(pfx));
				sb.append("\n");
			}
			return sb.toString();
		}
		return "";
	}

	public static String toJava(final String pfx, final Code[] codes) {
		if( codes != null ) {
			final StringBuilder sb = new StringBuilder();
			for( final Code code : codes ) {
				sb.append(code.toJava(pfx + "\t"));
				sb.append("\n");
			}
			return sb.toString();
		}
		else {
			return "";
		}
	}

	public static String toJava(final String pfx, final List<Code> codes) {
		if( codes != null ) {
			final StringBuilder sb = new StringBuilder();
			for( final Code code : codes ) {
				sb.append(code.toJava(pfx + "\t"));
				sb.append("\n");
			}
			return sb.toString();
		}
		else {
			return "";
		}
	}

}
