package adhoc_app;

public class Hello extends Protocolo {
    public static int helloInterval;
    public static int deadInterval;
    
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
