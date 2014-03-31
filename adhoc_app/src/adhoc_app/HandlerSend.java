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
            Hello ola = new Hello("Host");
            byte [] id = Adhoc_app.serializa(ola);
            this.packet = new DatagramPacket(id, id.length,this.address,9999);
            System.out.println("A enviar pacote");
            socket.send(this.packet);
        } catch (IOException ex) {
            Logger.getLogger(HandlerReceive.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
