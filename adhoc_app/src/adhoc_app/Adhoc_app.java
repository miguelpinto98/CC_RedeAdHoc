package adhoc_app;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
 
public class Adhoc_app {
    //private HashMap<String,Vizinho> vizinhos;
    private ArrayList<String> vizinhos;

    public ArrayList<String> getVizinhos() {
        return vizinhos;
    }

    public void setVizinhos(ArrayList<String> vizinhos) {
        this.vizinhos = vizinhos;
    }
    
    public static void main(String[] args) throws IOException {
        MulticastSocket socket = new MulticastSocket(9999);
        InetAddress address = InetAddress.getByName("FF02::1");
        socket.joinGroup(address);
        
        System.out.println("Entrei no Servidor ");
        while(true) {
            HandlerReceive tr = new HandlerReceive(socket,address);
            HandlerSend ts = new HandlerSend(socket,address);
            tr.start();
            ts.start();
        
            if(false) {
                socket.leaveGroup(address);
                socket.close();
        }
        }
    }
}