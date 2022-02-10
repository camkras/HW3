/*
   Server app for HW3 in cs3700
   Written by Cameron Krasovich and Noah Pearson Kramer
   Based on TCP Server App written and provided by
   Weiying Zhu
*/

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

            
            String HTTPRequest;
            
            //read request from client             
            while ((HTTPRequest = cSocketIn.readLine()) != null) {
                

                //The client request can be combined into a single string, and then parsed.
                
                String[] requestLines = HTTPRequest.split("\r\n"); // maybe parse by comma... can we parse by \r\n ?
                
                String[] line = requestLines[0].split(" "); //parse by space
                
                if (line[0].equals("GET"))
                    {

                    }
                else
                    {
                        System.out.println("400 Bad Request");
                        //needs to respond to client with this
                    }

                
                try{
                    String path = line[1];     
                    BufferedReader br = new BufferedReader(new FileReader(path));
                    
                    }
                catch (IOException e)
                    {
                        System.err.println(e);
                        System.out.println("404 Not Found "); // output to client.
                    }


                
           }
                        
           cSocketOut.close();
           cSocketIn.close();
           clientTCPSocket.close();

       } catch (IOException e) {
           e.printStackTrace();
       }
    }
}
