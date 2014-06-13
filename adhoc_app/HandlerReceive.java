package adhoc_app;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HandlerReceive extends Thread {
    public static int MAXBUFFER = 1024;
    
    private MulticastSocket socket;
    private DatagramPacket packet;
    private InetAddress address;
    
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
                System.out.println(hl.getHostNameEmissor()+">Recebi o pacote de "+ this.packet.getAddress() +" com a mensagem "+hl.getMessage()+"<"+hl.getIpEmissor()); 
                
                neighborsTest(hl.getHostNameEmissor(),hl.getIpEmissor());
                testStatusHost();
            }
        } catch (IOException ex) {
            Logger.getLogger(HandlerSend.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HandlerReceive.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   

    private void testStatusHost() {
        for(Host h : Adhoc_app.vizinhos.values()) {
            if(h.getStatus()==1 && h.difInterval() > Hello.deadInterval) {
                System.out.println("Ligacao Perdida com "+h.getName());
                h.setStatus(0);
            }
        }
    }

    private void neighborsTest(String NameEmissor, String IpEmissor) {
        if(Adhoc_app.vizinhos.containsKey(NameEmissor)) {                    
            Host h = Adhoc_app.vizinhos.get(NameEmissor);
            Adhoc_app.vizinhos.remove(h.getName());

            if(h.getStatus()==0) {
                h.setStatus(1);
                h.setLastResponse(new GregorianCalendar());
            } else {
                h.setLastResponse(new GregorianCalendar());
            }
            Adhoc_app.vizinhos.put(h.getName(), h);
        } else {
            System.out.println("A guardar host "+NameEmissor+" como vizinho...");

            Adhoc_app.vizinhos.put(NameEmissor, new Host(IpEmissor, NameEmissor, this.packet.getPort()));
        }
    }
}
