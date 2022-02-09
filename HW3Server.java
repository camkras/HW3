//copied these from the example programs, now need to be changed to fit the assignment

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
