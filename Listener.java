package webpageFeedback;

import java.net.*;
import java.io.*;

/*
 * I'm writing this program to build a tool for my web server. I currently have a little PHP running that saves comments on my site to
 * my linux web server. I'd like to be able to access these comments without logging into the web server, so I hope to build this program
 * to send the file to a corresponding app on my phone. I'll need to build a log in interface that encrypts the login, and an app for my 
 * phone to display the data.
 */

public class Listener {

    ServerSocket serverSocket = null;
    Socket clientSocket = null;
    String rawStringToParse = ""; //String that stores raw data first pulled from feedback.txt file
    
	public static void main(String[] args) throws Exception {

		//Create Listener class
        Listener server = new Listener();

        //Launch server
        server.start(3200);
        
        //Listen for handshake, when one arrives, take user name and password in encrypted format.

        
        //If user name and pass match, send back connection made message
        
        
        //Read in feedback txt file. 
        server.rawStringToParse = server.readTextFile();
        
        //Combine into JSON format and send to connected device
        
        //close connection
        server.stop();
	}
    private void start(int port) throws Exception{   	
        try {
        	serverSocket = new ServerSocket(port);
        } 
        catch (IOException e) {
        	System.err.println("Could not listen on port: 3200. Error: " + e);
        	System.exit(1);
        }    
        try {
        	clientSocket = serverSocket.accept();
        }
        catch(IOException e) {
        	System.err.println("Connection issue on serverSocket. Error: " + e);
        	System.exit(1);
        }
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                clientSocket.getInputStream()));
        String inputLine, outputLine;
    }
    
    public String readTextFile() throws IOException {
    	File file = new File("/var/www/html/feedback/feedback.txt");
    	
    	BufferedReader reader = new BufferedReader(new FileReader(file));
    	String documentText = "";
    	try {
			while(reader.readLine() != null) {
				documentText+=reader.readLine();
			}
		} catch (IOException e) {
			System.out.println(e);
		}
    	reader.close();
    	return documentText;
    }
    
	public void stop() throws Exception{
		in.close();
		reader.close();
	    clientSocket.close();
	    serverSocket.close();
	}
}

