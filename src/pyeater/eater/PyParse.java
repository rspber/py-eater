package pyeater.eater;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pyeater.code.Code;
import pyeater.code.Annotation;
import pyeater.code.CodeAssert;
import pyeater.code.CodeBreak;
import pyeater.code.CodeClass;
import pyeater.code.CodeContinue;
import pyeater.code.CodeDeclare;
import pyeater.code.CodeFunc;
import pyeater.code.CodeDel;
import pyeater.code.CodeExcept;
import pyeater.code.CodeFor;
import pyeater.code.CodeGlobal;
import pyeater.code.CodeIfThen;
import pyeater.code.CodeIfThenElse;
import pyeater.code.CodeImport;
import pyeater.code.CodeNonLocal;
import pyeater.code.CodeOfValue;
import pyeater.code.CodeRaise;
import pyeater.code.CodeReturn;
import pyeater.code.CodeSet;
import pyeater.code.CodeTry;
import pyeater.code.CodeWhile;
import pyeater.code.CodeWith;
import pyeater.code.CodeYield;
import pyeater.value.AnyName;
import pyeater.value.RefNameTypedDefault;
import pyeater.value.RecName;
import pyeater.value.Value;
import pyeater.value.ValueAs;
import pyeater.value.ValueTypedDefault;
import pyeater.value.Values;
import pyeater.value.ValuesInPar;

public class PyParse extends PyExpr {

	// scanner is in class or subclass
	int inClass;

	// scanner is in def or subdef
	int inDef;

	// current annotation
	final List<Annotation> annotations = new ArrayList<>();

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

	private Code readIf() {
		final int code_depth = ident;
		List<CodeIfThen> list = new ArrayList<>();
		boolean ok = true;
		while( ok ) {
			final Value expr = readExpr();
			eoc();
			final List<Code> code = readCode(code_depth);
			list.add( new CodeIfThen(expr, code));
			ok = skipWord("elif");
		}
		if( skipWord("else") ) {
			eoc();
			List<Code> elso = readCode(code_depth);
			return new CodeIfThenElse(list, elso);
		}
		else {
			return list.size() == 1 ? list.get(0) : new CodeIfThenElse(list, null);
		}
	}

	private Code readFor() {
		final int code_depth = ident;
		final List<Value> ffor = readForFor();
		reqWord("in");
		final List <Value> expr = new ArrayList<>();
		do {
			expr.add(readExpr());
		}
		while( skip(',') );
		eoc();
		final List<Code> code = readCode(code_depth);
		if( skipWord("else") ) {
			eoc();
			final List<Code> elso = readCode(code_depth);
			return new CodeFor(ffor, expr, code, elso);
		}
		else {
			return new CodeFor(ffor, expr, code, null);
		}
	}

	private Code readWith() {
		final int code_depth = ident;
		final List<Value> list = new ArrayList<>();
		do {
			final Value expr = readExpr();
			List<AnyName> as = new ArrayList<>();
			if( skipWord("as") ) {
				if( skip('(') ) {
					as = cntRead( (Object) -> new AnyName(token()), ')');
				}
				else {
					as.add(readRecName_(name_()));
				}
			}
			list.add( new ValueAs(expr, as));
		}
		while( skip(',') );
		eoc();
		final List<Code> code = readCode(code_depth);
		return new CodeWith(list, code);
	}

	private Code readWhile() {
		final int code_depth = ident;
		final Value expr = readExpr();
		eoc();
		final List<Code> code = readCode(code_depth);
		if( skipWord("else") ) {
			eoc();
			List<Code> elso = readCode(code_depth);
			return new CodeWhile(expr, code, elso);
		}
		else {
			return new CodeWhile(expr, code, null);
		}
	}

	private Code readTry() {
		final int code_depth = ident;
		eoc();
		final List<Code> code = readCode(code_depth);
		List<CodeExcept> excepts = new ArrayList<>();
		final List<AnyName> excpts = new ArrayList<>();
		while( skipWord("except") ) {
			if( skip_('(') ) {
				loopTo( (Object) -> excpts.add(readRecName_(name_())), ')');
			}
			else {
				do {
					excpts.add(readRecName_(name_()));
				}
				while( skipWord("or"));
			}
			String as = null;
			if( skipWord("as") ) {
				as = name_();
			}
			eoc();
			final List<Code> excco = readCode(code_depth);
			List<Code> elexcco = null;
			if( skipWord( "else") ) {
				eoc();
				elexcco = readCode(code_depth);
			}
			excepts.add( new CodeExcept(excpts, as, excco, elexcco) );
		}
		List<Code> finaly = null;
		if( skipWord("finally") ) {
			eoc();
			finaly = readCode(code_depth);
		}
		return new CodeTry(code, excepts, finaly);
	}

	private Code readReturn() {
		int lin = lineNo;
		blanks();
		if( lin == lineNo ) {
			return new CodeReturn(readCommaExpr());
		}
		return new CodeReturn(null);
	}

	private Code readRaise() {
		final int code_depth = ident;
		Value expr = null;
		Value from = null;
		if( blanks_() != 0 ) {
			expr = readExpr_();
			if( (code_depth <= ident || ip > ident ) && skipWord_("from") ) {
				from = readExpr_();
			}
		}
		return new CodeRaise(expr, from);
	}

	private RefNameTypedDefault readArg() {
		char c = blanks();
		if( skip_('/') ) {
			return new RefNameTypedDefault(0, "/", null, null);
		}
		int stars = 0;
		while( c == '*' ) {
			++stars;
			++ip;
			c = char_();
		}
		final String name = name_();
		Value type = null;
		Value defualt = null;
		if( skip( ':') ) {
			type = readExpr();
		}
		if( skip('=') ) {
			defualt = readExpr();
		}
		return new RefNameTypedDefault(stars, name, type, defualt);
	}

	private List<ValueTypedDefault> readExtends() {
		final List<ValueTypedDefault> list = new ArrayList<>();
		if( skip('(') ) {
			loopTo( (Object) -> {
				final Value value = readExpr();
				Value defualt = null;
				if( skip('=') ) {
					defualt = readExpr();
				}
				list.add(new ValueTypedDefault(value, null, defualt));
			}, ')');
		}
		eoc();
		return list;
	}

	public final List<AnyName> readImport_() {
		final List<AnyName> list = new ArrayList<>();
		String name;
		while( blanks_() != 0 && (name = name__()) != null ) {
			list.add(readRecName_(name));
			skip_(',');
		}
		return list;
	}

	public AnyName anyName(final List<String> list) {
		if( list.size() == 0 ) {
			err("No from");
		}
		AnyName name = new AnyName(list.get(0));
		for( int i = list.size(); --i >= 1; ) {
			name = new RecName(list.get(i), name);
		}
		return name;
	}

	public Code readFrom() {
		final AnyName from;
		{
			List<String> list = new ArrayList<>();
			blanks_();
			while( char_() == '.' ) {
				++ip;
				list.add(".");
			}
			do {
				String n = name__();	// name_();
				if( n == null ) {
					break;
				}
				list.add(n);
			}
			while( skip('.'));
			from = anyName(list);
		}
		reqWord("import");
		final TmpList res = new TmpList();
		if( skip('*') ) {
			res.list.add( new CodeImport(from, new AnyName("*")) );
			blanks_();
		}
		else {
			if( skip('(') ) {
				final List<AnyName> list = cntRead( (Value) ->  readRecName_(token()), ')');
				list.forEach( na -> res.list.add( new CodeImport( from, na)) );
			}
			else {
				readImport_().forEach( imp -> res.list.add( new CodeImport(from, imp)));
			}
		}
		return res;
	}

	public final TmpList readImport() {
		final TmpList res = new TmpList();
		readImport_().forEach( imp -> res.list.add( new CodeImport(null, imp)));
		return res;
	}

	public Annotation readAnnotation() {
		final AnyName name = readRecName_(name_());
		List<ValueTypedDefault> params = null;
		if( skip('(') ) {
			params = cntRead( (Object) -> {
				blanks();
				final Value value = readExpr_();
				Value defualt = null;
				if( skip('=') ) {
					defualt = readExpr_();
				}
				return new ValueTypedDefault(value, null, defualt);
			}, ')');
			blanks();
		}
		return new Annotation(name, params);
	}

	private Annotation[] getAnnotations() {
		if( annotations.size() > 0 ) {
			final Annotation[] ret = annotations.toArray(new Annotation[annotations.size()]);
			annotations.clear();
			return ret;
		}
		return null;
	}

	public Code readClass() {
		final Annotation[] annos = getAnnotations();
		final int code_depth = ident;
		final String name = name_();
		final List<ValueTypedDefault> extnds = readExtends();
		++inClass;
		final List<Code> body = readCode(code_depth);
		--inClass;
		return new CodeClass(annos, name, extnds, body);
	}

	public Code readDef() {
		final Annotation[] annos = getAnnotations();
		final int code_depth = ident;
		final String name = name_();
		final List<RefNameTypedDefault> args = skip('(') ? cntRead( (Arg) -> readArg(), ')') : null;
		if( skip( "->") ) {
			readExpr();
		}
		eoc();
		++inDef;
		final List<Code> code = readCode(code_depth);
		--inDef;
		return new CodeFunc(annos, inClass > 0 && inDef == 0, name, args, code);
	}

	public Code readAssert() {
		final Value expr = readExpr();
		Value msg = null;
		if( skip_(',') ) {
			msg = readExpr_();
		}
		return new CodeAssert(expr, msg);	
	}

	public Code readYield() {
		Value expr = null;
		List<Value> from = null;
		if( blanks_() != 0 ) {
			skipWord_("from");
			from = readCommaExpr();
		}
		return new CodeYield(expr, from);
	}

	private Code readCode_() {
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
				return readClass();
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
			annotations.add(readAnnotation());
			return readCode_();
		}
		Value expr;
		if( skip_('(') ) {
			expr = cont(new ValuesInPar(cntRead( (Object) -> readExpr(), ')')));
		}
		else {
			expr = readExpr();
		}
		if( expr == null ) {
			err("no code");
		}
		{
			final List<Value> list = new ArrayList<>();
			list.add(expr);
			while( skip(',')) {
				list.add(readExpr());
			}
			expr = list.size() > 1 ? new Values(list) : list.get(0);
		}
		if( skip(':') ) {
			final Value type = readExpr();
			Value defualt = null;
			if( skip('=') ) {
				defualt = readExpr();
			}
			return new CodeDeclare( expr, type, defualt);
		}
		final String oper = oper(); 
		if( oper != null ) {
			switch( oper ) {
			case "=":
				final List<Value> list = new ArrayList<>();	// if tuple
				do {
					list.add(readExpr());
				}
				while( skip(',') );
				Value lexpr = expr;
				expr = list.size() > 1 ? new Values(list) : list.get(0);
				final TmpList listCode = new TmpList();
				listCode.list.add(new CodeSet(lexpr, expr));
				while( skip('=') ) {
					lexpr = expr;
					expr = readExpr();
					listCode.list.add(new CodeSet(lexpr, expr));
				}
				return listCode;
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
				return new CodeSet(expr, readExpr());
			default:
				err("unrecognized operator " + oper);
				undo();
			}
		}
		return new CodeOfValue(expr);
	}

	public List<Code> readCode(final int min_code_depth) {
		final List<Code> list = new ArrayList<>();
		while( nextLine() && blanks() != 0) {
			if( ident <= min_code_depth ) {
				break;
			}
			final Code code = readCode_();
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
			if( code instanceof TmpList ) {
				list.addAll( ((TmpList)code).list);
			}
			else {
				list.add(code);
			}
		}
		return list;
	}

}
