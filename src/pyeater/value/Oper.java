package pyeater.value;

import java.util.List;

public class Oper extends Value  {

	final String oper;
	final Value[] values;

	public Oper(final String oper, final List<Value> list)
	{
		this.oper = oper;
		this.values = list.toArray(new Value[list.size()]);
	}

	public Oper(final Value value1, final String oper, final Value value2)
	{
		this.oper = oper;
		this.values = new Value[] { value1, value2 };
	}

	public Oper(final String oper, final Value value1)
	{
		this.oper = oper;
		this.values = new Value[] { value1 };
	}

}
