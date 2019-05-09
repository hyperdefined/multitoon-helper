package space.hyperdefined.multitoonhelper.versionchecker;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;

import space.hyperdefined.multitoonhelper.reference.Reference;

public class VersionChecker implements Runnable {
	public static String latestVersion = "";
	public static boolean isLatestVesion;

	public VersionChecker() {}

	public void run() {
		InputStream in =null;
		try { in =new URL("https://raw.githubusercontent.com/hypertjs/multitoon-helper/master/version").openStream();

		}
		catch(MalformedURLException e) {
			latestVersion = Reference.version;
		}
		catch(IOException e) {
			latestVersion = Reference.version;
		}
		try {
			latestVersion = (String) IOUtils.readLines( in ).get(0);
		}
		catch(IOException e) {
			latestVersion = Reference.version;
		}
		finally {
			IOUtils.closeQuietly( in );
		}
	}
	public static String getLatestVersion() {
		return latestVersion;
	}
}