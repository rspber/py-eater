package pyeater.eater;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pyeater.value.BackElvisFor;
import pyeater.value.BackElvisIf;
import pyeater.value.AnyName;
import pyeater.value.RefNameTypedDefault;
import pyeater.value.Oper;
import pyeater.value.Value;
import pyeater.value.Value3Dot;
import pyeater.value.ValueWithArray;
import pyeater.value.Values;
import pyeater.value.ValueBinary;
import pyeater.value.ValueCall;
import pyeater.value.RecName;
import pyeater.value.ValueFormatted;
import pyeater.value.ValueJson;
import pyeater.value.ValueJsonField;
import pyeater.value.ValueLambda;
import pyeater.value.ValueName;
import pyeater.value.ValueNo;
import pyeater.value.ValueNumber;
import pyeater.value.ValueQuoted;
import pyeater.value.ValueRecName;
import pyeater.value.ValueOfName;
import pyeater.value.ValueRegExp;
import pyeater.value.ValueTypedDefault;
import pyeater.value.ValuesInPar;
import pyeater.value.ValuesInArray;

public class PyExpr extends PySkipper {

	PyExpr(final Scanner scanner) {
		super(scanner);
	}

	Value back_elvis(Value expr) {
		final int lin = lineNo;
		while( lin == lineNo ) {
			if( skipWord_("for") ) {
				blanks();
				final List<Value> forfor = readForFor();
				reqWord("in");
				final Value elvisValue = readExpr();
				expr = new BackElvisFor(new Values(forfor), elvisValue);
				continue;
			}
			if( skipWord_("if") ) {
				blanks();
				final Value elvisThen = readExpr();
				final Value elvisElse = skipWord("else") ? readExpr() : null;
				expr = new BackElvisIf(elvisThen, elvisElse);
				continue;
			}
			break;
		}
		return expr;
	}

	Value readLambda() {
		final List<RefNameTypedDefault> list = new ArrayList<>();
		do {
			int stars = 0;
			while( blanks_() == '*' ) {
				++stars;
				++ip;
			}
			final String name = name_();
			Value defualt = null;
			if( skip('=') ) {
				defualt = readExpr();
			}
			list.add( new RefNameTypedDefault(stars, name, null, defualt));
		}
		while( skip(',') );
		Value body = null;
		if( skip_(':') ) {
			body = readExpr();
		}
		return new ValueLambda(list, body);
	}

	private List<ValueJsonField> readJson(final char c) {
		final List<ValueJsonField> list = new ArrayList<>();
		++ip;
		if( !skip(c) ) {
			do {
				final Value name = readExpr_();
				Value value = null;
				if( skip(':') ) {
					value = readExpr();
				}
				list.add( new ValueJsonField(name, value));
				skip(',');
			}
			while( !skip(c) );
		}
		return list;
	}

	AnyName readRecName_(String nxtName) {
		AnyName vName = new AnyName(nxtName);
		while( skip_('.') && (nxtName = name_()) != null ) {
			vName = new RecName(nxtName, vName);
		}
		return vName;
	}

	Value forForFor() {
		if( skip('(') ) {
			return new ValuesInPar(cntRead( (Object) -> forForFor(), ')'));	// in Par ?
		}
		else {
			if( skip_('[') ) {
				return new ValuesInArray(readInArr(']'));
			}
			else {
				skip_('*');
				return new ValueOfName(readRecName_(name_()));
			}
		}
	}

	List<Value> readForFor() {
		final List<Value> list = new ArrayList<>();
		do {
			list.add(forForFor());
		}
		while( skip(',') );
		return list;
	}

	List<Value> readInArr(final char c) {
		++ip;
		final List<Value> list = new ArrayList<>();
		blanks();
		loopTo( (Object) -> {
			if( skip(':') ) {
				list.add(new ValueNo());
			}
			else {
				list.add(readExpr());
			}
		}, c);
		return list;
	}

	List<ValueTypedDefault> readParams(final char c) {
		++ip;
		final List<ValueTypedDefault> list = new ArrayList<>();
		blanks();
		loopTo( (Object) -> {
			final Value value = readExpr();
			Value type = null;
			if( skip(':') ) {
				type = readExpr();
			}
			Value defualt = null;
			if( skip('=') ) {
				defualt = readExpr();
			}
			list.add( new ValueTypedDefault(value, type, defualt));
		}, c);
		return list;
	}

	protected Value cont(Value value) {
		while(true) {
			if( blanks_() == '(' ) {							// read function call parameters
				value = back_elvis(new ValueCall(value, readParams(')')));
				isNextLine = false;
				continue;
			}
			if( blanks_() == '[') {								// read nested values in square bracketS
				value = back_elvis(new ValueWithArray(value, readInArr(']')));
				isNextLine = false;
				continue;
			}
			if( blanks1() == '.' ) {							// read name of object field or function
				++ip;
				value = new ValueRecName(value, name_() );
				isNextLine = false;
				continue;
			}
			break;
		}
		return value;
	}

	protected Value value() {
		char c = blanks_();
		if( c == '.' ) {	// "..."
			++ip;
			if( char_() == '.' ) {
				++ip;
				if( char_() == '.' ) {
					++ip;
					return new Value3Dot();
				}
				--ip;
			}
			--ip;
		}
		{								// quotando
			boolean isr = false;
			if( c == 'r' ) {
				isr = true;
				++ip;
				c = char_();
			}
			boolean isf = false;
			if( c == 'f' ) {
				isf = true;
				++ip;
				c = char_();
			}
			boolean isb = false;
			if( c == 'b' ) {
				isb = true;
				++ip;
				c = char_();
			}
			String q = readQuoted_();
			if( q != null ) {
				Value v = new ValueQuoted(q);
				if( isf ) {
					v = new ValueFormatted(v);
				}
				if( isr ) {
					v = new ValueRegExp(v);
				}
				if( isb ) {
					v = new ValueBinary(v);
				}
				return cont(v);
			}
			if( isb ) {
				--ip;
				c = char_();
			}
			if( isf ) {
				--ip;
				c = char_();
			}
			if( isr ) {
				--ip;
				c = char_();
			}
 		}
		if( PyUtil.isAlpha(c) ) {
			return cont(new ValueName(name_()));
		}
		{
			final String value = number_();
			if( value != null ) {
				return new ValueNumber(value);
			}
		}
		if( c == '[' ) {										// read values in brackets as value
			return cont(back_elvis(new ValuesInArray(readInArr(']'))));
		}
		if( c == '{' ) {										// read object in place as value
			return cont(new ValueJson(readJson('}')));
		}
		return null;
	}

	String oper() {
		blanks1();
		previp = ip;
		if( skipWord("not") ) {
			if( skipWord("in") ) {
				return "not in";
			}
			return "not";
		}
		if( skipWord("in") ) {
			return "in";
		}
		if( skipWord("is") ) {
			if( skipWord("not") ) {
				return "is not";
			}
			return "is";
		}
		while(true) {
			final char c = char_();
			switch( c ) {
			case '~': 
			case '+': 
			case '/': 
			case '&': 
			case '|': 
			case '^': 
			case '%': 
			case '!': 
			case '<': 
			case '>': 
				if( ip > previp ) {
					--ip;
					if( char_() == '=' ) {
						++ip;
						return substoip();
					}
					++ip;
				}
			case '=':
				++ip;
				break;
			case '-':
			{
				if( ip > previp ) {
					return substoip();
				}
				++ip;
				final char d = char_();
				if( d == '>' ) {
					--ip;
					return substoip();
				}
				else {
					break;
				}
			}
			case '*': 
			{
				++ip;
				final char d = char_();
				if( d == 0 || d == ' ' || d == '\t' || d == '*' || d == '='|| d == '['|| d == '(' ) {
					break;
				}
				else {
					--ip;
					return substoip();
				}
			}
			case '@':
			{
				++ip;
				final char d = char_();
				if( d == 0 || d == ' ' || d == '\t' ) {
				}
				else {
					--ip;
				}
				return substoip();
			}
			case '\'': 
			case '"':
			{
				final String ret = substoip();
				++ip;
				boolean ok = char_() != c;
				--ip;
				return ret != null || !ok ? ret : "+";
			}
			case 'f':
			{
				final String ret = substoip();
				++ip;
				final char d = char_();
				--ip;
				if( d == '\'' || d == '"' ) {
					return ret != null ? ret : "+";
				}
				return ret;
			}
			case ':':
				++ip;
				if( char_() == '=' ) {
					break;
				}
				--ip;
			default:
				return substoip();
			}
		}
	}

	protected Value readExpr_() {
		if( skip_('*') ) {
			do {
			}
			while( skip_('*'));
		}
		final Value value;
		if( skip_('(') ) {
			value = cont(back_elvis(new ValuesInPar(cntRead( (Object) -> readExpr_(), ')' ))));
		}
		else {
			if( skipWord_("not") ) {
				blanks();
				return readExpr_();
			}
			if( skipWord_("lambda") ) {
				return readLambda();
			}
			if( skipWord_("await") ) {
			}
			if( skipWord_("yield") ) {
				if( skip_('(') ) {
					return new ValuesInPar(cntRead( (Object) -> readExpr_(), ')' ));
				}
			}
			int lin = lineNo;
			int pfx = ident;
			Value v1 = value();
			if( v1 != null && pfx == ident && (lin == lineNo || ip > ident) ) {
				v1 = back_elvis(v1);
			}
			value = v1;
		}

		final String oper = oper();
		if( oper != null ) {
			if( value != null ) {
				switch( oper ) {
				case "+":
				case "-":
				case "*":
				case "**":
				case "/":
				case "//":
				case "^":
				case "%":
				case "&":
				case "|":
				case ">>":
				case "<<":
					return new Oper(value, oper, readExpr());
				case "in":
				case "not in":
				case "is":
				case "is not":
				case "==":
				case "<":
				case "<=":
				case ">":
				case ">=":
				case "!=":
				case ":=":
				case "@":
					return new Oper(value, oper, readExpr());
				case "<>":
					return new Oper(value, "!=", readExpr());
				case "=":
				case "+=":
				case "-=":
				case "*=":
				case "**=":
				case "/=":
				case "//=":
				case "^=":
				case "%=":
				case "&=":
				case "|=":
				case ">>=":
				case "<<=":
				case "not":
					undo();
					break;
				default:;
					err("bad operator use " + oper);
					undo();
				}
			}
			else {
				switch( oper ) {
				case "~": 
				case "not":
				case "-":
				case "+":
					return new Oper(oper, readExpr());
				case "in":
				case "not in":
					undo();
					break;
				default:;
					err("bad operator use " + oper);
					undo();
				}
			}
		}
		return value;
	}

	public Value readExpr() {
		Value expr = readExpr_();
		List<Value> o2 = null;
		while( expr != null ) {
			o2 = null;
			while( skipWord("and") ) {
				if( o2 == null ) {
					o2 = new ArrayList<>();
					o2.add(expr);
				}
				o2.add( readExpr_() );
			}
			if( o2 != null ) {
				expr = new Oper(null, "&&", null, o2);
				continue;
			}
			while( skipWord("or") ) {
				if( o2 == null ) {
					o2 = new ArrayList<>();
					o2.add(expr);
				}
				o2.add( readExpr_() );
			}
			if( o2 != null ) {
				expr = new Oper(null, "||", null, o2);
				continue;
			}
			break;
		}
		return expr;
	}

}
