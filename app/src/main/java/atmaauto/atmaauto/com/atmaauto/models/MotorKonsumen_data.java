package atmaauto.atmaauto.com.atmaauto.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MotorKonsumen_data {
    @SerializedName("data")
    @Expose
    private List<MotorKonsumen> data = null;

    public List<MotorKonsumen> getData() {
        return data;
    }

    public void setData(List<MotorKonsumen> data) {
        this.data = data;
    }
}
