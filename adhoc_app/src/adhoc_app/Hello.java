package adhoc_app;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.HashMap;

public class Hello extends Protocol implements Serializable {
    public static int helloInterval = 5000;
    public static int deadInterval = 20000;
    
    private String message;
    //private HashSet<String> vizinhos;
    private HashMap<String,InetAddress> vizinhos;

    public Hello(String message, InetAddress emissor, String host, HashMap<String,InetAddress> vizinhos) {
        super(emissor,host);
        this.message = message;
        this.vizinhos = vizinhos;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HashMap<String,InetAddress> getVizinhos() {
        return vizinhos;
    }

    public void setVizinhos(HashMap<String,InetAddress> vizinhos) {
        this.vizinhos = vizinhos;
    }
}
