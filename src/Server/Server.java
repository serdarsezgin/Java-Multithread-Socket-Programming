package Server;

import ClientType2.Planners;
import ClientType1.Machines;
import Job.Jobs;
import com.sun.source.tree.CaseTree;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    private static ServerSocket serverSocket;
    private static final int PORT = 1234;

    public static void main(String[] args) throws IOException {
        try {

            serverSocket = new ServerSocket(PORT);
        } catch (IOException ioEx) {
            System.out.println("\nUnable to set up port!");
            System.exit(1);
        }
        System.out.println("SERVER STARTED...");
        do {
            //Wait for client...
            Socket client = serverSocket.accept();

            System.out.println("\nNew client accepted.\n");

            //Create a thread to handle communication with
            //this client and pass the constructor for this
            //thread a reference to the relevant socket...
            ClientHandler handler = new ClientHandler(client);

            handler.start();//As usual, this method calls run.
        } while (true);
    }

}

class ClientHandler extends Thread {

    private Socket client;
    private Scanner input;
    private PrintWriter output;

    public ClientHandler(Socket socket) {
        //Set up reference to associated socket...
        client = socket;

        try {
            input = new Scanner(client.getInputStream());
            output = new PrintWriter(
                    client.getOutputStream(), true);
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }

    public void run() {
        String received;
        boolean access = false;
        String[] parsed_message = new String[256];
        String machineListReturn;

        do {
            //Accept message from client on
            //the socket's input stream...
            received = input.nextLine();
            parsed_message = received.split("&");
            switch (parsed_message[0]) {
                case "ListMachines":
                    machineListReturn = ListMachines();

                    output.println(machineListReturn);
                    break;
                case "ClientType1":
                    addMachine(received);
                    output.println("machine added successfully");
                    break;

                case "MachineInfo":
                    output.println(MachineDetail(parsed_message[1]));
                    break;
                case "Login":

                    access = LoginControl(received);
                    if (access) {
                        output.println("login successful!");
                        System.out.println("planner logged in");
                    } else {
                        output.println("can't login!");
                    }
                    break;
                case "AddJob":
                    addJob(received);
                    output.println("job added");
                case "ListJobs":
                    String jobListReturn = listJobs();

                    output.println(jobListReturn);
                    break;

            }

            //System.out.println(parsed_message[0] + "ff" + parsed_message[1]);
            //Echo message back to client on
            //the socket's output stream...
            output.println(received);

            //Repeat above until 'QUIT' sent by client...
        } while (!received.equals("QUIT"));

        try {
            if (client != null) {
                System.out.println(
                        "Closing down connection...");
                client.close();
            }
        } catch (IOException ioEx) {
            System.out.println("Unable to disconnect!");
        }
    }

    public boolean LoginControl(String namepassword) {
        Planners planners = new Planners();
        Map<String, String> users = planners.allUserInformations;
        String[] parsed_message = new String[99];
        boolean isLoggedIn = false;
        for (Map.Entry<String, String> entry : users.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            parsed_message = namepassword.split("&");
            if (key.equals(parsed_message[1]) && value.equals(parsed_message[2])) {
                isLoggedIn = true;
                break;
            }
        }
        return isLoggedIn;
    }

    public void addMachine(String value) {
        Machines machines = new Machines();

        String[] parsed_message = new String[256];
        parsed_message = value.split("&");

        machines.name = parsed_message[1];
        machines.id = Integer.parseInt(parsed_message[2]);
        machines.type = parsed_message[3];
        machines.speed = Integer.parseInt(parsed_message[4]);
        machines.MachinesList.add(machines);
        System.out.println(parsed_message[1] + " machine added successfully");

    }

    String ListMachines() {
        Machines machines = new Machines();
        String returnVal = "";
        for (Machines m : machines.MachinesList) {

            returnVal += m;
            returnVal += "  -----  ";
        }

        return returnVal;
    }

    String MachineDetail(String id) {

        Machines oMachine = new Machines();
        Jobs oJob = new Jobs();
        String val = "";
        String info = "";

        for (Machines test : oMachine.MachinesList) {
            if (test.getId() == Integer.parseInt(id)) {

                info += test.getId() + " id sine sahip makinenin durumu: " + test.status;

            }
            Set<Integer> keys = oMachine.jobMap.keySet();

            // iterate through the key set and display key and values
            for (Integer key : keys) {

                val = "  Yaptığı işler----iş id:" + Integer.toString(oMachine.jobMap.get(key).id);
                val += " tip: " + oMachine.jobMap.get(key).type;
                val += " uzunluk:" + Integer.toString(oMachine.jobMap.get(key).uzunluk);

            }

        }
        info = info + val;
        return info;

    }

    public void addJob(String value) {
        Jobs jobs = new Jobs();

        String[] parsed_message = new String[256];
        parsed_message = value.split("&");

        jobs.id = Integer.parseInt(parsed_message[1]);
        jobs.type = parsed_message[2];
        jobs.uzunluk = Integer.parseInt(parsed_message[3]);

        jobs.JobsList.add(jobs);
        System.out.println("-> " + parsed_message[1] + " <- id job added successfully");
        jobToMachine(jobs.id);

    }

    public void jobToMachine(int jobId) {

        Machines oMachine = new Machines();
        Jobs oJob = new Jobs();
        for (Machines machine : oMachine.MachinesList) {
            for (Jobs job : oJob.JobsList) {
                if (job.id == jobId && machine.getType().equals(job.type)) {
                    if (machine.status.equals("EMPTY")) {
                        machine.setStatus("BUSY");
                        //BURAYA MAKİNENİN BU İŞİ ALDIĞINI EKLE - JOBMAP
                        oMachine.jobMap.put(machine.id, job);
                        System.out.println("MAKİNE İŞİ ALDI");
                        
                       // oJob.JobsList.remove(job.id); //BEKLEYEN ISLER LISTESINDEN ALINAN ISI CIKART

                    } else {
                        System.out.println("makine meşgul");
                    }

                }
            }
        }
    }

    public String listJobs() {
        Jobs jobs = new Jobs();
        String returnVal = "";
        for (Jobs j : jobs.JobsList) {

            returnVal += j;
            returnVal += "  -----  ";
        }

        return returnVal;

    }

    //iş listesi yazdır..
}

