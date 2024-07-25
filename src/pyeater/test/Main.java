package pyeater.test;

import java.io.File;

import pyeater.eater.PyEater;
import pyeater.py.Directory;

public class Main {

	public static void main(final String[] args) {

		if( args.length < 1 ) {
			System.out.println("No repository to eat given");
			return;
		}

		final File file = new File(args[0]);
		if( !file.exists() ) {
			System.out.println("Directory: " + args[0] + " not exists.");
			return;
		}
		final Directory root = new Directory(null, "org.py");	// (..., package)
		final PyEater pyEater = new PyEater();

		// scan for all repository files
		pyEater.scan(root, file);

		// print scanned files statistics
		if( pyEater.errcount > 0 ) {
			System.out.println("Finished with: " + pyEater.errcount + " errors.");
		}
		else {
			System.out.println("Finished, no errors found.");
		}

		// produce output files
		if( args.length > 1 ) {

			root.initial_converts();

			root.toJava(new File(args[1]));
		}
	}

}
