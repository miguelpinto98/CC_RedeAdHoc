package adhoc_app;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.HashSet;
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
                Object obj = Utilities.desSerializa(this.packet.getData());
                
                if(obj instanceof Protocol) {
                    if(obj.getClass().equals(Hello.class)) {
                        Hello hl = (Hello) obj;
                
                        if(!hl.getHostNameEmissor().equals(Adhoc_app.HOSTNAME))
                            System.out.println(hl.getHostNameEmissor()+">Recebi o pacote HELLO de "+ this.packet.getAddress() +" <> "+hl.getIpEmissor()); 
                
                        neighborsTest(hl.getHostNameEmissor(),hl.getIpEmissor(),hl.getVizinhos());
                        testStatusHost();
                    }
                    
                    if(obj.getClass().equals(RouteRequest.class)) {
                        RouteRequest rq = (RouteRequest) obj;
                        
                        if(rq.getNodoDestino().equals(Adhoc_app.HOSTNAME)) {
                            //RouteReply rp = new RouteReply(Utilities.encontrarMeuIP().getHostName(), Adhoc_app.HOSTNAME, new ArrayList<String>());
                         
                        }
                    }
                    
                    if(obj.getClass().equals(TW.class)) {
                        TW tw = (TW) obj;
                        String dest = tw.getDestino().replace("#", "");
                        
                        Message msg = new Message(tw.getIpEmissor(),tw.getHostNameEmissor(),dest,tw.getText());
                        byte[] bs = Utilities.serializa(msg);
                        DatagramPacket dpck = new DatagramPacket(bs,bs.length,tw.getIpEmissor(),9999);
                        socket.send(dpck);
                    }
                    
                    if(obj.getClass().equals(Message.class)) {
                        Message msg = (Message) obj;
                        System.out.println("LIXOOOOOO");
                        
                        if(Adhoc_app.HOSTNAME.equals(msg.getTo())) {
                            System.out.println("### MSG FIM");
                        }
                        
                        if(Adhoc_app.CloneMapVizinhos().containsKey(msg.getTo())) {
                            Host h = Adhoc_app.CloneMapVizinhos().get(msg.getTo());
                            
                            System.out.println("### A Mandar MSG AO VIZINHo "+h.getIp().toString());
                            
                            byte[] bs = Utilities.serializa(msg);
                            DatagramPacket dpck = new DatagramPacket(bs,bs.length,h.getIp(),9999);
                            //DatagramSocket clientSocket = new DatagramSocket();
                            socket.send(dpck);
                            //clientSocket.close();
                        }
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(HandlerSend.class.getName()).log(Level.SEVERE, "asdas", ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HandlerReceive.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   

    private void testStatusHost() {
        Adhoc_app.StatusSync();
    }

    private void neighborsTest(String NameEmissor, InetAddress IpEmissor, HashSet<String> vizinhos) {
        vizinhos.remove(Adhoc_app.HOSTNAME);
        Adhoc_app.neighborsSync(NameEmissor, IpEmissor, this.packet.getPort(), vizinhos);
    }

    public MulticastSocket getSocket() {
        return socket;
    }

    public void setSocket(MulticastSocket socket) {
        this.socket = socket;
    }

    public DatagramPacket getPacket() {
        return packet;
    }

    public void setPacket(DatagramPacket packet) {
        this.packet = packet;
    }

    public InetAddress getAddress() {
        return address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }
}
