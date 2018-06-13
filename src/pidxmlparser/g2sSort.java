package pidxmlparser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class G2sSort {
	
	
    private String g2sMsgFile;
    private String startSearch;
    private String endSearch;
    private String g2sfile;

	
	// Pulls the g2s log into a string so we can search it
	public void G2sMsgFile (String g2sMsgFileArg) throws IOException {		
		this.g2sMsgFile = g2sMsgFileArg;		
		g2sfile = new String(Files.readAllBytes(Paths.get(g2sMsgFile)));			
	}
	
	

	// The actual searching for xml patterns 
	public ArrayList<String> G2sSearch (String start, String end) {
		this.startSearch = start;
		this.endSearch = end;				
		
		ArrayList<String> g2sMsgArray = new ArrayList<String>();
		g2sMsgArray.add("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"); // Add XML header and main element so the FormatXml class can read it
		g2sMsgArray.add("<allmessages>");
				
		
		Pattern p = Pattern.compile(Pattern.quote(startSearch)  + ".*?" + Pattern.quote(endSearch));
		Matcher m = p.matcher(g2sfile);
		
		while (m.find()) {				
		g2sMsgArray.add(m.group());
	   // g2sMsgArray.add(" \r\n");
		}		
		
		g2sMsgArray.add("</allmessages>"); // Add closing main XML element 
		
		return(g2sMsgArray);
		
	}
	
}


