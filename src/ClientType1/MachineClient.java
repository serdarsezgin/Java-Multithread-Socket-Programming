package ClientType1;

import java.io.*;
import java.net.*;
import java.util.*;

public class MachineClient {

    private static InetAddress host;
    private static final int PORT = 1234;

    public static void main(String[] args) throws IOException {
        try {
            host = InetAddress.getLocalHost();
        } catch (UnknownHostException uhEx) {
            System.out.println("\nHost ID not found!\n");
            System.exit(1);
        }
        
        Menu();

        //sendMessages();
    }

    static void Menu() throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        System.out.println("MACHINES INTERFACE\n1.Makine Ekle");
        System.out.println("seçim:");
        String input = reader.readLine();
        switch (input) {
            case "1" -> {
                String protocolMessage ="ClientType1";
                protocolMessage += "&";
                System.out.println("makineadı giriniz");

                protocolMessage += reader.readLine();
                protocolMessage += "&";
                System.out.println("makine id giriniz");
                protocolMessage += reader.readLine();
                protocolMessage += "&";
                System.out.println("makine tipi giriniz");
                protocolMessage += reader.readLine();
                protocolMessage += "&";
                System.out.println("makine hızı giriniz");
                protocolMessage += reader.readLine();
                
                sendMessages(protocolMessage);

            }
            
        }
    }

    private static void sendMessages(String msg) {
        Socket socket = null;

        try {
            socket = new Socket(host, PORT);

            Scanner networkInput
                    = new Scanner(socket.getInputStream());
            PrintWriter networkOutput
                    = new PrintWriter(
                            socket.getOutputStream(), true);

            //Set up stream for keyboard entry...
            Scanner userEntry = new Scanner(System.in);

            String message, response;
            do {
                System.out.print(
                        "(Hit Enter or type 'QUIT' to exit): ");
                message = userEntry.nextLine();
                networkOutput.println(msg);
                response = networkInput.nextLine();
                System.out.println("\nSERVER> " + response);
            } while (!message.equals("QUIT"));
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        } finally {
            try {
                System.out.println("\nClosing connection...");
                socket.close();
            } catch (IOException ioEx) {
                System.out.println("Unable to disconnect!");
                System.exit(1);
            }
        }
    }
}
