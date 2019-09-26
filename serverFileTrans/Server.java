package serverFileTrans;

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

        //Prompt user for port server will run on
		Scanner scanner = new Scanner(System.in);
		System.out.println("Type port you would like to use.");
		server.port = scanner.nextInt();
		scanner.close();
		

		while (server.keepRunning) {
			try {
				//Wait for initial connection
				server.serverSocket = new ServerSocket(server.port);
				server.start(server.port);
			} catch (IOException e) {
				System.err.println("Could not listen on port: " + server.port + " Error: " + e);
				System.exit(1);
			}
		}
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

        PrintWriter output = new PrintWriter(link.getOutputStream(),true); //outgoing data to client on other side
		output.println("Connection successful, sending data now...");
		//Read in feedback txt file.
		rawStringToParse = readTextFile(); //String that stores raw data first pulled from feedback.txt file
		//Send to connected device
		output.println(rawStringToParse);

		//When data arrives, take user name and password in encrypted format.

		//If user name and pass match, send back connection made message

		InputStream raw = link.getInputStream();

        if(!(raw.read()==-1)){
			output.close();
			raw.close();
			keepRunning=false;
		}
	}
    
	//This method reads in the text file
	private String readTextFile() throws IOException {
		String path = "C:\\Users\\rlash\\Documents\\feedback.txt";
		path = path.replace("\\","/");
		File file = new File(path);
		Scanner sc = new Scanner(file);
		String documentText = "";

		try {
			while(sc.hasNextLine()) {
				documentText += (sc.nextLine()+"\n");
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		sc.close();
		return documentText;
	}
    
	//This method stops the server
	private void stop() throws Exception {
		link.close();
		serverSocket.close();
		System.exit(1);
	}
}

