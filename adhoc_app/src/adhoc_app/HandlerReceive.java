package adhoc_app;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HandlerReceive extends Thread {
    public static int MAXBUFFER = 1024;
    
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
            byte[] buffer = new byte[MAXBUFFER];
            this.packet = new DatagramPacket(buffer, buffer.length);
            
            while(true) {
                socket.receive(this.packet);
                Hello hl = (Hello) Utilities.desSerializa(this.packet.getData());
                System.out.println("Recebi o pacote de "+ this.packet.getAddress() +" com a mensagem "+ hl.getMessage());
            }
        } catch (IOException ex) {
            Logger.getLogger(HandlerSend.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HandlerReceive.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
}
