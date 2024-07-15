package pyeater.code;

import java.util.List;

import pyeater.value.Value;

public class CodeIfThenElse extends Value {

	final CodeIfThen[] ifthen;
	final Value[] elso;

	public CodeIfThenElse(final List<CodeIfThen> ifthen, final List<Value> elso) {
		this.ifthen = ifthen.toArray(new CodeIfThen[ifthen.size()]);
		this.elso = elso != null ? elso.toArray(new Value[elso.size()]) : null;
	}

	public CodeIfThenElse(final CodeIfThen ifthen, final List<Value> elso) {
		this.ifthen = new CodeIfThen[] {ifthen};
		this.elso = elso != null ? elso.toArray(new Value[elso.size()]) : null;
	}

}
