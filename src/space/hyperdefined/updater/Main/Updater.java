package space.hyperdefined.updater.Main;

import java.io.BufferedInputStream;
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
import java.util.Scanner;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
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
		  
		  public static void listFilesForFolder(final File folder) throws Exception {
			    for (final File fileEntry : folder.listFiles()) {
			        if (fileEntry.isDirectory()) {
			        	
			            listFilesForFolder(fileEntry);
			            
			        } else {
			        	
			            //System.out.println(fileEntry.getName());
			    		JSONObject json = readJsonFromUrl(Reference.UPDATE_URL);
			    		
			    		String content = new String (fileEntry.getName());
			    		
			    		if(json.has(content)){
			    			
				    		JSONObject filename = json.getJSONObject(content);
			    		    String hash = filename.getString("hash");
			    		    
			    		    String dl = filename.getString("dl");
			    		   
			                InputStream is = new FileInputStream(fileEntry);

			                String digest = DigestUtils.shaHex(is);  
			                
			                File dir = new File("temp");
		                	dir.mkdir();
			                
			                if (!hash.equals(digest))
			                {
			                	System.out.println(content + " is out of date. Found " + digest + " but expected " + hash);
			                	
			                	String patchURL = Reference.PATCHES_URL + dl;
			                    
			                    try {
			                        downloadUsingNIO(patchURL, "temp/" + dl);
			                        
			                        downloadUsingStream(patchURL, "temp/" + dl);
			                        
			                        System.out.println("Downloading file " + content + ".");
			                        
			                        FileInputStream in = new FileInputStream("temp/" + dl);
			                        FileOutputStream out = new FileOutputStream(Reference.INSTALL_DIR + content);
			                        BZip2CompressorInputStream bzIn = new BZip2CompressorInputStream(in);
			                        byte[] buffer = new byte[5];
			                        int n = 0;
			                        while (-1 != (n = bzIn.read(buffer))) {
			                          out.write(buffer, 0, n);
			                        }
			                        out.close();
			                        bzIn.close();
			                        
			                        File deleteTemp = new File("temp/" + dl);
			                        
			                        deleteTemp.delete();
			                        System.out.println("Done downloading " + content + ".");
			                        
			                        
			                    } catch (IOException e) {
			                        e.printStackTrace();
			                    }
			                    
			                    
			                    
			                }
			                
			    		    
			    		} else {
			    		    // It doesn't exist, do nothing 
			    		}
					
			        	}
			        }
			    }
			private static void downloadUsingStream(String urlStr, String file) throws IOException{
		        URL url = new URL(urlStr);
		        BufferedInputStream bis = new BufferedInputStream(url.openStream());
		        FileOutputStream fis = new FileOutputStream(file);
		        byte[] buffer = new byte[1024];
		        int count=0;
		        while((count = bis.read(buffer,0,1024)) != -1)
		        {
		            fis.write(buffer, 0, count);
		        }
		        fis.close();
		        bis.close();
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
		
		File locationTXT = new File("TTRLocation.txt");
		
		try {

	        Scanner sc = new Scanner(locationTXT);

	        while (sc.hasNextLine()) {
	            Reference.INSTALL_DIR= sc.nextLine();
	        }
	        sc.close();
	    } 
	    catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
		
		final File folder = new File(Reference.INSTALL_DIR);
		listFilesForFolder(folder);
		
		System.out.println("Finished checking for updates!");
		System.exit(0);
	}
}