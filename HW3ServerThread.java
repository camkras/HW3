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
import java.util.ArrayList;

public class HW3ServerThread extends Thread {
    private Socket clientTCPSocket = null;

    public HW3ServerThread(Socket socket) {
        super("TCPMultiServerThread");
        clientTCPSocket = socket;
    }

    public String[] responseHeader(String requestType, String host, String version)throws IOException{
        
        Date date = new Date(); 
        String[] response = new String[5];
        response[0] ="  Response Header";
        response[1] = version + " " +requestType; 
        response[2] = "Date: " +date.toString();
        response[3] = "Server: " + host; 
        response[4] = "";
        return response;
    }

    public void run() {
        do
        {
        Date date = new Date();
        try {
            PrintWriter cSocketOut = new PrintWriter(clientTCPSocket.getOutputStream(), true);
            BufferedReader cSocketIn = new BufferedReader(new InputStreamReader(clientTCPSocket.getInputStream()));
            String HTTPRequest;
            ArrayList<String> lines = new ArrayList<String>(); 
            
            // Get all lines
            int count = 0;
            while (count <=3) {
                HTTPRequest = cSocketIn.readLine();
                lines.add(HTTPRequest);
                System.out.println("Line Received: " +HTTPRequest);
                //cSocketOut.println(HTTPRequest);
                count++;
            }

            System.out.println("Full HTTP Request Recieved...");  

            String requestType;
            String path;
            String version;
            String host;
            String userAgent;

            // Line 1: (Request type, path, version)
            String line1[] = lines.get(0).split("\\s+");
            //System.out.println("Print file line:" +lines.get(0));
            requestType = line1[0];
            path = line1[1];
            version = line1[2];
            // Line 2: (Host)
            String line2[] = lines.get(1).split("\\s+");
            //System.out.println("Print hostline:" +lines.get(1));
            host = line2[1];
            // Line 3: (User agent)
            String line3[] = lines.get(2).split("\\s+");
            userAgent = line3[1];
            // Line 4: (Request done)


            //Interpret HTTP Request:

            if(requestType.equals("GET"))
            {
                try{
                    String fullPath =  path;
                    BufferedReader br = new BufferedReader(new FileReader(fullPath));
                    String[] response = responseHeader(requestType, host, version);
                    for (int i = 0; i <=4; i++)
                    {
                        cSocketOut.println(response[i]);
                    }
                    String file;
                    while((file = br.readLine())!= null)
                    {
                        cSocketOut.println(file);
                    }
                    br.close();
                }
                catch(FileNotFoundException e)
                {
                    System.out.println("File Not Found");
                    requestType = requestType + (" 404 File Not Found");
                    String[] response =responseHeader(requestType, host, version);
                    for (int i = 0; i <=4; i++)
                    {
                        cSocketOut.println(response[i]);
                        
                    }
                    cSocketOut.close();
                }



            }
            else // 400 bad request
            {
                requestType = requestType + (" 400 bad request");
                String[] response = responseHeader(requestType, host, version);
                for (int i = 0; i <=4; i++)
                {
                    cSocketOut.println(response[i]);
                    
                }
                cSocketOut.close();
            }

            
            
        //cSocketOut.close();
        //cSocketIn.close();
        //clientTCPSocket.close();
        }    
                
        
        catch (IOException e) {
           e.printStackTrace();
       }
    }  
    while(!clientTCPSocket.isClosed());
    }
    
}
