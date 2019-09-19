package webpageFeedback;

import java.net.*;
import java.util.Scanner;
import java.io.*;

/*
 * I'm writing this program to build a tool for my web server. I currently have a little PHP running that saves comments on my site to
 * my linux web server. I'd like to be able to access these comments without logging into the web server, so I hope to build this program
 * to send the file to a corresponding app on my phone. I'll need to build a log in interface that encrypts the login, and an app for my 
 * phone to display the data.
 */

public class Server {

	ServerSocket serverSocket = null;
	Socket link = null;
	Scanner input = null;
	PrintWriter output = null; //Sends output from server to client app
	String rawStringToParse = ""; //String that stores raw data first pulled from feedback.txt file
	bool keepRunning = true; //Variable to trigger shutdown of server
    
	public static void main(String[] args) throws Exception {

		//Create Server class
        	Server server = new Server();
		//This will be used to test client locally, removed later to be replaced with outside app.
        	Client client = new Client();
	
		//Wait for initial connection
        	try {
        		serverSocket = new ServerSocket(port);
        	} 
        	catch (IOException e) {
        		System.err.println("Could not listen on port: 3200. Error: " + e);
			System.exit(1);
        	}

        	//Launch server
		do{
        	server.start(3200);
		} while (keepRunning); //Need to create condition where variable switches to turn off server
        
		server.stop();       
	}
	
	//This method starts the server, makes connections
	public void start(int port) throws Exception{   	
        
        	try {
        		link = serverSocket.accept();
        	}
        	catch(IOException e) {
        		System.err.println("Connection issue on serverSocket. Error: " + e);
			System.exit(1);
        	}             
        
        	input = new Scanner(link.getInputStream()); //incoming data to server
        	output = new PrintWriter(link.getOutputStream(),true); //outgoing data to client on other side
        	System.out.println("Server started, waiting on port 3200. Input and Output ready");
	    
		//Listen for handshake
        	if(input!=null){
		//When data arrives, take user name and password in encrypted format.
		
        	//If user name and pass match, send back connection made message  
	
		//Read in feedback txt file.       
        	server.rawStringToParse = server.readTextFile();
		
		//Send to connected device
        	server.output.println(server.rawStringToParse);
		}
    	}
    
	//This method reads in the text file
	public String readTextFile() throws IOException {
    		File file = new File("C:\feedback.txt");
    	
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
    
    	//This method stops the server
	public void stop() throws Exception {
		input.close();
		output.close();
		link.close();
		serverSocket.close();
		System.exit(1);
	}
}

