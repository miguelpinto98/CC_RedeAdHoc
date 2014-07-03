package adhoc_app;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;

public class RouteReply extends Protocol implements Serializable {
    private ArrayList<String> caminho;
    
    public RouteReply(InetAddress ip, String host, ArrayList<String> cam) {
        super(ip, host);
        this.caminho = cam;
    }

    public ArrayList<String> getCaminho() {
        return caminho;
    }

    public void setCaminho(ArrayList<String> caminho) {
        this.caminho = caminho;
    }
}
