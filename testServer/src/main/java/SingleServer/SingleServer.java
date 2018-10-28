package SingleServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SingleServer {
    public static void main(String[] args) throws InterruptedException {
        try(ServerSocket server = new ServerSocket(3345)) {
            Socket client = server.accept();

            System.out.print("Connection accepted.");

            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            System.out.println("DataOutputStream  created");

            DataInputStream in = new DataInputStream(client.getInputStream());
            System.out.println("DataInputStream created");

            while (!client.isClosed()) {
                System.out.println("SingleServer.SingleServer reading from channel");

                String entry = in.readUTF();

                System.out.println("READ from client message - " + entry);
                System.out.println("SingleServer.SingleServer try writing to channel");

                if (entry.equalsIgnoreCase("quit")) {
                    System.out.println("Client.Client initialize connections suicide ...");
                    out.writeUTF("SingleServer.SingleServer reply - " + entry + " - OK");
                    out.flush();
                    Thread.sleep(3000);
                    break;
                }

                out.writeUTF("SingleServer.SingleServer reply - " + entry + " - OK");
                System.out.println("SingleServer.SingleServer Wrote message to client.");

                out.flush();
            }

            System.out.println("Client.Client disconnected");
            System.out.println("Closing connections & channels.");

            in.close();
            out.close();

            client.close();

            System.out.println("Closing connections & channels - DONE.");
        } catch (IOException error) {
            error.printStackTrace();
        }
    }
}
