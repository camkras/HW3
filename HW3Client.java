/*
   Client app for HW3 in cs3700
   Written by Cameron Krasovich and Noah Pearson Kramer
   Based on TCP Client App written and provided by
   Weiying Zhu
*/


import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.text.html.CSS;

public class HW3Client {

   public static void main(String[] args) throws IOException {
   
      String fromUser = "";
      String[] HTTPRequest = new String[4];
      String HTTPResponse;
      int counter = 0;
      int consecCount = 0;
      int savedLines = 0;
   
      //Capture target address/ip
      System.out.println("Please input host address or IP (ie. cs3700a.msudenver.edu) ");
      Scanner scan = new Scanner(System.in);
      String hostAddr = scan.nextLine();
      Socket tcpSocket = null;
      PrintWriter socketOut = null;
      BufferedReader socketIn = null;
                    
      try {
         tcpSocket = new Socket(hostAddr, 5180);   // 5180 I my assigned port for server
         long start_time = System.currentTimeMillis();
         socketOut = new PrintWriter(tcpSocket.getOutputStream(), true);
         socketIn = new BufferedReader(new InputStreamReader(tcpSocket.getInputStream()));
         long end_time = System.currentTimeMillis();
         long difference = end_time-start_time;
         System.out.println("RTT of Socket creation = " + difference + "ms"); //RTT time of connection around creating socket object
      
      } catch (UnknownHostException e) {
         System.err.println("Don't know about host: " + hostAddr);
         System.exit(1);
      } catch (IOException e) {
         System.err.println("Couldn't get I/O for the connection to: " + hostAddr);
         System.exit(1);
      }        
             
      boolean cont = true;
      while (cont) {
      
         System.out.println("Please input the HTTP method type: ");
         HTTPRequest[0] = scan.nextLine().trim();
         //System.out.println(HTTPRequest[0]);
         //socketOut.println(methodType);
         System.out.println("Please input the name of the htm file: ");
         HTTPRequest[1] = scan.nextLine().trim();
         //System.out.println(HTTPRequest[1]);
         //socketOut.println(htmFile);
         System.out.println("Please input the HTTP version: ");
         HTTPRequest[2] = scan.nextLine().trim();
         //socketOut.println(HTTPVersion);
         //System.out.println(HTTPRequest[2]);
         
         System.out.println("Please input the User agent: ");
         HTTPRequest[3] = scan.nextLine().trim();
         //System.out.println(HTTPRequest[3]);
         //socketOut.println(userAgent);
        //for (int i = 0; i < HTTPRequest.length; i++) {
          //    System.out.print(HTTPRequest[i] + ", ");
      
         //send request header
         String[] requestHeaderLines = new String[4];
         requestHeaderLines[0] = (HTTPRequest[0]+" "+ HTTPRequest[1]+" HTTP/"+HTTPRequest[2]);
         requestHeaderLines[1] = ("Host: /"+hostAddr);
         requestHeaderLines[2] =("User-Agent: "+HTTPRequest[3]);
         requestHeaderLines[3] =("");
         for (int i = 0; i < requestHeaderLines.length; i++)
         {
            socketOut.println(requestHeaderLines[i]);
            //System.out.println("Sent Line " +i+":" + requestHeaderLines[i]);
         }
                   
         // get response header
         int count1=0;
         String fromServer;
         while(count1 <5)
         {
            fromServer=socketIn.readLine();
            System.out.println(fromServer); 
            count1++;
           // System.out.println("count: " + count1);
         }
         //System.out.println("response header loop done");    
         
         while ((fromServer = socketIn.readLine()) != null) {
            FileWriter fileWriter = new FileWriter(HTTPRequest[1]);              
            PrintWriter printWriter = new PrintWriter(fileWriter, true);             
                       
            if (fromServer.equals("")){
               consecCount++;
               printWriter.println(fromServer);
               //System.out.println(fromServer);
               savedLines++;
            }
            if (!fromServer.equals("")){ 
               consecCount=0;
               printWriter.println(fromServer);
               //System.out.println(fromServer);
               savedLines++;
            }                           
            //System.out.println("consecutive blank lines: " + consecCount);
            
            if (consecCount == 4){            
               //System.out.println("File finished (4 empty lines) ");
               System.out.println("Saved Lines: " + savedLines + " to /" + HTTPRequest[1]);
               printWriter.close();
            }                
         }                        
      
         System.out.println("\nWould you like to continue? y/n");
         fromUser = scan.nextLine();
         if (!fromUser.equals("y")) {
            socketOut.close();
            socketIn.close();
            tcpSocket.close();   
            cont = false;                 
         }       
      }
   }
}
