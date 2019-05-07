package atmaauto.atmaauto.com.atmaauto.models;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailSparepart_data {

    @SerializedName("data")
    @Expose
    private List<DetailSparepart> data = null;

    public List<DetailSparepart> getData() {
        return data;
    }

    public void setData(List<DetailSparepart> data) {
        this.data = data;
    }
}
