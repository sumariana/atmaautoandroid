package atmaauto.atmaauto.com.atmaauto.models;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sparepart_data {
    @SerializedName("data")
    @Expose
    private List<Sparepart> data = null;

    public List<Sparepart> getData() {
        return data;
    }

    public void setData(List<Sparepart> data) {
        this.data = data;
    }
}
