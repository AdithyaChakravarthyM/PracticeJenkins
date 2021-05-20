import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.HTTP;
import org.json.JSONObject;

public class jsonparser {
	Logger logger = Logger.getLogger(jsonparser.class.getName());
    String pageid = new String();
    ArrayList<String> seealso = new ArrayList<String>();
    public String getData(String Url)  
    {
        try
        {
		    URL url = new URL(Url);
		    HttpURLConnection http = (HttpURLConnection)url.openConnection();
			http.setRequestMethod("GET");
			http.connect();
			BufferedReader br = new BufferedReader(new InputStreamReader(http.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line;

			while((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			br.close();
			Pattern pattern = Pattern.compile(Pattern.quote("pageid\":") + "(.*?)" + Pattern.quote(",\"ns"));
			Matcher matcher = pattern.matcher(sb);

			while (matcher.find()) {
				pageid = matcher.group(1);
			}
			pattern = Pattern.compile(Pattern.quote("\\n==See also==\\n") + "(.*?)" + Pattern.quote("\\n\\n"),Pattern.DOTALL);
			matcher = pattern.matcher(sb);
			while (matcher.find()) {
				String res = new String();
				res =  matcher.group(0);
				pattern = Pattern.compile(Pattern.quote("[") + "(.*?)" + Pattern.quote("]"),Pattern.DOTALL);
				matcher = pattern.matcher(res);
				while(matcher.find()) {
					String addurl = matcher.group(0).replace(" ", "_");
					seealso.add(addurl);
				}
			}
			String filename = "output.txt";
			File myObj = new File(filename);
            FileMaker(myObj,filename);
            return "parse success";
        }
        catch(MalformedURLException e)
        {
        	 return "Error in  url ";
        }
        catch (IOException e) 
        {
        	 return "error with url data";
           
        }
    }
    public String FileMaker(File myObj,String filename)
    {
        try 
        {
            if (myObj.createNewFile()) 
            {
                logger.fine("File created");
            } 
            else 
            {
                logger.info("File already exists");
            }
            FileWriter myWriter = new FileWriter(filename);
            myWriter.write("Page Id : "+String.valueOf(pageid));
            myWriter.write("\nSee Also  : "+ seealso);
            myWriter.close();
            return "File Created Successfully";
        } 
        catch (IOException e) 
        {
            logger.severe("Error occured while creating file");
            e.printStackTrace();
            return "Error occured while creating file";
        }
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		jsonparser j = new jsonparser();
		String url="https://en.wikipedia.org/w/api.php?format=json&action=query&titles=SMALL&prop=revisions&rvprop=content";
		j.getData(url);
		
		System.out.println("pageid:"+j.pageid);
		System.out.println("see also :"+j.seealso);
	}

}
