package pyeater.value;

import java.util.List;

public class Oper extends Value  {

	public final String leftOper;
	public final String mdlOper;
	public final String rightOper;
	public final Value[] values;

	public Oper(final String leftOper, final String mdlOper, final String rightOper, final List<Value> list) {
		super(CaseValue.Oper);
		this.leftOper = leftOper;
		this.mdlOper = mdlOper;
		this.rightOper = rightOper;
		this.values = list.toArray(new Value[list.size()]);
	}

	public Oper(final Value value1, final String mdlOper, final Value value2) {
		super(CaseValue.Oper);
		this.leftOper = null;
		this.mdlOper = mdlOper;
		this.rightOper = null;
		this.values = new Value[] { value1, value2 };
	}

	public Oper(final String leftOper, final Value value1) {
		super(CaseValue.Oper);
		this.leftOper = leftOper;
		this.mdlOper = null;
		this.rightOper = null;
		this.values = new Value[] { value1 };
	}

	@Override
	public String toJava(final String pfx) {
		if( values != null ) {
			final StringBuilder sb = new StringBuilder();
			if( leftOper != null ) {
				sb.append(leftOper);
				sb.append(" ");
			}
			int i = 0;
			for( final Value value : values) {
				if( value != null ) {
					if( i++ > 0 ) {
						sb.append(" ");
						sb.append(mdlOper);
						sb.append(" ");
					}
					sb.append(value.toJava(pfx));
				}
			}
			if( rightOper != null ) {
				sb.append(" ");
				sb.append(rightOper);
			}
			return sb.toString();
		}
		return "";
	}

}
