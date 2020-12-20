package space.hyperdefined.multitoonhelper.accountmanager;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class AccountManager {

	public static List <String> usernames = new ArrayList<>();
	public static List <String> passwords = new ArrayList<>();
	public static List <String> labels = new ArrayList<>();

	static String readFile() throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get("config" + File.separator + "accounts.json"));
		return new String(encoded, StandardCharsets.UTF_8);
	}

	public static void getAccountsUsernames() throws JSONException {
		try {
			String content = readFile();
			JSONObject json = new JSONObject(content);
			usernames = new ArrayList<>();
			for (int i = 0; i < json.length(); i++) {
				JSONObject user = json.getJSONObject(i + "");
				usernames.add(user.getString("username"));
			}
		} catch(JSONException | IOException ex) {
			System.exit(1);
		}
	}
	public static void getAccountsPasswords() throws JSONException {
		try {
			String content = readFile();
			JSONObject json = new JSONObject(content);
			passwords = new ArrayList<>();
			for (int i = 0; i < json.length(); i++) {
				JSONObject user = json.getJSONObject(i + "");
				passwords.add(user.getString("password"));
			}
		}
		catch(JSONException | IOException ex) {
			System.exit(1);
		}
	}
	public static void getAccountsLabels() throws JSONException {
		try {
			String content = readFile();
			JSONObject json = new JSONObject(content);
			labels = new ArrayList<>();
			for (int i = 0; i < json.length(); i++) {
				JSONObject user = json.getJSONObject(i + "");
				labels.add(user.getString("label"));
			}
		}
		catch(JSONException | IOException ex) {
			System.exit(1);
		}
	}
}