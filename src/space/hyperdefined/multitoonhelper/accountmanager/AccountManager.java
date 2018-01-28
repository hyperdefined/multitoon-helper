package space.hyperdefined.multitoonhelper.accountmanager;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import space.hyperdefined.multitoonhelper.window.MainWindow;

public class AccountManager {

	public static List < String > usernames = new ArrayList < String > ();
	public static List < String > passwords = new ArrayList < String > ();

	public static boolean jsonError;
	public static boolean fileNotFound;
	public static boolean programLoads = true;

	static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	public static void getAccountsUsernames() throws JSONException,
	IOException {
		try {
			String content = readFile("config/accounts.json", StandardCharsets.UTF_8);
			JSONObject json = new JSONObject(content);
			usernames = new ArrayList < >();
			for (int i = 0; i < json.length(); i++) {
				JSONObject user = json.getJSONObject(i + "");
				usernames.add(user.getString("username"));
			}
		} catch(JSONException ex) {
			usernames.clear();
			MainWindow.logger.warning("Error reading accounts.json. Check the formatting.");
			jsonError = true;
			programLoads = false;
		}
		catch(IOException ex) {
			usernames.clear();
			MainWindow.logger.warning("The file account.json was not found.");
			fileNotFound = true;
			programLoads = false;
		}
	}
	public static void getAccountsPasswords() throws JSONException,
	IOException {
		try {
			String content = readFile("config/accounts.json", StandardCharsets.UTF_8);
			JSONObject json = new JSONObject(content);
			passwords = new ArrayList < >();
			for (int i = 0; i < json.length(); i++) {
				JSONObject user = json.getJSONObject(i + "");
				passwords.add(user.getString("password"));
			}
		}
		catch(JSONException ex) {
			passwords.clear();
			MainWindow.logger.warning("Error reading accounts.json. Check the formatting.");
			jsonError = true;
			programLoads = false;
		}
		catch(IOException ex) {
			passwords.clear();
			MainWindow.logger.warning("The file account.json was not found.");
			fileNotFound = true;
			programLoads = false;
		}
	}
}