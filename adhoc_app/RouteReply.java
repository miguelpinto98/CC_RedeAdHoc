package adhoc_app;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;

public class RouteReply extends Protocol implements Serializable {
    private ArrayList<Host> caminho;
    private int step;
    
    public RouteReply(InetAddress ip, String host, int n) {
        super(ip, host);
        this.caminho = new ArrayList<Host>();
        this.step = n;
    }

    public ArrayList<Host> getCaminho() {
        return caminho;
    }

    public void setCaminho(ArrayList<Host> caminho) {
        ArrayList<Host> aux = new ArrayList<Host>();
        
        for(Host h : caminho)
            aux.add(new Host(h.getIp(), h.getName()));
        
        this.caminho = aux;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }
}
