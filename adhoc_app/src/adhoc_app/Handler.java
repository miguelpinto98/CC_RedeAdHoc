package adhoc_app;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Handler extends Thread {
    private MulticastSocket socket;
    private DatagramPacket packet;
    private InetAddress address;
    
    public Handler() {
    }

    Handler(MulticastSocket socket, DatagramPacket packet, InetAddress address) {
        this.socket=socket;
        this.packet=packet;
        this.address=address;
    }
    
    @Override
    public void run() {
       /* try {
            String msg = "Hello";
            
            packet = new DatagramPacket(msg.getBytes(), msg.length(),this.address,9999);
            try {
                socket.send(packet);
            } catch (IOException ex) {
                System.out.println("ERROR");
            }
            
            byte[] buffer = new byte[1024];
            DatagramPacket recv = new DatagramPacket(buffer, buffer.length);
            socket.receive(recv);
            System.out.println(buffer.toString());
        } catch (IOException ex) {
            Logger.getLogger(Handler.class.getName()).log(Level.SEVERE, null, ex);
        }*/
     }
}
