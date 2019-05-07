package atmaauto.atmaauto.com.atmaauto.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailPengadaan_data {
    @SerializedName("data")
    @Expose
    private List<DetailPengadaan> data = null;

    public List<DetailPengadaan> getData() {
        return data;
    }

    public void setData(List<DetailPengadaan> data) {
        this.data = data;
    }
}
