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
            byte[] buffer = new byte[50];
            this.packet = new DatagramPacket(buffer, buffer.length);
            
            socket.receive(this.packet);
            System.out.println("ABC "+new String(buffer));
        } catch (IOException ex) {
            Logger.getLogger(HandlerSend.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
}
