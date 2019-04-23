package atmaauto.atmaauto.com.atmaauto.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Konsumen_data {
    @SerializedName("data")
    @Expose
    private List<Konsumen> data = null;

    public List<Konsumen> getData() {
        return data;
    }

    public void setData(List<Konsumen> data) {
        this.data = data;
    }
}
