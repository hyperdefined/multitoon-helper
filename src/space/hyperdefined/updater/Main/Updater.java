package space.hyperdefined.updater.Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Updater {

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    private static ArrayList < String > getOnlineFiles() throws JSONException, IOException {
        JSONObject json = readJsonFromUrl(Reference.UPDATE_URL);
        JSONArray onlineFiles = json.names();

        ArrayList < String > list = new ArrayList < String > ();
        if (onlineFiles != null) {
            int len = onlineFiles.length();
            for (int i = 0; i < len; i++) {
                list.add(onlineFiles.get(i).toString());
            }
        }

        return list;
    }

    private static void checkFiles() throws JSONException, IOException {
        ArrayList < String > onlineFiles = getOnlineFiles();

        JSONObject json = readJsonFromUrl(Reference.UPDATE_URL);

        for (int i = 0; i < onlineFiles.size(); i++) {
            String currentFile = onlineFiles.get(i).toString();
            JSONObject fileName = json.getJSONObject(currentFile);

            JSONArray arch = fileName.getJSONArray("only");

            ArrayList < String > type = new ArrayList < String > ();
            if (arch != null) {
                int length = arch.length();
                for (int k = 0; k < length; k++) {
                    type.add(arch.get(k).toString());
                }
            }

            ArrayList < String > checkFiles = new ArrayList < String > ();
            if (type.contains("win32") || type.contains("win64")) {
                checkFiles.add(fileName.toString());

                File locationTXT = new File("TTRLocation.txt");

                try {
                    Scanner sc = new Scanner(locationTXT);

                    while (sc.hasNextLine()) {
                        Reference.INSTALL_DIR = sc.nextLine();
                    }
                    sc.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            for (int o = 0; o < checkFiles.size(); o++) {
                try {
                    InputStream is = new FileInputStream(Reference.INSTALL_DIR + currentFile);
                    String localHash = DigestUtils.shaHex(is);

                    String onlineHash = fileName.getString("hash");
                    String downloadName = fileName.getString("dl");

                    if (onlineHash.equals(localHash)) {
                        System.out.println(currentFile + " is update to date!");
                    } else {
                        System.out.println(currentFile + " is outdated! Downloading...");

                        String patchURL = Reference.PATCHES_URL + downloadName;

                        downloadUsingNIO(patchURL, "temp/" + downloadName);

                        FileInputStream in = new FileInputStream("temp/" + downloadName);
                        FileOutputStream out = new FileOutputStream(Reference.INSTALL_DIR + currentFile);
                        BZip2CompressorInputStream bzIn = new BZip2CompressorInputStream( in );
                        byte[] buffer = new byte[1024 * 8];
                        int n = 0;
                        while (-1 != (n = bzIn.read(buffer))) {
                            out.write(buffer, 0, n);
                        }
                        out.close();
                        bzIn.close();

                        File deleteTemp = new File("temp/" + downloadName);

                        deleteTemp.delete();
                        System.out.println("Done downloading " + currentFile + ".");

                    }
                } catch (FileNotFoundException e) {
                    System.out.println(currentFile + " was not found. Downloading...");

                    String downloadName = fileName.getString("dl");

                    String patchURL = Reference.PATCHES_URL + downloadName;

                    downloadUsingNIO(patchURL, "temp/" + downloadName);

                    FileInputStream in = new FileInputStream("temp/" + downloadName);
                    FileOutputStream out = new FileOutputStream(Reference.INSTALL_DIR + currentFile);
                    BZip2CompressorInputStream bzIn = new BZip2CompressorInputStream( in );
                    byte[] buffer = new byte[1024 * 8];
                    int n = 0;
                    while (-1 != (n = bzIn.read(buffer))) {
                        out.write(buffer, 0, n);
                    }
                    out.close();
                    bzIn.close();

                    File deleteTemp = new File("temp/" + downloadName);

                    deleteTemp.delete();
                    System.out.println("Done downloading " + currentFile + ".");
                }
            }
        }
    }

    private static void downloadUsingNIO(String urlStr, String file) throws IOException {
        URL url = new URL(urlStr);
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(file);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
    }

    public static void main(String[] args) throws Exception {

        System.out.println("Checking for TTR updates...");

        checkFiles();

        System.out.println("Finished checking for updates!");

        TimeUnit.SECONDS.sleep(5);

        System.exit(0);
    }
}