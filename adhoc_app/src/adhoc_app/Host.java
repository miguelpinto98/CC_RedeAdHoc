package adhoc_app;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class Host implements Serializable {
    private InetAddress ip;
    private String name;
    private int porta;
    private GregorianCalendar lastResponse;
    private int status;
    private HashMap<String,InetAddress> vizinhos;

    public Host(InetAddress ip, String name, int porta, HashMap<String,InetAddress> vizinhos) {
        this.ip = ip;
        this.name = name;
        this.porta = porta;
        this.lastResponse = new GregorianCalendar();
        this.status = 1;
        this.vizinhos = vizinhos;
    }
    
    public Host(InetAddress address, String name) {
        this.ip = address;
        this.name = name;
        this.porta = 9999;
        this.lastResponse = null;
        this.status = 1;
        this.vizinhos = null;
    }
    
    private Host(Host aThis) {
        this.ip = aThis.getIp();
        this.name = aThis.getName();
        this.porta = aThis.getPorta();
        this.lastResponse = (GregorianCalendar) aThis.getLastResponse().clone();
        this.status = aThis.getStatus();
        this.vizinhos = aThis.getVizinhos();
    }

    public InetAddress getIp() {
        return ip;
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPorta() {
        return porta;
    }

    public void setPorta(int porta) {
        this.porta = porta;
    }

    public GregorianCalendar getLastResponse() {
        return lastResponse;
    }

    public void setLastResponse(GregorianCalendar lastResponse) {
        this.lastResponse = lastResponse;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public HashMap<String,InetAddress> getVizinhos() {
        return vizinhos;
    }

    public void setVizinhos(HashMap<String,InetAddress> vizinhos) {
        this.vizinhos = vizinhos;
    }

    @Override
    protected Object clone() {
        return new Host(this);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        
        if ((o == null) || (this.getClass() != o.getClass())) {
            return false;
        } else {
            Host h = (Host) o;
            return this.name.equals(h.getName());
        }
    }    

    @Override
    public int hashCode() {
        int hash = 7;        
        hash = 19 * hash + this.name.hashCode();
        return hash;
    }

    public long difInterval() {
        GregorianCalendar gc = new GregorianCalendar();
        return gc.getTimeInMillis()-lastResponse.getTimeInMillis();
    }
}
