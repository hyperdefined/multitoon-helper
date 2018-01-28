package space.hyperdefined.multitoonhelper.downloader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Downloader {

	public static boolean doesFileExist;

	public static void doesFileExist() {
		File f = new File("programs\\Controller.exe");
		if (f.exists()) {
			doesFileExist = true;
		}
	}

	public static void downloadFile() throws IOException {
		File directory = new File("\\programs\\Controller.exe");
		URL website = new URL("https://danfresneda.com/tt/multicontroller/ToontownMulticontroller_v1.0.9.exe");
		HttpURLConnection connection = (HttpURLConnection) website.openConnection();
		connection.setRequestMethod("GET");
		InputStream in =connection.getInputStream();
		FileOutputStream out = new FileOutputStream("programs\\Controller.exe");
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