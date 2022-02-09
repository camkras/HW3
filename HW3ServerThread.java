/*
   Server TCP app for HW3 in cs3700
   A thread is started to handle every client TCP connection to this server
   Written by Cameron Krasovich and
   Based on TCP Server App written and provided by
   Weiying Zhu
*/

import java.net.*;
import java.io.*;

public class HW3ServerThread extends Thread {
    private Socket clientTCPSocket = null;

    public HW3ServerThread(Socket socket) {
        super("TCPMultiServerThread");
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
   String[] clientArray = fromClient.split(",");
               
               //this sets status code for request
                if(!clientArray[0].equals("GET")){
                requestCode = ("400 Bad Request");
                }
                if(!clientArray[1].exists()){
                requestCode = ("404 Not Found");
                }
               else
                  requestCode = ("200 OK");
               /*
               Here is where we will generate response
               need 3line header and extra line then send requested file and add 4 blank lines at the end
               or 3 line header with error code and extra line
               
                for (int i = 0; i < clientArray.length; i++) {
              System.out.print(clientArray[i] + ", ");
              
              */
  }
                //send to client                
                //need break/exit
                          }

                        
           cSocketOut.close();
           cSocketIn.close();
           clientTCPSocket.close();

       } catch (IOException e) {
           e.printStackTrace();
       }
    }
}
