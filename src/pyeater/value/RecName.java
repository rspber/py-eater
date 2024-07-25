package pyeater.value;

public class RecName extends AnyName {

	// !!! change to setter/getter !!!
	public final String[] tt;	// comma names in ascending order

	public RecName(final String nxtName, final AnyName vName) {
		super(nxtName);
		this.tt = new String[vName.recLen() + 1];
		if( vName instanceof RecName ) {
			final RecName t = (RecName)vName;
			System.arraycopy(t.tt, 0, this.tt, 0, t.tt.length);
		}
		this.tt[vName.recLen()] = vName.getName();
	}

	@Override
	public int recLen() {
		return tt.length;
	}

	@Override
	public String toJava() {
		final StringBuilder sb = new StringBuilder();
		for( final String t : tt ) {
			sb.append(t);
			sb.append('.');
		}
		sb.append(getName());
		return sb.toString();
	}

}
