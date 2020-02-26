
package bellman.com.task.requests.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import bellman.com.task.models.Data;

public class AreaCategoriesResponse implements Serializable {

    @SerializedName("status_code")
    @Expose
    private int statusCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;
    private final static long serialVersionUID = 321737382635450846L;

    /**
     * No args constructor for use in serialization
     */
    public AreaCategoriesResponse() {
    }

    /**
     * @param data
     * @param message
     * @param statusCode
     */
    public AreaCategoriesResponse(int statusCode, String message, Data data) {
        super();
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
