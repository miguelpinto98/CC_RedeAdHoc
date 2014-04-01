package adhoc_app;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
 
public class Adhoc_app {
    private HashMap<String,Vizinho> vizinhos;
    
    public HashMap<String, Vizinho> getVizinhos() {
        return vizinhos;
    }

    public void setVizinhos(HashMap<String, Vizinho> vizinhos) {
        this.vizinhos = vizinhos;
    }
    
    public static void main(String[] args) throws IOException {
        try {
            MulticastSocket socket = new MulticastSocket(9999);
            InetAddress address = InetAddress.getByName("FF02::1");
            socket.joinGroup(address);
            
            Timer send = new Timer();
            
            
            System.out.println("A Iniciar");
            
            HandlerReceive tr = new HandlerReceive(socket,address);
            HandlerSend ts = new HandlerSend(socket,address);
            //ts.start();
            send.schedule(ts, 0, Hello.helloInterval);
            
            tr.start();
            tr.join();
        
            
            if(false) {
                socket.leaveGroup(address);
                socket.close();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Adhoc_app.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}