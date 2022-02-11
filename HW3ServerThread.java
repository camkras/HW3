/*
   Server app for HW3 in cs3700
   Written by Cameron Krasovich and Noah Pearson KrameR
   A thread is started to handle every client TCP connection to this server
   Based on TCP Server App written and provided by
   Weiying Zhu
*/

import java.net.*;
import java.io.*;
import java.util.Date;

public class HW3ServerThread extends Thread {
    private Socket clientTCPSocket = null;

    public HW3ServerThread(Socket socket) {
        super("TCPMultiServerThread");
        clientTCPSocket = socket;
    }

    public void run() {
        Date date = new Date();

        try {
            PrintWriter cSocketOut = new PrintWriter(clientTCPSocket.getOutputStream(), true);
            BufferedReader cSocketIn = new BufferedReader(new InputStreamReader(clientTCPSocket.getInputStream()));
            String HTTPRequest;
            //read request from client             
            while ((HTTPRequest = cSocketIn.readLine()) != null) {
                //The client request can be combined into a single string, and then parsed.
                String[] requestLines = HTTPRequest.split("\r\n"); // parse by \r\n
                String[] line = requestLines[0].split(" "); //parse by space
                
                //for (int i = 0; i < line.length; i++) {
                //System.out.print(line[i] + ", ");
                //Uncomment above to see if request if received and parsed
          
                if (line[0].equals("GET"))
                    {
                        try{
                                String path ="/HW03/Resources/"+ line[1];  // 'Resources' could be a separate folder or just store everything in HW03   
                                BufferedReader br = new BufferedReader(new FileReader(path));
                                InetAddress ip;
                            
                                // Get hostName for response header
                                String hostName = null;

                                try {
                                    ip = InetAddress.getLocalHost();
                                    hostName = ip.getHostName();
                                } catch (UnknownHostException e) {
                                    e.printStackTrace();
                                }
                                
                                
                                //Construct Response Message
                                // Format: 
                                /*          Response Header:
                                HTTP/1.2 200 OK
                                Date:    .... 
                                Server:  .... 
                                *blank line*
                                */

                                String responseHeader = "     Response Header \r\n" + line[2] + "200 Okay \r\n"+"Date: "+
                                date.toString()+"\r\n"+"Server: " + hostName +"\r\n"; 
                                cSocketOut.println(responseHeader);
                                
                        
                                // Send .htm file
                                while(br.readLine()!= null)
                                {
                                    cSocketOut.write(br.readLine());
                                }
                             }
                        catch (IOException e)
                            {
                                InetAddress ip;
                                String hostName = null;
                                try {
                                    ip = InetAddress.getLocalHost();
                                    hostName = ip.getHostName();
                                } catch (UnknownHostException f) {
                                    f.printStackTrace();
                                }
                                String responseHeader = "     Response Header \r\n" + line[2] + "404 Not Found \r\n"+"Date: "+date.toString()+"\r\n"+"Server: " + hostName +"\r\n";
                                cSocketOut.println(responseHeader);
                             }  
                    }
                else
                    {
                        InetAddress ip;
                        String hostName = null;
                        try {
                            ip = InetAddress.getLocalHost();
                            hostName = ip.getHostName();
                        } catch (UnknownHostException f) {
                            f.printStackTrace();
                        }
                        String responseHeader = "     Response Header \r\n" + line[2] + "400 Bad Request \r\n"+"Date: "+date.toString()+"\r\n"+"Server: " + hostName +"\r\n";
                        cSocketOut.println(responseHeader);
                    }

                
                
                


       /* Cameron's Method
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

                //send to client                
                //need break/exit
            
                        
           cSocketOut.close();
           cSocketIn.close();
           clientTCPSocket.close();
        }
        }
        catch (IOException e) {
           e.printStackTrace();
       }
    } 
    }
