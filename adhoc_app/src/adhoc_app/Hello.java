package adhoc_app;

import java.io.Serializable;

public class Hello extends Protocolo implements Serializable {
    public static int helloInterval = 5000;
    public static int deadInterval = 20000;
    
    private String message;

    public Hello(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
