package adhoc_app;

import java.io.*;
import java.net.*;
 
public class Adhoc_app {

    public static void main(String[] args) throws IOException {
        MulticastSocket socket = new MulticastSocket(9999);
        InetAddress address = InetAddress.getByName("230.0.0.1");
        socket.joinGroup(address);

            HandlerReceive tr = new HandlerReceive(socket,address);
            HandlerSend ts = new HandlerSend(socket,address);
            tr.start();
            ts.start();
        
            if(false) {
                socket.leaveGroup(address);
                socket.close();
        }
    }
}