package atmaauto.atmaauto.com.atmaauto.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Jasa_data {

    @SerializedName("data")
    @Expose
    private List<Jasa> data = null;

    public List<Jasa> getData() {
        return data;
    }

    public void setData(List<Jasa> data) {
        this.data = data;
    }
}
