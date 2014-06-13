package adhoc_app;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
 
public class Adhoc_app {
    public static final int PORT = 9999;
    public static final String GROUP = "FF02::1";
    //public static final String GROUP = "230.0.0.1";

    
    /* Guardar os Vizinhos de um Host */
    public static HashMap<String,Host> vizinhos = new HashMap<String,Host>();
   
    public static void main(String[] args) throws IOException {
        try {
            MulticastSocket socket = new MulticastSocket(PORT);
            InetAddress address = InetAddress.getByName(GROUP);
            socket.joinGroup(address);

            InetAddress emissor = Utilities.encontrarMeuIP();
            String NameEmissor = Utilities.encontraHostName();
            
            Timer send = new Timer();
           
            System.out.println("MEU IP: "+emissor.getHostName()+"\nA Iniciar");
            HandlerReceive tr = new HandlerReceive(socket,address);
           
            HandlerSend ts = new HandlerSend(socket,address,emissor.getHostName(),NameEmissor);
            send.schedule(ts, 0, Hello.helloInterval);

            tr.start();
            tr.join();
        
        } catch (InterruptedException ex) {
            Logger.getLogger(Adhoc_app.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}