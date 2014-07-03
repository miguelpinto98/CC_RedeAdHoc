package adhoc_app;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.HashSet;

public class Hello extends Protocol implements Serializable {
    public static int helloInterval = 5000;
    public static int deadInterval = 20000;
    
    private String message;
    private HashSet<String> vizinhos;

    public Hello(String message, InetAddress emissor, String host, HashSet<String> vizinhos) {
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

    public HashSet<String> getVizinhos() {
        return vizinhos;
    }

    public void setVizinhos(HashSet<String> vizinhos) {
        this.vizinhos = vizinhos;
    }
}
