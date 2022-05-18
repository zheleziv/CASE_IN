package case_in.db.demo.response;

import java.util.Objects;

public class StringResponse {

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public StringResponse(String value) {
        this.value = value;
    }

    private String value;
}
