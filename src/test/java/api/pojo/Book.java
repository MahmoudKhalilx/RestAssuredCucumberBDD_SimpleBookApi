package api.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {

    private int id;
    private boolean available;

    public int getId() { return id; }
    public boolean isAvailable() { return available; }
}
