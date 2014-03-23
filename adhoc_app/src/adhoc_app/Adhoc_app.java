package adhoc_app;

import java.io.*;
import java.net.*;
 
public class Adhoc_app {

    public static void main(String[] args) throws IOException {
        MulticastSocket socket = new MulticastSocket(9999);
        InetAddress address = InetAddress.getByName("230.0.0.2");
        DatagramPacket packet = null;

        while(true) {
            socket.joinGroup(address);
            Handler thread = new Handler(socket,packet,address);
            thread.start();
        
            if(false) {
                socket.leaveGroup(address);
                socket.close();
            }
        }
    }
}