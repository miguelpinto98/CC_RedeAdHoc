package adhoc_app;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HandlerSend extends TimerTask {
    private MulticastSocket socket;
    private DatagramPacket packet;
    private InetAddress address;
    //private String ipEmissor;
    private String hostName;
    
    HandlerSend(MulticastSocket socket, InetAddress address, String ipEmissor, String NameEmissor) {
        this.socket = socket;
        this.address = address;
        this.hostName = NameEmissor;
    }
    
    @Override
    public void run() {
        HashMap<String,Host> vizs = Adhoc_app.CloneMapVizinhos();
        try {
            HashSet<String> aux = new HashSet<String>();
            for(Host h : vizs.values())
                if(h.getStatus()==1)
                    aux.add(h.getName());
            
            Hello ola = new Hello("Pacote Hello", Utilities.encontrarMeuIP(), this.hostName,aux);
            byte [] id = Utilities.serializa(ola);
            this.packet = new DatagramPacket(id,id.length,address,9999);
            System.out.println("A enviar pacote HELLO");
            socket.send(this.packet);    
        } catch (IOException ex) {
            Logger.getLogger(HandlerReceive.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(!vizs.isEmpty()) {
            for(Host h : vizs.values()) {
                if(h.getStatus()==1) {
                    System.out.println("VP" +h.getName());
                    for(String s : h.getVizinhos()) {
                        System.out.println("    VS" +s); 
                    }
                }
            }
        }
    }
}
