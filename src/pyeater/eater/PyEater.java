package pyeater.eater;

import java.io.File;
import java.util.Scanner;

import pyeater.py.Directory;

public class PyEater {

	public int count;
	public int errcount;

	public void eatme(final Directory dir, final File file)
	{
		System.out.println(++count + ": " + file.getAbsolutePath());

		try {
			final Scanner scanner = new Scanner(file);
			scanner.useDelimiter("\r*\n");
			final PyParse pyParse = new PyParse(scanner);
			pyParse.readCode(-1);
		}
		catch( Exception e) {
			++errcount;
			System.out.println("Error: " + e.getMessage());
//			throw new RuntimeException(e);
		}

	}

	public void scan(final Directory dir, final File repo)
	{
		for( final File entry : repo.listFiles() ) {
			if( entry.isDirectory() ) {
				scan(dir.mkdir(entry.getName()), entry);
			}
			else {
				if( entry.isFile() ) {
					final String fname = entry.getName();
					if( fname.endsWith(".py") ) {
						eatme(dir, entry);
					}
				}
			}
		}
	}

}