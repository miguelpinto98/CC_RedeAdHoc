package adhoc_app;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;

public class RouteRequest extends Protocol implements Serializable {
    public static int TIMEOUT = 5000;
    public static int RADIUS = 2;
    
    private String destino;
    private ArrayList<Host> visitados;
    //private GregorianCalendar time;

    public RouteRequest(InetAddress ip, String host, String dest) {
        super(ip, host);
        this.destino = dest;
        this.visitados = new ArrayList<Host>();
        //this.time = new GregorianCalendar();
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public ArrayList<Host> getVisitados() {
        return visitados;
    }
    
    public void setVisitados(ArrayList<Host> visitados) {
        this.visitados = visitados;
    }
    
    public void addVisitados(Host nodo) {
        this.visitados.add(nodo);
    }

    boolean existe(String name) {
        for(Host h : this.getVisitados())
            if(h.getName().contains(name))
                return true;
        return false;
    }
    
}
