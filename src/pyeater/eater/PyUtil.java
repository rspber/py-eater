package pyeater.eater;

public class PyUtil {

	public static boolean isAlpha(final char c) {
		return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c == '_';
	}

	public static boolean isAlpha09(final char c) {
		return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c == '_' || c >= '0' && c <= '9';
	}

	public static boolean is09(final char c) {
		return c >= '0' && c <= '9';
	}

	public static boolean is_09(final char c) {
		return c == '_' || c >= '0' && c <= '9';
	}

	public static boolean is01(final char c) {
		return c >= '0' && c <= '1';
	}

	public static boolean isHex(final char c) {
		return c >= 'a' && c <= 'f' || c >= 'A' && c <= 'F' || c >= '0' && c <= '9';
	}

	public static boolean is_Hex(final char c) {
		return c == '_' || c >= 'a' && c <= 'f' || c >= 'A' && c <= 'F' || c >= '0' && c <= '9';
	}

}
