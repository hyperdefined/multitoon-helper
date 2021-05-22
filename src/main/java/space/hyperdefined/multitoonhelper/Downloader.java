package space.hyperdefined.multitoonhelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Downloader {
	public static void downloadFile() throws IOException {
		URL website = new URL("https://danfresneda.com/tt/multicontroller/ToontownMulticontroller.exe");
		HttpURLConnection connection = (HttpURLConnection) website.openConnection();
		connection.setRequestMethod("GET");
		InputStream in = connection.getInputStream();
		File controller = new File("programs" + File.separator + "Controller.exe");
		File programs = new File("programs");
		if (!programs.exists()) {
			if (!programs.mkdirs()) {
				System.exit(1);
			}
		}
		FileOutputStream out = new FileOutputStream(controller);
		copy( in , out, 1024);
		out.close();
	}
	public static void copy(InputStream input, OutputStream output, int bufferSize) throws IOException {
		byte[] buf = new byte[bufferSize];
		int n = input.read(buf);
		while (n >= 0) {
			output.write(buf, 0, n);
			n = input.read(buf);
		}
		output.flush();
	}
}