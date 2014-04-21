package adhoc_app;

import java.util.GregorianCalendar;

public class Host {
    private String id;
    private GregorianCalendar lastResponse;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GregorianCalendar getLastResponse() {
        return lastResponse;
    }

    public void setLastResponse(GregorianCalendar lastResponse) {
        this.lastResponse = lastResponse;
    }
}
