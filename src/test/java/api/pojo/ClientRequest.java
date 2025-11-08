package api.pojo;

public class ClientRequest {
    private String clientName;
    private String clientEmail;

    public ClientRequest(String clientName, String clientEmail) {
        this.clientName = clientName;
        this.clientEmail = clientEmail;
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientEmail() {
        return clientEmail;
    }
}
