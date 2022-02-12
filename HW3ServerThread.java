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

            while ((HTTPRequest = cSocketIn.readLine()) != null) {
                System.out.println("HTTP REQUEST LINE:");
                System.out.println(HTTPRequest);
                String[] line = HTTPRequest.split(" "); //parse by space
                System.out.println(" Print LINE ELEMENTS:");
                for (int i =0;i<line.length;i++)
                {
                    System.out.println(line[i]);
                }

                if (line[0].equals("GET"))
                    {
                        try{
                                String path ="/HW03/"+ line[1];  // 'Resources' could be a separate folder or just store everything in HW03   
                                BufferedReader br = new BufferedReader(new FileReader(path));
                                String hostLine = cSocketIn.readLine();
                                line = hostLine.split(" ");
                                String hostName = line[1];
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
                                String hostLine = cSocketIn.readLine();
                                line = hostLine.split(" ");
                                String hostName = line[1];
                                String responseHeader = "     Response Header \r\n" + line[2] + "404 Not Found \r\n"+"Date: "+date.toString()+"\r\n"+"Server: " + hostName +"\r\n";
                                cSocketOut.println(responseHeader);
                             }  
                    }
                else
                    {
                        String hostLine = cSocketIn.readLine();
                        line = hostLine.split(" ");
                        String hostName = line[1];
                        String responseHeader = "     Response Header \r\n" + line[2] + "400 Bad Request \r\n"+"Date: "+date.toString()+"\r\n"+"Server: " + hostName +"\r\n";
                        cSocketOut.println(responseHeader);
                    }

            }
            cSocketOut.close();
            cSocketIn.close();
            clientTCPSocket.close();
        }    
                
        
        catch (IOException e) {
           e.printStackTrace();
       }
    }
}
    
    