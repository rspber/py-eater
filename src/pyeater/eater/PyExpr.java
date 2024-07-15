package pyeater.eater;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pyeater.value.BackElvisFor;
import pyeater.value.BackElvisIf;
import pyeater.value.NamedRefValue;
import pyeater.value.Oper;
import pyeater.value.Value;
import pyeater.value.Value3Dot;
import pyeater.value.ValueWithArr;
import pyeater.value.ValueBinary;
import pyeater.value.ValueCall;
import pyeater.value.ValueEmpty;
import pyeater.value.RecField;
import pyeater.value.ValueFormatted;
import pyeater.value.ValueJson;
import pyeater.value.ValueLambda;
import pyeater.value.ValueName;
import pyeater.value.NamedValue;
import pyeater.value.ValueNumber;
import pyeater.value.ValueQuoted;
import pyeater.value.ValueRegExp;
import pyeater.value.ValueTypedDefault;
import pyeater.value.Values;
import pyeater.value.ValuesInArr;
import pyeater.value.Params;

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
		final List<NamedRefValue> list = new ArrayList<>();
		do {
			int stars = 0;
			while( blanks_() == '*' ) {
				++stars;
				++ip;
			}
			final String name = name_();
			final Value value = skip('=') ? readExpr() : null;
			list.add( new NamedRefValue(stars, name, value));
		}
		while( skip(',') );
		final Value body = skip_(':') ? readExpr() : null;
		return new ValueLambda(list, body);
	}

	private List<NamedValue> readJson(final char c) {
		final List<NamedValue> list = new ArrayList<>();
		++ip;
		if( !skip(c) ) {
			do {
				final Value name = readExpr_();
				final Value value = skip(':') ? readExpr() : null;
				list.add( new NamedValue(name, value));
				skip(',');
			}
			while( !skip(c) );
		}
		return list;
	}

	Value readRecName_(String name) {
		Value rec = new ValueName(name);
		while( skip_('.') && (name = name_()) != null ) {
			rec = new RecField(rec, name);
		}
		return rec;
	}

	Value forForFor() {
		if( skip('(') ) {
			return new Values(cntRead( (Object) -> forForFor(), null, ')'));	// in Par ?
		}
		else {
			if( skip_('[') ) {
				return new ValuesInArr(readInPar(']'));
			}
			else {
				skip_('*');
				return readRecName_(name_());
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

	Value readInPar(final char c) {
		++ip;
		List<ValueTypedDefault> list = new ArrayList<>();
		blanks();
		loopTo( (Object) -> {
			final Value value = readExpr();
			final Value type = skip(':') ? readExpr() : null;
			final Value defualt = skip('=') ? readExpr() : null;
			list.add( new ValueTypedDefault(value, type, defualt));
		}, c);
		return back_elvis(new Params(list));
	}

	protected Value cont(Value value) {
		while(true) {
			if( blanks_() == '(' ) {							// read function call parameters
				value = new ValueCall(value, readInPar(')'));
				isNextLine = false;
				continue;
			}
			if( blanks_() == '[') {								// read nested values in square bracketS
				value = new ValueWithArr(value, readInPar(']'));
				isNextLine = false;
				continue;
			}
			if( blanks1() == '.' ) {							// read name of object field or function
				++ip;
				value = new RecField(value, name_() );
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
			Object q = readQuoted_();
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
			return cont(new ValuesInArr(readInPar(']')));
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
			value = cont(back_elvis(new Values(cntRead( (Object) -> readExpr_(), new ValueEmpty(), ')' ))));
		}
		else {
			if( skipWord_("not") ) {
				return readExpr_();
			}
			if( skipWord_("lambda") ) {
				return readLambda();
			}
			if( skipWord_("await") ) {
			}
			if( skipWord_("yield") ) {
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
				expr = new Oper("&&", o2);
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
				expr = new Oper("||", o2);
				continue;
			}
			break;
		}
		return expr;
	}

}
