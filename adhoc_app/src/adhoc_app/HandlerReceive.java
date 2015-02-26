package adhoc_app;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
            boolean flag = true;
            
            while(true) { 
                socket.receive(this.packet);
                Object obj = Utilities.desSerializa(this.packet.getData());
                
                if(obj instanceof Protocol) {
                    if(obj.getClass().equals(Hello.class)) {    // HELLO
                        Hello hl = (Hello) obj;
                
                        if(!hl.getHostNameEmissor().equals(Adhoc_app.HOSTNAME))
                            System.out.println(hl.getHostNameEmissor()+">>Recebi o pacote HELLO de "+hl.getIpEmissor()); 

                        neighborsTest(hl.getHostNameEmissor(),hl.getIpEmissor(),hl.getVizinhos());
                        testStatusHost();
                    }
                    
                    if(obj.getClass().equals(RouteRequest.class)) { // ROUTE REQUEST
                        boolean encontrado = false;
                        RouteRequest rq = (RouteRequest) obj;
                        rq.addVisitados(new Host(Utilities.encontrarMeuIP(), Adhoc_app.HOSTNAME));
                        
                        for(Host h : Adhoc_app.CloneMapVizinhos().values()) {
                            
                            for(String nodo : h.getVizinhos().keySet()) {
                                if(nodo.equals(rq.getDestino())) {
                                    rq.addVisitados(h); rq.addVisitados(new Host(h.getVizinhos().get(nodo), nodo));
                                    
                                    RouteReply rrp = new RouteReply(rq.getIpEmissor(), rq.getDestino(), rq.getVisitados().size()-2);
                                    rrp.setCaminho(rq.getVisitados());
                                    prepareSerializa(rrp, h.getIp());
                                    
                                    //System.out.println("ALOOOOOOOOOOOOOO"+rq.getIpEmissor().getHostName()+"OOOOOOOOOOOOOOO"+h.getName()+nodo);
                                    encontrado = true;
                                }
                            }     
                            if(!encontrado && !rq.existe(h.getName())) {
                                //System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR"+h.getName());
                                prepareSerializa(rq, h.getIp());
                            }
                        }
                    }
                    
                    if(obj.getClass().equals(RouteReply.class)) {   //ROUTE REPLY
                        RouteReply rp = (RouteReply) obj;
                        int s = rp.getStep()-1;
                        
                        if(s<0) {
                            System.out.println("CHEGOU PEDIDO ROUTE REPLY");
                            
                        } else {
                            if(s==0) {
                                rp.setStep(s);
                                
                                System.out.println("A CHEGAR AO PROPRIO EMISSOR"+rp.getHostNameEmissor());
                                prepareSerializa(rp, rp.getIpEmissor());
                            } else {
                                Host h = rp.getCaminho().get(s);
                                rp.setStep(s);
                                System.out.println("A VOLTAR PARA TRAS DESTINO-"+h.getName()+h.getIp().getHostName());
                                prepareSerializa(rp, h.getIp());
                            }
                        }
                    }
                    
                    if(obj.getClass().equals(TW.class)) {       // TW
                        TW tw = (TW) obj;
                        String dest = tw.getDestino().replace("#", "");
                        
                        Message msg = new Message(tw.getIpEmissor(),tw.getHostNameEmissor(),dest,tw.getText());
                        prepareSerializa(msg, tw.getIpEmissor());
                    }
                    
                    if(obj.getClass().equals(Message.class)) {      //MESSAGE
                        Message msg = (Message) obj;
                        
                        if(Adhoc_app.HOSTNAME.equals(msg.getTo())) {
                            System.out.println("### MSG FINAL -> "+msg.getMessage());
                        } else {
                            if(Adhoc_app.CloneMapVizinhos().containsKey(msg.getTo())) {
                                Host h = Adhoc_app.CloneMapVizinhos().get(msg.getTo());
                                System.out.println("### A Mandar MSG AO VIZINHO "+h.getIp().getHostName());                            
                                prepareSerializa(msg, h.getIp());
                            } else {
                                flag = false;
                                Iterator<Host> it = Adhoc_app.CloneMapVizinhos().values().iterator();
                                while(it.hasNext() && !flag) {
                                    Host h = it.next();
                                    Iterator<String> str = h.getVizinhos().keySet().iterator();
                                    while(str.hasNext() && !flag) {
                                        String nodo = str.next();
                                        if(nodo.equals(msg.getTo())) {
                                            System.out.println("### A Preparar MSG AO SUB-VIZINHO");
                                            prepareSerializa(msg,h.getIp());
                                            flag = true;
                                        }
                                    }   
                                }
                            }
                        }
                        
                        if(!flag) {
                            RouteRequest rrq = new RouteRequest(Adhoc_app.ADDDRESS, msg.getHostNameEmissor(), msg.getTo());
                            rrq.addVisitados(new Host(Adhoc_app.ADDDRESS, Adhoc_app.HOSTNAME));
   
                            for(Host h : Adhoc_app.CloneMapVizinhos().values()) {
                                prepareSerializa(rrq,h.getIp());
                            }
                            
                            
                        }
                        flag=true;
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(HandlerSend.class.getName()).log(Level.SEVERE, "asdas", ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HandlerReceive.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void prepareSerializa(Object obj, InetAddress address) throws IOException {
        byte[] bs = Utilities.serializa(obj);
        DatagramPacket dp = new DatagramPacket(bs,bs.length,address,9999);
        this.socket.send(dp);
    }

    private void testStatusHost() {
        Adhoc_app.StatusSync();
    }

    private void neighborsTest(String NameEmissor, InetAddress IpEmissor, HashMap<String,InetAddress> vizinhos) {
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
