package atmaauto.atmaauto.com.atmaauto.models;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Motor_data {
    @SerializedName("data")
    @Expose
    private List<Motor> data = null;

    public List<Motor> getData() {
        return data;
    }

    public void setData(List<Motor> data) {
        this.data = data;
    }
}
