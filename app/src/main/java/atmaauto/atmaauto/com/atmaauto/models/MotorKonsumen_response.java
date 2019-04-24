package atmaauto.atmaauto.com.atmaauto.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class MotorKonsumen_response {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private MotorKonsumen data;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public MotorKonsumen getData() {
        return data;
    }

    public void setData(MotorKonsumen data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
