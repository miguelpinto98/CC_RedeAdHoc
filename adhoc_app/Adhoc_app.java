package adhoc_app;

import java.io.*;
import java.net.*;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
 
public class Adhoc_app {
    public static final int PORT = 9999;
    public static final String GROUP = "FF02::1";    
    public static final String HOSTNAME = Utilities.encontraHostName();
    
    /* Guardar os Vizinhos de um Host */
    public static HashMap<String,Host> vizinhos = new HashMap<String,Host>();
    
    public static synchronized void neighborsSync(String NameEmissor, InetAddress IpEmissor, int Porta, HashSet<String> vizs) {  
        if(Adhoc_app.vizinhos.containsKey(NameEmissor)) {                    
            Host h = Adhoc_app.vizinhos.remove(NameEmissor);

            if(h.getStatus()==0) {
                h.setStatus(1);
                h.setLastResponse(new GregorianCalendar());
            } else {
                h.setLastResponse(new GregorianCalendar());
            }
            
            h.setVizinhos(vizs);
            addHost(h);
        } else {
            if(!NameEmissor.equals(Adhoc_app.HOSTNAME)) {
                System.out.println("A guardar host "+NameEmissor+" como vizinho...");
            addHost(new Host(IpEmissor, NameEmissor, Porta,vizs));
            }
        }
    }
    
    public static void addHost(Host h) {
        vizinhos.put(h.getName(), h);
    }
    
    public static synchronized HashMap<String,Host> CloneMapVizinhos() {
        HashMap<String,Host> aux = new HashMap<String,Host>();
        
        for(Host h : vizinhos.values())
            aux.put(h.getName(), (Host) h.clone());
         
        return aux;
    }
    
    public static synchronized void StatusSync() {
        for(Host h : vizinhos.values()) {
            if(h.getStatus()==1 && h.difInterval() > Hello.deadInterval) {
                System.out.println("Ligacao Perdida com "+h.getName());
                h.setStatus(0);
            }
        }
    }
    
    public static void main(String[] args) throws IOException {
        try {
            MulticastSocket socket = new MulticastSocket(PORT);
            InetAddress address = InetAddress.getByName(GROUP);
            socket.joinGroup(address);

            InetAddress emissor = Utilities.encontrarMeuIP();
            //String NameEmissor = Utilities.encontraHostName();
            
            Timer send = new Timer();
           
            System.out.println("MEU IP: "+emissor.getHostName()+"\nA Iniciar");
            HandlerReceive tr = new HandlerReceive(socket,address);
           
            HandlerSend ts = new HandlerSend(socket,address,emissor.getHostName(),HOSTNAME);
            send.schedule(ts, 0, Hello.helloInterval);

            HandlerMessage hm = new HandlerMessage();
            
            tr.start();
            hm.start();
            
            tr.join();
            hm.join();
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Adhoc_app.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}