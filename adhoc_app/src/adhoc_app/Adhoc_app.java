package adhoc_app;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
 
public class Adhoc_app {
    public static HashMap<String,Vizinhos> VIZINHOS = new HashMap<>();
    public static final int PORT = 9999;
    //public static final String GROUP = "FF02::1";
    public static final String GROUP = "230.0.0.1";
    
    public static void iniciaListaVizinhosHost() {
        //VIZINHOS.put(address.getHostName(),new Vizinhos());
    }
    
    public static void main(String[] args) throws IOException {
        try {
            MulticastSocket socket = new MulticastSocket(PORT);
            InetAddress address = InetAddress.getByName(GROUP);
            socket.joinGroup(address);

            InetAddress abc = Utilities.encontrarMeuIP();
            
            Timer send = new Timer();
            Timer verVizinhos = new Timer();
           
            System.out.println("MEU IP: "+abc.getCanonicalHostName()+"\nA Iniciar");
            HandlerReceive tr = new HandlerReceive(socket,address);
           
            HandlerSend ts = new HandlerSend(socket,address);
            send.schedule(ts, 0, Hello.helloInterval);

            VerificaVizinhos vv = new VerificaVizinhos();
            verVizinhos.schedule(vv, 0, Hello.deadInterval);
            
            tr.start();
            tr.join();
        
            /*
            if(false) {
                socket.leaveGroup(address);
                socket.close();
            }*/
        } catch (InterruptedException ex) {
            Logger.getLogger(Adhoc_app.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}