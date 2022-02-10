/*
   Client app for HW3 in cs3700
   Written by Cameron Krasovich and Noah Pearson Kramer
   Based on TCP Client App written and provided by
   Weiying Zhu
*/


import java.io.*;
import java.net.*;
import java.util.*;

//Things commented out are mostly tcp and connection, currently instead of connecting this code just takes
//a request adress and then collects the input. it then prints the input as a string parased by commas
//change print to send then process response

public class HW3Client {

   public static void main(String[] args) throws IOException {
   
   String fromUser = "";
   String[] HTTPRequest = new String[4];
   String HTTPResponse;
   
   
      //Step 1, capture target address/ip
      System.out.println("Please input host address or IP (ie. cs3700a.msudenver.edu) ");
      Scanner scan = new Scanner(System.in);
      String hostAddr = scan.nextLine();
   
   /*
      Socket tcpSocket = null;
      PrintWriter socketOut = null;
      BufferedReader socketIn = null;
   
                        
      try {
         tcpSocket = new Socket(hostAddr, 5180);   // 5180 I my assigned port for server
         socketOut = new PrintWriter(tcpSocket.getOutputStream(), true);
         socketIn = new BufferedReader(new InputStreamReader(tcpSocket.getInputStream()));
         //Need to add RTT time of connection around creating socket object

      } catch (UnknownHostException e) {
         System.err.println("Don't know about host: " + hostAddr);
         System.exit(1);
      } catch (IOException e) {
         System.err.println("Couldn't get I/O for the connection to: " + hostAddr);
         System.exit(1);
      }        
      */         
   
      while (hostAddr != null) {
      
         System.out.println("Please input the HTTP method type: ");
         HTTPRequest[0] = scan.nextLine();
         //socketOut.println(methodType);
         
         System.out.println("Please input the name of the htm file: ");
         HTTPRequest[1] = scan.nextLine();
         //socketOut.println(htmFile);
         
         
         System.out.println("Please input the HTTP version: ");
         HTTPRequest[2] = scan.nextLine();
         //socketOut.println(HTTPVersion);
         
         
         System.out.println("Please input the User agent: ");
         HTTPRequest[3] = scan.nextLine();
         //socketOut.println(userAgent);
        
        for (int i = 0; i < HTTPRequest.length; i++) {
              System.out.print(HTTPRequest[i] + ", ");
  }
         
         
         /*
                             
         if ((fromServer = socketIn.readLine()) != null)
         {
            System.out.println("Server: " + fromServer);
         }
         else {
            System.out.println("Server replies nothing!");
            break;
         }
      */
      System.out.println("\nWould you like to continue? y/n");
      fromUser = scan.nextLine();
         if (!fromUser.equals("y")) {
            /* socketOut.close();
            socketIn.close();
            sysIn.close();
            tcpSocket.close();   */
            break;
         }
      }
   
   }
}
