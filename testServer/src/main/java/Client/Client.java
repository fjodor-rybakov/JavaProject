package Client;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws InterruptedException {
        try (
                Socket socket = new Socket("localhost", 3345);
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
                DataInputStream ois = new DataInputStream(socket.getInputStream())
        ) {
            System.out.println("Client.Client connected to socket.");
            System.out.println();
            System.out.println("Client.Client writing channel = oos & reading channel = ois initialized.");

            while(!socket.isOutputShutdown()) {
                if (br.ready()) {
                    System.out.println("Client.Client start writing in channel...");
                    Thread.sleep(1000);
                    String clientCommand = br.readLine();

                    oos.writeUTF(clientCommand);
                    oos.flush();
                    System.out.println("Clien sent message " + clientCommand + " to server.");
                    Thread.sleep(1000);

                    if (clientCommand.equalsIgnoreCase("quit")){
                        System.out.println("Client.Client kill connections");
                        Thread.sleep(2000);

                        if(ois.read() > -1) {
                            System.out.println("reading...");
                            String in = ois.readUTF();
                            System.out.println(in);
                        }

                        break;
                    }

                    System.out.println("Client.Client sent message & start waiting for data from server...");
                    Thread.sleep(2000);

                    if (ois.read() > -1) {
                        System.out.println("reading...");
                        String in = ois.readUTF();
                        System.out.println(in);
                    }
                }
            }

            System.out.println("Closing connections & channels on clentSide - DONE.");
        } catch (IOException error) {
            error.printStackTrace();
        }
    }
}
