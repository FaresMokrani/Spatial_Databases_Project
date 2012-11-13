import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Run {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		URL seed, toVisit;
		try {
			seed = new URL("http://www.udacity.com/cs101x/index.html");
			
			Crawling(seed);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	//Crawling Method
	
	public static void Crawling (URL seed ) {
		HashSet<String> crawledList = new HashSet<String>();
		LinkedHashSet<String> toCrawlList = new LinkedHashSet<String>();
		
		String tempString = "";
		StringBuffer pageContents = new StringBuffer();
		
		toCrawlList.add(seed.toString());
		
		try {
			while(toCrawlList.size()>0 && !crawledList.contains(toCrawlList.iterator().next()))
			{
				pageContents = new StringBuffer();
				String url = (String) toCrawlList.iterator().next();
				URL toVisit = new URL (url);
				toCrawlList.remove(url);
				crawledList.add(url);

				BufferedReader buffer = new BufferedReader(new InputStreamReader(toVisit.openStream()));
				while((tempString = buffer.readLine()) != null)
					pageContents.append(tempString);
				
				System.out.println(pageContents.toString());
				
				//get all the links of the actual page
				ArrayList<String> allLinks = fetchLinks(pageContents.toString());
				toCrawlList.addAll(allLinks);
				System.out.println(toCrawlList);
				System.out.println("I dont understand");
			}
			
			//Printing the list of the visited Links
			System.out.println("The Visited links are : \n");
			System.out.println(crawledList);
				
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	//Fetching Links Method
	
	public static ArrayList<String> fetchLinks(String pageContent/*, HashSet crawledList*/){
		
		Pattern linkPattern = Pattern.compile("<a\\s+href\\s*=\\s*\"?(.*?)[\"|>]", Pattern.CASE_INSENSITIVE);
		Matcher linkMatch = linkPattern.matcher(pageContent);
		ArrayList<String> linksList = new ArrayList<String>();
		
		while(linkMatch.find())
		{
			String link = linkMatch.group(1).trim();
			linksList.add(link);
		}
		
		return linksList;
	}

}
