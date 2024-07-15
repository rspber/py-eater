package pyeater.code;

import java.util.List;

import pyeater.value.Value;

public class CodeDef extends Value {

	final String name;
	final Arg[] args;
	final Value[] code;

	public CodeDef(final String name, final List<Arg> args, final List<Value> code) {
		this.name = name;
		this.args = args.toArray(new Arg[args.size()]);
		this.code = code.toArray(new Value[code.size()]);
	}

}
