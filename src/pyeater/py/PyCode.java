package pyeater.py;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pyeater.code.Code;
import pyeater.code.CodeClass;
import pyeater.code.CodeFunc;
import pyeater.code.CodeImport;
import pyeater.util.IterAll;
import pyeater.util.Str;
import pyeater.value.AnyName;
import pyeater.value.RecName;
import pyeater.value.Value;
import pyeater.value.ValueName;
import pyeater.value.ValueOfName;

public class PyCode {

	public final static String THIS = "this";

	public final String name;
	public final List<Code> tmpCodeList;
	public final List<Code> innport;
	public final List<Code> body;

	public PyCode(final String name, final List<Code> codeList) {
		this.name = name.endsWith(".py") ? name.substring(0, name.length() - 3) : name;
		this.tmpCodeList = codeList;
		this.innport = new ArrayList<>();
		this.body = new ArrayList<>();
	}

	void separate_imports() {
		for( final Code code : tmpCodeList ) {
			if( code instanceof CodeImport ) {
				innport.add(code);
			}
			else {
				body.add(code);
			}
		}
	}

	void create_file_main_class() {
		// try_to_find_file_main_class
		CodeClass fileClass = null;
		for( final Code code : body ) {
			if( code instanceof CodeClass ) {
				final CodeClass c = (CodeClass)code;
				if( Str.eq(c.name, name) ) {
					fileClass = c;
					break;
				}
			}
		}

		// create main file class if not exists
		if( fileClass == null ) { 
			fileClass = new CodeClass(null, name, null, null);
			body.add(fileClass);
		}

		// leave on top classes only
		for( int i = body.size(); --i >= 0; ) {
			final Code code = body.get(i);
			if( code == fileClass ) {
				continue;
			}
			if( code instanceof CodeClass ) {
			}
			else {
				fileClass.codeList.add(0, body.remove(i));
			}
		}
	}

	void clean_up_self_parameter() {
		new IterAll() {

			int inClass;

			@Override
			public void iterCodeClass(final CodeClass code) {
				++inClass;
				super.iterCodeClass(code);
				--inClass;
			}
		}.iterCodes(body);
	}

	void self_to_this() {
		new IterAll() {

			private String self;

			private void procValueName(final ValueName value) {
				if( Str.eq(value.getName(), self) ) {
					value.setName(THIS);
				}
			}

			private void procAnyName(final AnyName obj) {
				if( obj instanceof RecName ) {
					final RecName rec = (RecName)obj;
					if( Str.eq(rec.tt[0], self)) {
						rec.tt[0] = THIS;
					}
				}
				else {
					if( Str.eq(obj.getName(), self)) {
						obj.setName(THIS);
					}
				}
			}

			@Override
			public void procede(final Value value) {
				switch( value.type ) {
				case ValueName:
					procValueName((ValueName)value);
					break;
				case ValueOfName:
					procAnyName(((ValueOfName)value).name);
					break;
				case ValueQuoted:
				case ValueNumber:
				case ValueNo:
				case Value3Dot:
					break;
				default:
					int i = 0;
				}
			}

			@Override
			public void iterCodeFunc(final CodeFunc code) {
				final String tmp = this.self;
				this.self = code.self;
				super.iterCodeFunc(code);
				this.self = tmp;
			}

		}.iterCodes(body);
	}

	public void initial_converts() {
		separate_imports();
		tmpCodeList.clear();
		create_file_main_class();
		clean_up_self_parameter();
		self_to_this();
	}

	public void toJava(final File path, final String pakkage) {
		try {
			final String njava = name + ".java"; 
			final FileWriter w = new FileWriter(new File(path, njava));
			w.write("package " + pakkage + ";\n\n");

			for( final Code code : innport ) {
				w.write(code.toJava(""));
				w.write("\n");
			}
			w.write("\n");

			for( final Code code : body ) {
				w.write(code.toJava(""));
				w.write("\n");
			}
			w.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
