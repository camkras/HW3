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
                String[] line = HTTPRequest.split("\\+s"); //parse by space
                System.out.println("Print LINE ELEMENTS:");
                for (int i =0;i<line.length;i++)
                {
                    System.out.println(line[i]);
                }
                String version = line[2];
                if (line[0].equals("GET"))
                    {
                        
                        try{
                                String path ="/HW03/"+ line[1];  // 'Resources' could be a separate folder or just store everything in HW03   
                                BufferedReader br = new BufferedReader(new FileReader(path));
                                
                                //Construct Response Message
                                // Format: 
                                /*          Response Header:
                                HTTP/1.2 200 OK
                                Date:    .... 
                                Server:  .... 
                                *blank line*
                                */

                                String hostLine = cSocketIn.readLine();
                                line = hostLine.split(" ");
                                String hostName = line[1];
                                String[] rHeaderLines = new String[4];
                                rHeaderLines[0] = (version + "200 Okay"+"\r\n");
                                rHeaderLines[1] = ("Date:" +date.toString()+"\r\n");
                                rHeaderLines[2] = ("Server: " + hostName)+"\r\n";
                                rHeaderLines[3] = ("")+"\r\n";
                                for(int i = 0; i < rHeaderLines.length; i++)
                                {
                                    cSocketOut.println(rHeaderLines[i]);
                                }

                                
                        
                                // Send .htm file
                                while(br.readLine()!= null)
                                {
                                    cSocketOut.write(br.readLine());
                                }
                                br.close();
                             }
                        catch (FileNotFoundException e)
                            {
                                String hostLine = cSocketIn.readLine();
                                line = hostLine.split(" ");
                                String hostName = line[1];
                                String[] rHeaderLines = new String[4];
                                rHeaderLines[0] = (version + "404 Not Found"+"\r\n");
                                rHeaderLines[1] = ("Date:" +date.toString()+"\r\n");
                                rHeaderLines[2] = ("Server: " + hostName)+"\r\n";
                                rHeaderLines[3] = ("")+"\r\n";
                                for(int i = 0; i < rHeaderLines.length; i++)
                                {
                                    cSocketOut.println(rHeaderLines[i]);
                                }
                             }  
                    }
                else
                    {
                        String hostLine = cSocketIn.readLine();
                        line = hostLine.split(" ");
                        String hostName = line[1];
                        String[] rHeaderLines = new String[4];
                        rHeaderLines[0] = (version + "400 Bad Request"+"\r\n");
                        rHeaderLines[1] = ("Date:" +date.toString()+"\r\n");
                        rHeaderLines[2] = ("Server: " + hostName)+"\r\n";
                        rHeaderLines[3] = ("")+"\r\n";
                        for(int i = 0; i < rHeaderLines.length; i++)
                        {
                            cSocketOut.println(rHeaderLines[i]);
                        }
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
    
    