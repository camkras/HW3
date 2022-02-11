/*
   Server app for HW3 in cs3700
   Written by Cameron Krasovich and Noah Pearson Kramer
   Based on TCP Server App written and provided by
   Weiying Zhu
*/

import java.net.*;
import java.io.*;
import java.util.Date;

public class HW3ServerThread extends Thread {
    private Socket clientTCPSocket = null;

    public HW3ServerThread(Socket socket) {
        super("HW3ServerThread");
        clientTCPSocket = socket;
    }

    public void run() {
        Date date = new Date();

        try {
            PrintWriter cSocketOut = new PrintWriter(
                clientTCPSocket.getOutputStream(), true);

            BufferedReader cSocketIn = new BufferedReader(new InputStreamReader(clientTCPSocket.getInputStream()));
            String HTTPRequest;
            //read request from client             
            while ((HTTPRequest = cSocketIn.readLine()) != null) {
                //The client request can be combined into a single string, and then parsed.
                String[] requestLines = HTTPRequest.split("\r\n"); // maybe parse by comma... can we parse by \r\n ?
                String[] line = requestLines[0].split(" "); //parse by space
                if (line[0].equals("GET"))
                    {
                        try{
                            String path ="/HW03/Resources/"+ line[1];  // 'Resources' could be a separate folder or just store everything in HW03   
                            BufferedReader br = new BufferedReader(new FileReader(path));
                            //Construct Response Message
                            // Format: 
                            /*          Response Header:
                                HTTP/1.2 200 OK
                                Date:    .... java date or calendar class
                                Server:  .... how do we get the server name? should be able to change based on server?
                                *blank line*
                            */
                            InetAddress ip;
                            String hostName = null;

                                try {
                                    ip = InetAddress.getLocalHost();
                                    hostName = ip.getHostName();
                                } catch (UnknownHostException e) {
                                    e.printStackTrace();
                                }
                            String responseHeader = "     Response Header \r\n" + line[2] + "200 Okay \r\n"+"Date: "+date.toString()+"\r\n"+"Server: " + hostName +"\r\n"; // still needs to get server name
                            cSocketOut.println(responseHeader);
                            // Send .htm file
                            while(br.readLine()!= null)
                            {
                                cSocketOut.write(br.readLine());
                            }
                            }
                        catch (IOException e)
                            {
                                System.err.println(e);
                                System.out.println("404 Not Found "); // output to client.
                            }  
                    }
                else
                    {
                        System.out.println("400 Bad Request"); 
                        //needs to respond to client with this
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
