package serverFileTrans;

import java.net.*;
import java.io.*;

public class Client {

    public static void main(String[] args) throws Exception {

        int port = 3200;

            InetAddress server = InetAddress.getByName("localhost");

            try {
                System.out.println("Connecting" + " on port " + port);
                Socket client = new Socket(server, port);

                System.out.println("Just connected to " + client.getRemoteSocketAddress());
                OutputStream outToServer = client.getOutputStream();
                DataOutputStream out = new DataOutputStream(outToServer);

                out.writeUTF("Hello");
                InputStream inFromServer = client.getInputStream();
                DataInputStream in = new DataInputStream(inFromServer);

                System.out.println("Server says " + in.readUTF());
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}