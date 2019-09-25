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

	private ServerSocket serverSocket = null;
	private Socket link = null;
	private boolean keepRunning = true; //Variable to trigger shutdown of server
    private int port;
    
	public static void main(String[] args) throws Exception {

		//Create Server class
        Server server = new Server();
		//This will be used to test client locally, removed later to be replaced with outside app.
        Client client = new Client();

        //Prompt user for port server will run on
		Scanner scanner = new Scanner(System.in);
		System.out.println("Type port you would like to use.");
		server.port = scanner.nextInt();
		scanner.close();
		
		//Wait for initial connection
        try {
            server.serverSocket = new ServerSocket(server.port);
        }
        catch (IOException e) {
            System.err.println("Could not listen on port: " + server.port + " Error: "+ e);
            System.exit(1);
        }
		
        //Launch server
		do{
        	server.start(server.port);
		} while (server.keepRunning); //Need to create condition where variable switches to turn off server
        
		server.stop();       
	}
	
	//This method starts the server, makes connections
	private void start(int port) throws Exception{
        String rawStringToParse="";

        try {
        	link = serverSocket.accept();
        }
        catch(IOException e) {
        	System.err.println("Connection issue on serverSocket. Error: " + e);
		System.exit(1);
        }

        Scanner input = new Scanner(link.getInputStream());
        PrintWriter output = new PrintWriter(link.getOutputStream(),true); //outgoing data to client on other side
        System.out.println("Server started, waiting on port 3200. Input and Output ready");

		//Listen for handshake
        if(input.hasNext()){
            if(input.nextInt()==42){
                keepRunning=false;
                return;
            }
            //When data arrives, take user name and password in encrypted format.

            //If user name and pass match, send back connection made message

            //Read in feedback txt file.
            rawStringToParse = readTextFile(); //String that stores raw data first pulled from feedback.txt file

            //Send to connected device
            output.println(rawStringToParse);

            input.close();
            output.close();
		    }
    	}
    
	//This method reads in the text file
	private String readTextFile() throws IOException {
    		File file = new File("C:\feedback.txt");
    	
    		BufferedReader reader = new BufferedReader(new FileReader(file));
    		String documentText = "";
    		try {
			while(reader.readLine() != null) {
				documentText+=reader.readLine();
			}
			} catch (IOException e) {
				System.err.println(e);
			}
    		reader.close();
    		return documentText;
    	}
    
    	//This method stops the server
	private void stop() throws Exception {
		link.close();
		serverSocket.close();
		System.exit(1);
	}
}

