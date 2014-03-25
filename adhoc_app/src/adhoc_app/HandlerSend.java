package adhoc_app;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HandlerSend extends Thread {
    private MulticastSocket socket;
    private DatagramPacket packet;
    private InetAddress address;
    
    
    HandlerSend(MulticastSocket socket, InetAddress address) {
        this.socket = socket;
        this.address = address;
    }
    
    @Override
    public void run() {
        try {
            byte[] buffer = new byte[1024];
            this.packet = new DatagramPacket(buffer, buffer.length);
            
            socket.receive(this.packet);
            System.out.println(buffer.toString());
        } catch (IOException ex) {
            Logger.getLogger(HandlerSend.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
