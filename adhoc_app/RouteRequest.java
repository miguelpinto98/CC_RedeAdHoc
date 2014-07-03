package adhoc_app;

import java.io.Serializable;
import java.net.InetAddress;

public class RouteRequest extends Protocol implements Serializable {
    public static int TIMEOUT = 5000;
    public static int RADIUS = 2;
    
    private String destino;

    public RouteRequest(InetAddress ip, String host, String dest) {
        super(ip, host);
        this.destino = dest;
    }

    public String getNodoDestino() {
        return destino;
    }

    public void setNodoDestino(String destino) {
        this.destino = destino;
    }
    
    

}
