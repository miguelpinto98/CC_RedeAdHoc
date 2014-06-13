package adhoc_app;

import java.util.GregorianCalendar;

public class Host {
    private String ip;
    private String name;
    private int porta;
    private GregorianCalendar lastResponse;
    private int status;

    public Host(String ip, String name, int porta) {
        this.ip = ip;
        this.name = name;
        this.porta = porta;
        this.lastResponse = new GregorianCalendar();
        this.status = 1;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
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
