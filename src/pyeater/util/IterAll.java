package pyeater.util;

import java.util.List;

import pyeater.code.*;
import pyeater.value.*;

public class IterAll {

	// all are Virtual

	// last low level method
	public void procede(final Value value) {
		
	}

	public void iterValues(final Value[] values) {
		if( values != null ) {
			for( final Value value : values ) {
				iterValue(value);
			}
		}
	}

	public void iterValueTD(final ValueTypedDefault[] values) {
		if( values != null ) {
			for( final ValueTypedDefault value : values ) {
				iterValue(value.type);
				iterValue(value.value);
				iterValue(value.defualt);
			}
		}
	}

	public void iterValueJF(final ValueJsonField[] fields) {
		if( fields != null ) {
			for( final ValueJsonField field : fields ) {
				iterValue(field.name);
				iterValue(field.value);
			}
		}
	}

	public void iterNamedRV(final RefNameTypedDefault[] params) {
		if( params != null ) {
			for( final RefNameTypedDefault param : params ) {
				iterValue(param.type);
				iterValue(param.defualt);
			}
		}
	}

	public void iterBackElvisFor(final BackElvisFor value) {
		iterValue( value.expr);
		iterValue( value.elvisValue);
	}

	public void iterBackElvisIf(final BackElvisIf value) {
		iterValue( value.elvisThen);
		iterValue( value.elvisElse);
	}

	public void iterOper(final Oper value) {
		iterValues( value.values );
	}

	public void iterValueAs(final ValueAs value) {
		iterValue(value.value);
	}

	public void iterValueCall(final ValueCall value) {
		iterValue(value.name);
		iterValueTD(value.params);
	}

	public void iterValueLambda(final ValueLambda value) {
		iterNamedRV(value.params );
		iterValue(value.body );
	}

	public void iterValueFormatted(final ValueFormatted value) {
		iterValue(value.value );
	}

	public void iterValueRegExp(final ValueRegExp value) {
		iterValue(value.value );
	}

	public void iterValueBinary(final ValueBinary value) {
		iterValue(value.value );
	}

	public void iterValueJson(final ValueJson value) {
		iterValueJF(value.fields);
	}

	public void iterValueRecName(final ValueRecName value) {
		iterValue(value.rec);
		procede(value);
	}

	public void iterValuesInArray(final ValuesInArray value) {
		iterValues(value.values );
	}

	public void iterValueWithArray(final ValueWithArray value) {
		iterValue(value.name);
		iterValues(value.values);
	}

	public void iterValue(final Value value) {
		if( value != null ) {
			switch( value.type ) {
			case BackElvisFor:
				iterBackElvisFor( (BackElvisFor)value);
				break;
			case BackElvisIf:
				iterBackElvisIf( (BackElvisIf)value);
				break;
			case Oper:
				iterOper((Oper)value);
				break;
			case Values:
				iterValues(((Values)value).values);
				break;
			case ValueAs:
				iterValueAs((ValueAs)value);
				break;
			case ValueCall:
				iterValueCall((ValueCall)value);
				break;
			case ValueFormatted:
				iterValueFormatted((ValueFormatted)value);
				break;
			case ValueRegExp:
				iterValueRegExp((ValueRegExp)value);
				break;
			case ValueBinary:
				iterValueBinary((ValueBinary)value);
				break;
			case ValueJson:
				iterValueJson((ValueJson)value);
				break;
			case ValueLambda:
				iterValueLambda((ValueLambda)value);
				break;
			case ValuesInPar:
				iterValues(((ValuesInPar)value).values );
				break;
			case ValuesInArray:
				iterValuesInArray((ValuesInArray)value);
				break;
			case ValueWithArray:
				iterValueWithArray((ValueWithArray)value);
				break;
			case ValueRecName:
				iterValueRecName((ValueRecName)value);
				break;
			case ValueName:
			case ValueOfName:
				procede(value);
				break;
			default:
				procede(value);
			}
		}
	}

	public void iterCodeAssert(final CodeAssert code) {
		iterValue(code.expr);
		iterValue(code.msg);
		
	}

	public void iterCodeClass(final CodeClass code) {
		iterCodes(code.codeList);
	}

	public void iterCodeDel(final CodeDel code) {
		iterValues(code.expr);
	}

	public void iterCodeExcept(final CodeExcept code) {
		iterCodes(code.code);
		iterCodes(code.elso);
	}

	public void iterCodeFor(final CodeFor code) {
		iterValues(code.ffor);
		iterValues(code.expr);
		iterCodes(code.code);
		iterCodes(code.elso);
	}

	public void iterCodeFunc( final CodeFunc code) {
		iterCodes(code.code);
	}

	public void iterCodeIfThen( final CodeIfThen code ) {
		iterValue(code.expr);
		iterCodes(code.code);
	}

	public void iterCodeIfThenElse( final CodeIfThenElse code ) {
		if( code.ifthen != null ) {
			for( final CodeIfThen ifthen : code.ifthen ) {
				iterCodeIfThen(ifthen);
			}
		}
		iterCodes(code.elso);
	}

	public void iterCodeOfValue( final CodeOfValue code) {
		iterValue(code.expr);
	}

	public void iterCodeRaise( final CodeRaise code) {
		iterValue(code.expr);
		iterValue(code.from);
	}

	public void iterCodeReturn( final CodeReturn code) {
		iterValues(code.expr);
	}

	public void iterCodeSet( final CodeSet code) {
		iterValue(code.lvalue);
		iterValue(code.rvalue);
	}

	public void iterCodeExcepts( final CodeExcept[] excepts ) {
		if( excepts != null ) {
			for( final CodeExcept excpt : excepts ) {
				iterCodeExcept(excpt);
			}
		}
	}

	public void iterCodeTry( final CodeTry code ) {
		iterCodes(code.code);
		iterCodeExcepts(((CodeTry)code).excepts);
		iterCodes(code.finaly);
	}

	public void iterCodeWhile( final CodeWhile code ) {
		iterValue(code.expr);
		iterCodes(code.code);
		iterCodes(code.elso);
	}

	public void iterCodeWith( final CodeWith code) {
		iterValues(code.with);
		iterCodes(code.code);
	}

	public void iterCodeYield( final CodeYield code) {
		iterValue(code.expr);
	}

	public void iterCode(final Code code) {
		switch( code.type ) {
		case CodeAssert:
			iterCodeAssert((CodeAssert)code);
			break;
		case CodeBreak:
			break;
		case CodeClass:
			iterCodeClass((CodeClass)code);
			break;
		case CodeContinue:
			break;
		case CodeDeclare:
			break;
		case CodeDel:
			iterCodeDel((CodeDel)code);
			break;
		case CodeExcept:
			iterCodeExcept((CodeExcept)code);
			break;
		case CodeFor:
			iterCodeFor((CodeFor)code);
			break;
		case CodeFunc:
			iterCodeFunc((CodeFunc)code);
			break;
		case CodeGlobal:
			break;
		case CodeIfThen:
			iterCodeIfThen((CodeIfThen)code);
			break;
		case CodeIfThenElse:
			iterCodeIfThenElse((CodeIfThenElse)code);
			break;
		case CodeImport:
			break;
		case CodeNonLocal:
			break;
		case CodeOfValue:
			iterCodeOfValue((CodeOfValue)code);
			break;
		case CodeRaise:
			iterCodeRaise((CodeRaise)code);
			break;
		case CodeReturn:
			iterCodeReturn((CodeReturn)code);
			break;
		case CodeSet:
			iterCodeSet((CodeSet)code);
			break;
		case CodeTry:
			iterCodeTry((CodeTry)code);
			break;
		case CodeWhile:
			iterCodeWhile((CodeWhile)code);
			break;
		case CodeWith:
			iterCodeWith( (CodeWith)code);
			break;
		case CodeYield:
			iterCodeYield((CodeYield)code);
			break;
		default:
			throw new RuntimeException("unknown code: " + code.type);
		}
	}

	public void iterCodes(final Code[] codes) {
		if( codes != null ) {
			for( final Code code : codes ) {
				iterCode(code);
			}
		}
	}

	public void iterCodes(final List<Code> codes) {
		for( final Code code : codes ) {
			iterCode(code);
		}
	}

}
