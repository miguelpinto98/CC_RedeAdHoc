package adhoc_app;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
 
public class Adhoc_app {
    //private HashMap<String,Vizinho> vizinhos;
    private ArrayList<String> vizinhos;

    public ArrayList<String> getVizinhos() {
        return vizinhos;
    }

    public void setVizinhos(ArrayList<String> vizinhos) {
        this.vizinhos = vizinhos;
    }
    
    public static byte[] serializa(Object o) throws IOException{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(o);

			return baos.toByteArray();
	 }

	 public static Object desSerializa(byte[] ab) throws IOException, ClassNotFoundException{
			ByteArrayInputStream bais = new ByteArrayInputStream(ab);

			ObjectInputStream ois = new ObjectInputStream(bais);

			return ois.readObject();
	 }

    
    public static void main(String[] args) throws IOException {
        try {
            MulticastSocket socket = new MulticastSocket(9999);
            InetAddress address = InetAddress.getByName("230.0.0.1");
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