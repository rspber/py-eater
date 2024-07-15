package pyeater.eater;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;

class PySkipper {

	private final Scanner scanner;

	// current line number
	int lineNo;

	// current line
	protected String line;

	// current line ident
	int ident;

	// line traverse pointers
	int previp;
	int ip;

	// is in line+1
	boolean isNextLine;
	
	PySkipper(final Scanner scanner) {
		this.scanner = scanner;
	}

	private int ident() {
		int n = 0;
		for( int i = 0; i < line.length(); ++i ) {
			final char c = line.charAt(i);
			switch( c ) {
			case ' ':
			case '\t':
				++n;
				continue;
			default:
				return n;
			}
		}
		return 0;
	}

	String substoip() {
		if( previp < ip ) {
			final String s = line.substring(previp, ip);
			return s.isBlank() ? null : s.strip();
		}
		return null;
	}

	public boolean nextLine_() {
		isNextLine = false;
		while( scanner.hasNext() ) {
			line = scanner.next();
			++lineNo;
			previp = 0;
			ip = 0;
			while( line.endsWith("\\") && scanner.hasNext() ) {
				++lineNo;
				line = line.substring(0, line.length()-1) + scanner.next();
			}
			ident = ident();
			if( ident < line.length() && line.charAt(ident) == '#' ) {
				continue;
			}
//			System.out.println(line);
			return true;
		}
		return false;
	}

	public boolean nextLine() {
		if( line != null && ip < line.length() ) {
			if( line.charAt(ip) == '#' ) {
			}
			else {
				if( ip > ident ) {
					final char c = line.charAt(ip-1);
					if( c == ',' || c == ';' ) {
					}
					else {
//						System.out.println(line);
//						err("line has unrecognized content");
					}
				}
				return true;
			}
		}
		return nextLine_();
	}

	public void undo() {
		ip = previp;
	}

	char blanks_() {
		for( ; ip < line.length(); ++ip ) {
			final char c = line.charAt(ip);
			switch( c ) {
			case ' ':
			case '\t':
				continue;
			case '#':
				return 0;
			default:
				return c;
			}
		}
		return 0;
	}

	char blanks() {
		do {
			final char c = blanks_();
			if( c != 0 ) {
				return c;
			}
		}
		while( nextLine_() );
		return 0;
	}

	char blanks1() {
		char c = blanks_();
		if( c != 0 ) {
			return c;
		}
		if( !isNextLine && nextLine_() ) {
			c = blanks_();
			if( PyUtil.isAlpha09(c) ) {
				if( !line.startsWith("not ", ident) &&
					!line.startsWith("and ", ident) &&
					!line.startsWith("or ", ident)
				) {
					isNextLine = true;
				}
			}
			return c;
		}
		return 0;
	}

	boolean skip(final char c) {
		if( blanks() == c ) {
			++ip;
			if( c == '[' || c == ']' || c == '(' || c == ')' || c == ',' ) {
				isNextLine = false;
			}
			return true;
		}
		return false;
	}

	boolean skip_(final char c) {
		if( blanks_() == c ) {
			++ip;
			if( c == '[' || c == ']' || c == '(' || c == ')' || c == ',' ) {
				isNextLine = false;
			}
			return true;
		}
		return false;
	}

	void req(final char c) {
		if( !skip(c) ) {
			err(c + " expected");
		}
	}

	char char_() {
		return ip < line.length() ? line.charAt(ip) : +-0;
	}

	private boolean isBlank__(final int i) {
		if( ip + i < line.length() ) {
			final char c = line.charAt(ip + i);
			return c == ' ' || c == '\t' || c == ':';
		}
		return true;
	}

	boolean skipWord_(final String s) {
		blanks_();
		if( line.startsWith(s, ip) && isBlank__(s.length())) {
			previp = ip;
			ip += s.length();
			return true;
		}
		return false;
	}

	private boolean isBlank_(final int i) {
		if( ip + i < line.length() ) {
			final char c = line.charAt(ip + i);
			return c == ' ' || c == '\t' || c == ':' || c == '#' || c == ',' || c == ']' || c == ')' || c == ';';
		}
		return true;
	}

	boolean skipWord(final String s) {
		blanks();
		if( line.startsWith(s, ip) && isBlank_(s.length()) ) {
			previp = ip;
			ip += s.length();
			return true;
		}
		return false;
	}

	boolean skip_(final String s) {
		blanks_();
		if( line.startsWith(s, ip) ) {
			previp = ip;
			ip += s.length();
			return true;
		}
		return false;
	}

	boolean skip(final String s) {
		blanks();
		if( line.startsWith(s, ip) ) {
			previp = ip;
			ip += s.length();
			return true;
		}
		return false;
	}

	void req(final String s) {
		if( !skip(s) ) {
			err(s + " expented");
		}
	}

	void reqWord(final String s) {
		if( !skipWord(s) ) {
			err(s + " expented");
		}
	}

	// end of command
	void eoc() {
		final char c = blanks_();
		if( c == 0 ) {
			return;
		}
		if( c == ':' ) {
			++ip;
			blanks();
			return;
		}
		err(": expented");
	}

	boolean isAlpha() {
		return PyUtil.isAlpha(char_());
	}

	String name__() {
		if( isAlpha() ) { 
			previp = ip++;
			while( PyUtil.isAlpha09(char_()) ) { ++ip; };
			return line.substring(previp, ip);
		}
		return null;
	}

	String token() {
		blanks();
		return name__();
	}

	String name_() {
		blanks_();
		return name__();
	}

	private void qqq___(final List<String> list, final String qqq) {
		while( true ) {
			int i = line.indexOf(qqq, ip);
			if( i >= 0 && !(i > 0 && line.charAt(i-1) == '\\') ) {
				list.add(line.substring(ip, i));
				ip = i + 3;
				break;
			}
			list.add(line.substring(ip));
			ip = line.length();
			if( scanner.hasNext() ) {
				line = scanner.next();
				previp = 0;
				ip = 0;
				++lineNo;
				ident = ident();
			}
			else {
				break;
			}
		}
	}

	private String readQuoted__() {
		final char c = char_(); 
		if( c == '"' || c == '\'') {
			previp = ip;
			while( ++ip < line.length() ) {
				final char d = line.charAt(ip);
				if( d == '\\' ) {
					++ip;
					continue;
				}
				if( d == c ) {
					++ip;
					return line.substring(previp + 1, ip - 1);
				}
			}
			err("no enclosing quote");
			return line.substring(previp + 1, ip);
		}
		return null;
	}

	List<String> readQuoted_() {
		final char c = char_();
		if( c == '"' ) {
			previp = ip;
			++ip;
			if( char_() == '"') {
				final List<String> list = new ArrayList<>();
				++ip;
				if( char_() == '"') {
					++ip;
					qqq___(list, "\"\"\"");
					return list;
				}
				list.add("");
				return list;
			}
			--ip;
		}
		if( c == '\'' ) {
			previp = ip;
			++ip;
			if( char_() == '\'') {
				final List<String> list = new ArrayList<>();
				++ip;
				if( char_() == '\'') {
					++ip;
					qqq___(list, "'''");
					return list;
				}
				list.add("");
				return list;
			}
			--ip;
		}
		final List<String> list = new ArrayList<>();
		do {
			final String q = readQuoted__();
			if( q != null ) {
				list.add(q);
			}
			else {
				break;
			}
		}
		while( blanks_() == c);
		return list.size() > 0 ? list : null;
	}

	Object readQuoted() {
		blanks();
		return readQuoted_();
	}

	String number_() {
		int i0 = ip;
		while( PyUtil.is09(char_()) ) { ++ip; };
		if( char_() == '.' ) {
			++ip;
			while( PyUtil.is09(char_()) ) { ++ip; };
			if( i0 + 1 == ip ) {
				--ip;
				return null;
			}
		}
		else {
			if( i0 == ip ) {
				return null;
			}
		}
		if( char_() == '_' ) {
			while( PyUtil.is_09(char_()) ) { ++ip; };
		}
		if( char_() == 'j' ) {
			++ip;
		}
		if( char_() == 'e' ) {
			++ip;
			final char c = char_();
			if( c == '-' || c == '+' ) {
				++ip;
			}
			while( PyUtil.is09(char_()) ) { ++ip; };
		}
		if( char_() == 'b' ) {
			++ip;
			while( PyUtil.is01(char_()) ) { ++ip; };
		}
		if( char_() == 'x' ) {
			++ip;
			while( PyUtil.is_Hex(char_()) ) { ++ip; };
		}
		previp = i0;
		return line.substring(i0, ip);
	}

	public <T> void loopTo(final Consumer<T> proc, final char end) {
		if( !skip(end) ) {
			int i = 0;
			do {
				if( ++i > 10000 ) {
					err("dead loop");
				}
				proc.accept(null);
				skip(',');
			}
			while( !skip(end) );
		}
	}

	public <T> List<T> cntRead(final Function<Object, T> proc, final T empty, final char end) {
		final List<T> list = new ArrayList<>();
		if( !skip(end) ) {
			int i = 0;
			do {
				if( ++i > 10000 ) {
					err("dead loop");
				}
				final T item = proc.apply(null);
				if( item == null ) {
					if( empty != null ) {
						list.add(empty);
					}
					else {
						err("no item");
					}
				}
				list.add(item);
				skip(','); 
			}
			while( !skip(end) );
		}
		return list;
	}

	void err(final String msg) {
		int i = ip + 1 < line.length() ? ip + 1 : line.length();
		throw new RuntimeException("line: " + lineNo + "[" + line.substring(0, i) + "^^^" + line.substring(i) + "] " + msg);
	}

}
