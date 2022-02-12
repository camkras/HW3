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
        String[] response = new String[4];
        response[0] ="  Response Header \r\n";
        response[1] = version + " " +requestType+ "\r\n"; 
        response[2] = "Date: " +date.toString()+ "\r\n";
        response[3] = "Server: " + host+ "\r\n"; 
        return response;
    }

    public void run() {
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
                count++;
            }

            //hangs here until client close???????
            System.out.println("Full HTTP Request Recieved...");  

            String requestType;
            String path;
            String version;
            String host;
            String userAgent;

            // Line 1: (Request type, path, version)
            String line1[] = lines.get(0).split("\\s+");
            System.out.println("Print file line:" +lines.get(0));
            requestType = line1[0];
            path = line1[1];
            version = line1[2];
            // Line 2: (Host)
            String line2[] = lines.get(1).split("\\s+");
            System.out.println("Print hostline:" +lines.get(1));
            host = line2[1];
            // Line 3: (User agent)
            String line3[] = lines.get(2).split("\\s+");
            userAgent = line3[1];
            // Line 4: (Request done)


            //Interpret HTTP Request:

            if(requestType.equals("GET"))
            {
                try{
                    String fullPath ="HW03"+ path;
                    BufferedReader br = new BufferedReader(new FileReader(fullPath));
                    responseHeader(requestType, host, version);
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
                    responseHeader(requestType, host, version);
                }



            }
            else // 400 bad request
            {
            responseHeader(requestType, host, version);
            }






















                /*
                System.out.println("HTTP REQUEST LINE:");
                System.out.println(HTTPRequest);
                String[] line = HTTPRequest.split("\\s+"); //parse by space
                System.out.println("Print LINE ELEMENTS:");
                for (int i =0;i<line.length;i++)
                {
                    System.out.println(line[i]);
                }
                String version = "1.1";//line[2];
                if (line[0].equals("GET"))
                    {
                        System.out.println("If statement functions ");
                        try{
                                System.out.println("Try block is called ");
                                String path ="HW03"+ line[1];  // 'Resources' could be a separate folder or just store everything in HW03   
                                System.out.println("Path:"+path);
                                BufferedReader br = new BufferedReader(new FileReader(path));
                                
                                //Construct Response Message
                                // Format: 
                                /*          Response Header:
                                HTTP/1.2 200 OK
                                Date:    .... 
                                Server:  .... 
                                *blank line*
                                

                                String hostLine = cSocketIn.readLine();
                                line = hostLine.split("\\s+");
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
                                System.out.println("Catch block is called ");
                                String hostLine = cSocketIn.readLine();
                                System.out.println("HostLine: " + hostLine);
                                line = hostLine.split(" ");
                                //String hostName = line[1];
                                String[] rHeaderLines = new String[4];
                                rHeaderLines[0] = (version + "404 Not Found"+"\r\n");
                                rHeaderLines[1] = ("Date:" +date.toString()+"\r\n");
                                //rHeaderLines[2] = ("Server: " + hostName)+"\r\n";
                                rHeaderLines[3] = ("")+"\r\n";
                                for(int i = 0; i < rHeaderLines.length; i++)
                                {
                                    cSocketOut.println(rHeaderLines[i]);
                                }
                             }  
                    }
                else
                    {
                        System.out.println("Else is called (400 Bad Request) ");
                        String hostLine = cSocketIn.readLine();
                        line = hostLine.split("\\s+");
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
*/
            
            
        //cSocketOut.close();
        //cSocketIn.close();
        //clientTCPSocket.close();
        }    
                
        
        catch (IOException e) {
           e.printStackTrace();
       }

       
    }
}
    
    