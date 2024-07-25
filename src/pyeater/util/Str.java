package pyeater.util;

public class Str {

	public static boolean eq(final String s1, final String s2) {
		if( s1 == s2 ) {
			return true;
		}
		if( s1 == null || s2 == null ) {
			return false;
		}
		return s1.equals(s2);
	}

}
