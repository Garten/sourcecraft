package periphery;

import java.io.File;
import java.io.FilenameFilter;

public class DirectoryFilter implements FilenameFilter {

	@Override
	public boolean accept(File dir, String name) {
		return new File(dir, name).isDirectory();
	}

}
