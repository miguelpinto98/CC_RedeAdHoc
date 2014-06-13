package adhoc_app;

import java.io.Serializable;

public class Hello implements Serializable {
    public static int helloInterval = 5000;
    public static int deadInterval = 20000;
    
    private String message;
    private String ipEmissor;
    private String hostNameEmissor;

    public Hello(String message, String emissor, String host) {
        this.message = message;
        this.ipEmissor = emissor;
        this.hostNameEmissor = host;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIpEmissor() {
        return ipEmissor;
    }

    public void setIpEmissor(String ipEmissor) {
        this.ipEmissor = ipEmissor;
    }

    public String getHostNameEmissor() {
        return hostNameEmissor;
    }

    public void setHostNameEmissor(String hostName) {
        this.hostNameEmissor = hostName;
    }
}
