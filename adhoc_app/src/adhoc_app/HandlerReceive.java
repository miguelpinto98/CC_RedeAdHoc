package adhoc_app;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HandlerReceive extends Thread {
    private MulticastSocket socket;
    private DatagramPacket packet;
    private InetAddress address;
    
    public HandlerReceive() {
    }

    HandlerReceive(MulticastSocket socket, InetAddress address) {
        this.socket=socket;
        this.address=address;
    }
    
    @Override
    public void run() {
        try {
            String msg = "Hello";
            
            this.packet = new DatagramPacket(msg.getBytes(), msg.length(),this.address,9999);
            socket.send(this.packet);
        } catch (IOException ex) {
            Logger.getLogger(HandlerReceive.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
}
