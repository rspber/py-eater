package pyeater.code;

import java.util.ArrayList;
import java.util.List;

import pyeater.value.RecName;
import pyeater.value.AnyName;
import pyeater.value.Value;
import pyeater.value.ValueName;
import pyeater.value.ValueRecName;

public class CodeDeclare extends Code {

	public final AnyName name;
	public final Value type;
	public final Value defualt;

	public CodeDeclare(final Value name, final Value type, final Value defualt) {
		super(CaseCode.CodeDeclare);
		this.name = unwindName(name);
		this.type = type;
		this.defualt = defualt;
	}

	private static AnyName unwindName(Value expr) {
		final List<String> list = new ArrayList<>();
		while( expr instanceof ValueName ) {
			list.add( ((ValueName)expr).getName() );
			if( expr instanceof ValueRecName ) {
				expr = ((ValueRecName)expr).rec;
			}
			else {
				break;
			}
		}
		AnyName vName = null;
		for( int i = list.size(); --i >= 0;  ) {
			if( vName == null ) {
				vName = new AnyName(list.get(i));
			}
			else {
				vName = new RecName(list.get(i), vName);
			}
		}
		return vName;
	}

	@Override
	public String toJava(final String pfx) {
		return pfx + (type != null ? type.toJava(pfx) + " " : "") + (name != null ? name.toJava() : "") +
				(defualt != null ? " = " + defualt.toJava(pfx) : "") + ";";
	}

}
