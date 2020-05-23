package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TextureFolderMover {

	public static void copyFolder(File srcFolder, File destFolder) {
		try {
			TextureFolderMover.copyFolderIntern(srcFolder, destFolder);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void moveFolderOld(File destFolder, File srcFolder) {
		try {
			TextureFolderMover.copyFolderIntern(srcFolder, destFolder);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void copyFolderIntern(File source, File dest) throws IOException {
		if (source.isDirectory()) {
			if (!dest.exists()) {
				dest.mkdir();
			}

			String files[] = source.list();

			for (String file : files) {
				File srcFile = new File(source, file);
				File destFile = new File(dest, file);
				TextureFolderMover.copyFolderIntern(srcFile, destFile);
			}
		} else {
			InputStream in = new FileInputStream(source);
			OutputStream out = new FileOutputStream(dest);

			byte[] buffer = new byte[1024];

			int length;
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}
			in.close();
			out.close();
		}
	}
}
