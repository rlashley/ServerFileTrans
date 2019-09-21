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
	private Scanner input = null;
	private PrintWriter output = null; //Sends output from server to client app
	private String rawStringToParse = ""; //String that stores raw data first pulled from feedback.txt file
	private boolean keepRunning = true; //Variable to trigger shutdown of server
	private int serverPort;
    
	public static void main(String[] args) throws Exception {

		//Create Server class
		Server server = new Server();
		//This will be used to test client locally, removed later to be replaced with outside app.
		Client client = new Client();
		Scanner ServerInput = new Scanner(System.in);

		//User sets port that server will listen on
		System.out.println("Type the port on which the server will listen: ");
		server.serverPort = ServerInput.nextInt();

		//Wait for initial connection
		try {
			server.serverSocket = new ServerSocket(server.serverPort);
		}
		catch (IOException e) {
			System.err.println("Could not listen on port:" + server.serverPort + "Error: " + e);
			System.exit(1);
		}
		server.start(server.serverPort);

		//Launch server
		do{
        	server.listen();
		} while (server.keepRunning); //keepRunning is condition to not kill server
        
		server.stop();
	}

	//This method starts the server, makes connections
	private void start(int port) throws Exception{
		try {
			link = serverSocket.accept();
		}
		catch(IOException e) {
			System.err.println("Connection issue on serverSocket. Error: " + e);
			System.exit(1);
		}

		input = new Scanner(link.getInputStream()); //incoming data to server
		output = new PrintWriter(link.getOutputStream(),true); //outgoing data to client on other side
		System.out.println("Server started, waiting on port" + serverPort + "Input and Output ready");
    }

	private void listen() throws Exception{
		if(input!=null){		//Listen for handshake
			if(input.nextInt()==42){	//If incoming input = 42, kick out of loop and shutdown server
				keepRunning=false;
				return;
			}
			//When data arrives, take user name and password in encrypted format.

			// If user name and pass match, send back connection made message

			rawStringToParse = readTextFile();	//Read in feedback txt file.
			output.println(rawStringToParse);	//Send to connected device
		}
	}
    
	//This method reads in the text file
	private String readTextFile() throws IOException {
    	File file = new File("src/feedback.txt");
    	
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
	private void stop() {
        try {
            input.close();
            output.close();
            link.close();
            serverSocket.close();
            System.exit(1);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}

