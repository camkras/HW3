/*
   Server app for HW3 in cs3700
   Written by Cameron Krasovich and Noah Pearson Kramer
   Based on TCP Server App written and provided by
   Weiying Zhu
*/

import java.io.IOException;
import java.net.*;
public class HW3Server {
    public static void main(String[] args) throws IOException{
        ServerSocket serverTCPSocket = null;
        boolean listening = true;

        try{
            serverTCPSocket = new ServerSocket(5240); 
        }
        catch(IOException e){
            System.err.println("Could not listen on port: 5240");
            System.exit(-1);
        }
        while(listening)
        {
            new HW3ServerThread(serverTCPSocket.accept()).start();
        }

    }
    


}

