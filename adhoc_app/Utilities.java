package adhoc_app;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Utilities {    
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
    
     public static InetAddress encontrarMeuIP() {
        Enumeration<NetworkInterface> e = null;
	
        try {
            e = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e1) {
            System.out.println("Exit (Main): Failed find own address.");
	    System.exit(1);
	}
	
        while(e.hasMoreElements()) {        	
            NetworkInterface n=(NetworkInterface) e.nextElement();
            Enumeration<InetAddress> ee = n.getInetAddresses();
            
            while(ee.hasMoreElements()) {
	   	InetAddress i = (InetAddress) ee.nextElement();
	   				
	   	if(Inet6Address.class.isInstance(i) && i.isLoopbackAddress() == false) {
                    return i;
                }
            }
	} 
	return null ;
    }
    
    public static String encontraHostName() {
        Process p;
        try {
            p = Runtime.getRuntime().exec("hostname");
            p.waitFor();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
 
            return reader.readLine();
        } catch (IOException ex) {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
