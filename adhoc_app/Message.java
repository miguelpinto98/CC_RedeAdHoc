package adhoc_app;

import java.io.Serializable;
import java.net.InetAddress;

public class Message extends Protocol implements Serializable {
    private String to;
    private String message;
    
    public Message(InetAddress ip, String host, String d, String m) {
        super(ip, host);
        this.to = d;
        this.message = m;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
