package pyeater.eater;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pyeater.code.Arg;
import pyeater.code.CodeAnnotation;
import pyeater.code.CodeAssert;
import pyeater.code.CodeBreak;
import pyeater.code.CodeClass;
import pyeater.code.CodeContinue;
import pyeater.code.CodeDef;
import pyeater.code.CodeDel;
import pyeater.code.CodeExcept;
import pyeater.code.CodeFor;
import pyeater.code.CodeFrom;
import pyeater.code.CodeGlobal;
import pyeater.code.CodeIfThen;
import pyeater.code.CodeIfThenElse;
import pyeater.code.CodeImport;
import pyeater.code.CodeNonLocal;
import pyeater.code.CodeRaise;
import pyeater.code.CodeReturn;
import pyeater.code.CodeSet;
import pyeater.code.CodeTry;
import pyeater.code.CodeWhile;
import pyeater.code.CodeWith;
import pyeater.code.CodeYield;
import pyeater.value.Value;
import pyeater.value.Values;

public class PyParse extends PyExpr {

	public PyParse(final Scanner scanner) {
		super(scanner);
	}

	public List<String> readCommaNames() {
		final List<String> list = new ArrayList<>();
		do {
			list.add(name_());
		}
		while( skip(',') );
		return list;
	}

	List<Value> readCommaExpr() {
		final List<Value> list = new ArrayList<>();
		do {
			list.add(readExpr());
		}
		while( skip(',') );
		return list;
	}

	private Value readIf() {
		final int code_depth = ident;
		List<CodeIfThen> list = new ArrayList<>();
		boolean ok = true;
		while( ok ) {
			final Value expr = readExpr();
			eoc();
			final List<Value> code = readCode(code_depth);
			list.add( new CodeIfThen(expr, code));
			ok = skipWord("elif");
		}
		if( skipWord("else") ) {
			eoc();
			List<Value> elso = readCode(code_depth);
			return new CodeIfThenElse(list, elso);
		}
		else {
			return list.size() == 1 ? list.get(0) : new CodeIfThenElse(list, null);
		}
	}

	private Value readFor() {
		final int code_depth = ident;
		final List<Value> ffor = readForFor();
		reqWord("in");
		final List <Value> expr = new ArrayList<>();
		do {
			expr.add(readExpr());
		}
		while( skip(',') );
		eoc();
		final List<Value> code = readCode(code_depth);
		if( skipWord("else") ) {
			eoc();
			final List<Value> elso = readCode(code_depth);
			return new CodeFor(ffor, expr, code, elso);
		}
		else {
			return new CodeFor(ffor, expr, code, null);
		}
	}

	private Value readWith() {
		final int code_depth = ident;
		final List<Value> list = new ArrayList<>();
		do {
			final Value expr = readExpr();
			if( skipWord("as") ) {
				if( skip('(') ) {
					do {
						token();
					}
					while( skip(','));
					req(')');
				}
				else {
					readRecName_(name_());
				}
			}
			list.add(expr);
		}
		while( skip(',') );
		eoc();
		final List<Value> code = readCode(code_depth);
		return new CodeWith(list, code);
	}

	private Value readWhile() {
		final int code_depth = ident;
		final Value expr = readExpr();
		eoc();
		final List<Value> code = readCode(code_depth);
		if( skipWord("else") ) {
			eoc();
			List<Value> elso = readCode(code_depth);
			return new CodeWhile(expr, code, elso);
		}
		else {
			return new CodeWhile(expr, code, null);
		}
	}

	private Value readTry() {
		final int code_depth = ident;
		eoc();
		final List<Value> code = readCode(code_depth);
		List<CodeExcept> excepts = new ArrayList<>();
		final List<Value> excpts = new ArrayList<>();
		while( skipWord("except") ) {
			if( skip_('(') ) {
				loopTo( (Object) -> excpts.add(readRecName_(name_())), ')');
			}
			else {
				final List<Value> list = new ArrayList<>();
				do {
					list.add(readRecName_(name_()));
				}
				while( skipWord("or"));
				excpts.add( new Values(list));
			}
			String as = null;
			if( skipWord("as") ) {
				as = name_();
			}
			eoc();
			final List<Value> excco = readCode(code_depth);
			List<Value> elexcco = null;
			if( skipWord( "else") ) {
				eoc();
				elexcco = readCode(code_depth);
			}
			excepts.add( new CodeExcept(excpts, as, excco, elexcco) );
		}
		List<Value> finaly = null;
		if( skipWord("finally") ) {
			eoc();
			finaly = readCode(code_depth);
		}
		return new CodeTry(code, excepts, finaly);
	}

	private Value readReturn() {
		int lin = lineNo;
		blanks();
		if( lin == lineNo ) {
			return new CodeReturn(readCommaExpr());
		}
		return new CodeReturn(null);
	}

	private Value readRaise() {
		final int code_depth = ident;
		Value expr = null;
		if( blanks_() != 0 ) {
			expr = readExpr_();
			if( (code_depth <= ident || ip > ident ) && skipWord_("from") ) {
				final Value from = readExpr_();
				if( skipWord_("import") ) {
					readImport_();
				}
			}
		}
		return new CodeRaise(expr);
	}

	private Arg readArg() {
		char c = blanks();
		if( skip_('/') ) {
			return new Arg("/");
		}
		while( c == '*' ) {
			++ip;
			c = char_();
		}
		final String name = name_();
		if( skip( ':') ) {
			readExpr();
		}
		if( skip('=') ) {
			readExpr();
		}
		return new Arg(name);
	}

	private List<Value> readExtends() {
		final List<Value> list = new ArrayList<>();
		if( skip('(') ) {
			loopTo( (Object) -> {
				final Value value = readExpr();
				if( skip('=') ) {
					readExpr();
				}
				list.add(value);
			}, ')');
		}
		eoc();
		return list;
	}

	public final List<Value> readImport_() {
		final List<Value> list = new ArrayList<>();
		String name;
		while( blanks_() != 0 && (name = name__()) != null ) {
			list.add(readRecName_(name));
			skip_(',');
		}
		return list;
	}

	public Value readImport() {
		return new CodeImport(readImport_());
	}

	public Value readFrom() {
		List<String> from = new ArrayList<>();
		blanks_();
		while( true ) {
			if( char_() == '.' ) {
				++ip;
				if( char_() == '.' ) {
					++ip;
					from.add("..");
					continue;
				}
				from.add(".");
				continue;
			}
			String n = name__();	// name_();
			if( n == null ) {
				break;
			}
			from.add(n);
		}
		reqWord("import");
		if( skip('*') ) {
			blanks_();
		}
		else {
			if( skip('(') ) {
				final List<Value> list = cntRead( (Value) ->  readRecName_(token()), null, ')');
			}
			else {
				readImport_();
			}
		}
		return new CodeFrom(from, null);
	}

	public Value readAnnotation() {
		final Value name = readRecName_(name_());
		List<Value> params = null;
		if( skip('(') ) {
			loopTo( (Object) -> {
				blanks();
				readExpr_();
				if( skip('=') ) {
					readExpr_();
				}
			}, ')');
			blanks();
		}
		return new CodeAnnotation(name, params);
	}

	public Value readDef() {
		final int code_depth = ident;
		final String name = name_();
		final List<Arg> args = skip('(') ? cntRead( (Arg) -> readArg(), null, ')') : null;
		if( skip( "->") ) {
			readExpr();
		}
		eoc();
		final List<Value> code = readCode(code_depth);
		return new CodeDef(name, args, code);
	}

	public Value readAssert() {
		final Value expr = readExpr();
		final Value msg = skip_(',') ? readExpr_() : null;
		return new CodeAssert(expr, msg);	
	}

	public Value readYield() {
		Value expr = null;
		if( blanks_() != 0 ) {
			skipWord_("from");
			readCommaExpr();
		}
		return new CodeYield(expr);
	}

	private Value readCode_() {
		isNextLine = false;
		String token = token();
		while( token != null ) {
			switch( token ) {
			case "async":
				token = token();
				continue;
			case "import":
				return readImport();
			case "from":
				return readFrom();
			case "class":
				return new CodeClass(name_(), readExtends(), readCode(ident));
			case "def":
				return readDef();
			case "global":
				return new CodeGlobal(readCommaNames());
			case "nonlocal":
				return new CodeNonLocal(readCommaNames());
			case "if":
				return readIf();
			case "while":
				return readWhile();
			case "for":
				return readFor();
			case "break":
				skip(':');
				return new CodeBreak();
			case "continue":
				skip(':');
				return new CodeContinue();
			case "return":
				return readReturn();
			case "assert":
				return readAssert();
			case "with":
				return readWith();
			case "try":
				return readTry();
			case "raise":
				return readRaise();
			case "del":
				return new CodeDel(readCommaExpr());
			case "yield":
				return readYield();
			default:
				undo();
			}
			break;
		}
		if( skip_('@') ) {
			return readAnnotation();
		}
		Value expr;
		if( skip_('(') ) {
			expr = cont(new Values(cntRead( (Object) -> readExpr(), null, ')')));
		}
		else {
			expr = readExpr();
		}
		{
			final List<Value> list = new ArrayList<>();
			do {
				list.add(expr);
			}
			while( skip(','));
			expr = list.size() > 1 ? new Values(list) : list.get(0);
		}
		if( skip(':') ) {
			final Value type = readExpr();
			if( skip('=') ) {
				readExpr();
			}
			return expr;
		}
		final String oper = oper(); 
		if( oper != null ) {
			switch( oper ) {
			case "=":
				final List<Value> list = new ArrayList<>();
				do {
					list.add(readExpr());
				}
				while( skip(',') );
				expr = new CodeSet(expr, list.size() > 1 ? new Values(list) : list.get(0));
				while( skip('=') ) {
					readExpr();
				}
				break;
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
				expr = new CodeSet(expr, readExpr());
				break;
			default:
				err("unrecognized operator " + oper);
				undo();
			}
		}
		return expr;
	}

	public List<Value> readCode(final int min_code_depth) {
		final List<Value> list = new ArrayList<>();
		while( nextLine() && blanks() != 0) {
			if( ident <= min_code_depth ) {
				break;
			}
			final Value code = readCode_();
			if( code == null ) {
				err("no code");
			}
			while( true ) {
				if( char_() == ',') { ++ip; continue; }
				if( char_() == ';') {
					++ip; continue;
				}
				break;
			}
			list.add(code);
		}
		return list;
	}

}
