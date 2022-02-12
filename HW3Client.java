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
   
   
      Socket tcpSocket = null;
      PrintWriter socketOut = null;
      BufferedReader socketIn = null;
                    
      try {
         tcpSocket = new Socket(hostAddr, 5240);   // 5180 I my assigned port for server
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
         HTTPRequest[0] = scan.nextLine();
         System.out.println(HTTPRequest[0]);
         //socketOut.println(methodType);
         
         System.out.println("Please input the name of the htm file: ");
         HTTPRequest[1] = scan.nextLine();
         System.out.println(HTTPRequest[1]);
         //socketOut.println(htmFile);
         
         
         System.out.println("Please input the HTTP version: ");
         HTTPRequest[2] = scan.nextLine();
         //socketOut.println(HTTPVersion);
         System.out.println(HTTPRequest[2]);
         
         System.out.println("Please input the User agent: ");
         HTTPRequest[3] = scan.nextLine();
         System.out.println(HTTPRequest[3]);
         //socketOut.println(userAgent);
        
        //for (int i = 0; i < HTTPRequest.length; i++) {
          //    System.out.print(HTTPRequest[i] + ", ");

         //send request header
         String[] requestHeaderLines = new String[4];
         requestHeaderLines[0] = (HTTPRequest[0]+" /"+ HTTPRequest[1]+" HTTP/"+HTTPRequest[2]+"\r\n");
         requestHeaderLines[1] = ("Host: "+hostAddr+"\r\n");
         requestHeaderLines[2] =("User-Agent: "+HTTPRequest[3]+"\r\n");
         requestHeaderLines[3] =("");
          for (int i = 0; i < requestHeaderLines.length; i++)
          {
             socketOut.println(requestHeaderLines[i]);
          }
          
          
         
         // get response header




         //while ((HTTPResponse = socketIn.readLine()) != null) {
         //print header sepratting lines by \r\n
                  // I think the server actually handles the printing here.
         
                  //read end of header as as empty line

         //save htm file
         //end read with 4 empty lines (set null after)
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
            //break;
            cont = false;
         }
      }
      }
   
   
