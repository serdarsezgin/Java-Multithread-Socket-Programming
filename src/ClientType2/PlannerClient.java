package ClientType2;

import ClientType1.*;

import Server.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class PlannerClient extends Planners {

    private static InetAddress host;
    private static final int PORT = 1234;
    Planners test = new Planners();

    public static void main(String[] args) throws IOException {

        try {
            host = InetAddress.getLocalHost();
        } catch (UnknownHostException uhEx) {
            System.out.println("\nHost ID not found!\n");
            System.exit(1);
        }

        // Enter data using BufferReader
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        // Printing the read line

        System.out.println("username giriniz");
        // Reading data using readLine
        String sendMsg = "Login";
        sendMsg += "&";
        String name = reader.readLine();
        sendMsg += name;
        System.out.println("password giriniz");
        String password = reader.readLine();
        sendMsg += "&";
        sendMsg += password;
        sendMessages(sendMsg);
       

    }

    static void Menu() throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        System.out.println("PLANNER INTERFACE\n1.Makineleri Listele\n2.Makine Bilgisi Görüntüle\n3.İş Emri Ekle\n4.Bekleyen İş Emirlerini Listele");
        System.out.println("seçim:");
        String input = reader.readLine();
        switch (input) {
            case "1" -> {
                String protocolMessage = "ListMachines";
                protocolMessage += "&";
                sendMessages(protocolMessage);

            }
            case "2" -> {
                String protocolMessage = "MachineInfo";
                protocolMessage += "&";
                System.out.println("Görüntülemek istediğiniz makine id'si: ");
                String id = reader.readLine();
                protocolMessage += id;
                sendMessages(protocolMessage);
            }
            case "3" -> {

                String protocolMessage = "AddJob";
                protocolMessage += "&";
                System.out.println("iş id giriniz");

                protocolMessage += reader.readLine();
                protocolMessage += "&";
                System.out.println("işin türü giriniz");
                protocolMessage += reader.readLine();
                protocolMessage += "&";
                System.out.println("işin uzunluğu giriniz");
                protocolMessage += reader.readLine();

                sendMessages(protocolMessage);
            }

            case "4" -> {

                String protocolMessage = "ListJobs";
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
                //if(response.equals("login successful!"))
                Menu();

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
