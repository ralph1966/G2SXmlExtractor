package pidxmlparser;

import java.io.File;
import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintStream;

public class ExtractXml {
	
	public static void main(String[] args) throws IOException {
		
	// XML files for final results
	String g2sfileXml = "c:\\tmp\\g2sLogExtractor\\G2SMessages.xml";
	String nsfileXml = "c:\\tmp\\g2sLogExtractor\\NS1Messages.xml";
	String puifileXml = "c:\\tmp\\g2sLogExtractor\\PUIMessages.xml";
				
	String g2sSourcefile = "c:\\tmp\\g2sLogExtractor\\G2Slog.txt";  // g2s log from a terminal 
	String searchingStartns1 = "<ns1:g2sMessage"; // XML element to start pattern check ,NS1 messages
	String searchingStartng2s = "<g2s:g2sMessage"; // XML element to start pattern check, G2S messages
	String searchingStartpui = "<igtMediaDisplay:mdMsg"; // XML element to start pattern check, PUI messages
	String endsearching = ":g2sMessage>"; // XML element to end pattern check
	String puiendsearching = "igtMediaDisplay:mdMsg>"; // XML element to end PUI pattern check	
	
	System.out.println("Starting XML extraction......");
	PrintStream console = System.out;		
	
	/**
	* 1st part of extraction , XML elements from g2s log file to ArrayLists
	* 
	* 
    *
    */ 	
	
	// Create new G2sSort object and extract ns1, ns2 and pui type messages, these include PUIs 
	G2sSort g2ssortns = new G2sSort();
	
	// Set G2S log file
    g2ssortns.G2sMsgFile(g2sSourcefile);
	
	// Extract the normal G2S type messages from the above g2sSort object
	ArrayList<String> g2smessages = g2ssortns.G2sSearch(searchingStartng2s,endsearching);
		
    // Extract the normal NS1 type messages from the above g2sSort object
	ArrayList<String> ns1messages = g2ssortns.G2sSearch(searchingStartns1,endsearching);
	
	// Extract the normal PUI type messages from the above g2sSort object
	ArrayList<String> puimessages = g2ssortns.G2sSearch(searchingStartpui,puiendsearching);
	
	/**
	* 2nd part of extraction , results to XML files
	* 
	* 
	*
	*/ 
    
    // Join ArrayLits into a string for each one, adding new lines to make it more readable
	String g2sfileString = String.join("\n\n", g2smessages);
	String ns1fileString  = String.join("\n\n", ns1messages);
	String puifileString = String.join("\n\n", puimessages);

	// Create new printstream object and set XML output file for g2s messages.
	PrintStream g2s1messagesOut = new PrintStream(new File(g2sfileXml));
	System.setOut(g2s1messagesOut);	
	
    // Create new formmatter object and first run on g2s messages
	FormatXml formatter = new FormatXml();	
	System.out.println(formatter.format(g2sfileString)); // Format g2s messages 
	System.out.flush();
	g2s1messagesOut.close();	
	
	// Create new printstream to XML output file for ns1 messages	
    PrintStream ns1messagesOut = new PrintStream(new File(nsfileXml));
	System.setOut(ns1messagesOut);
		
	// run formatter again for ns1 messages
	System.out.println(formatter.format(ns1fileString)); // Format ns1 messages
	System.out.flush();
	ns1messagesOut.close();
	
	// Create new printstream to XML output file for PUI messages	
    PrintStream puimessagesOut = new PrintStream(new File(puifileXml));
	System.setOut(puimessagesOut);
	System.out.println(formatter.format(puifileString)); // Format PUI messages
	System.out.flush();
	puimessagesOut.close();
	
	// Final message back to console
	System.setOut(console);
	System.out.println("Finished XML extraction and sent to file......");	

   }
	
}