package adhoc_app;

import java.util.ArrayList;

public class Vizinhos {
    private ArrayList<Host> hosts;

    public Vizinhos() {
        this.hosts = new ArrayList<>();
    }
    
    public ArrayList<Host> getHosts() {
        return hosts;
    }

    public void setHosts(ArrayList<Host> hosts) {
        this.hosts = hosts;
    }
}
