//copied these from the example programs, now need to be changed to fit the assignment
import java.net.*;
import java.io.*;

public class HW3ServerThread extends Thread {
    private Socket clientTCPSocket = null;

    public HW3ServerThread(Socket socket) {
        super("HW3ServerThread");
        clientTCPSocket = socket;
    }

    public void run() {

        try {
            PrintWriter cSocketOut = new PrintWriter(
                clientTCPSocket.getOutputStream(), true);

            BufferedReader cSocketIn = new BufferedReader(
                new InputStreamReader(clientTCPSocket.getInputStream()));

            String fromClient, toClient;
                          
            while ((fromClient = cSocketIn.readLine()) != null) {
   
                toClient = fromClient.toUpperCase();
                cSocketOut.println(toClient);
                                
                if (fromClient.equals("Bye"))
                    break;
           }
                        
           cSocketOut.close();
           cSocketIn.close();
           clientTCPSocket.close();

       } catch (IOException e) {
           e.printStackTrace();
       }
    }
}
