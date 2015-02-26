package adhoc_app;

import java.io.Serializable;
import java.net.InetAddress;

public class TW extends Protocol implements Serializable {
    public static int messagesize = 500;
    
    private String destino;
    private String text;
    
    public TW(InetAddress ip, String host, String destino, String text) {
        super(ip, host);
        this.destino = destino;
        this.text = text;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }
    
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
}
