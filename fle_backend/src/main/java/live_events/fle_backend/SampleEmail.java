package live_events.fle_backend;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SampleEmail {

    private final Integer id;

    private final String email;

    @JsonCreator
    public SampleEmail(@JsonProperty("id") Integer id,
                         @JsonProperty("email") String email) {
        this.id = id;
        this.email = email;
    }

    public Integer getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }

    @Override
    public String toString() {
        return "SampleMessage{id=" + this.id + ", message='" + this.email + "'}";
    }

}
