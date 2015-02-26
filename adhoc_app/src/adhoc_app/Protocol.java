package adhoc_app;

import java.io.Serializable;
import java.net.InetAddress;

public abstract class Protocol implements Serializable {
    private InetAddress ipEmissor;
    private String hostNameEmissor;
    
    public Protocol(InetAddress ip,String host) {
        this.ipEmissor = ip;
        this.hostNameEmissor = host;
    }
    
    public InetAddress getIpEmissor() {
        return ipEmissor;
    }

    public void setIpEmissor(InetAddress ipEmissor) {
        this.ipEmissor = ipEmissor;
    }

    public String getHostNameEmissor() {
        return hostNameEmissor;
    }

    public void setHostNameEmissor(String hostName) {
        this.hostNameEmissor = hostName;
    }
}
