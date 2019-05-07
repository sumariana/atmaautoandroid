package atmaauto.atmaauto.com.atmaauto.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransaksiPengadaan_data {

    @SerializedName("data")
    @Expose
    private List<TransaksiPengadaan> data = null;

    public List<TransaksiPengadaan> getData() {
        return data;
    }

    public void setData(List<TransaksiPengadaan> data) {
        this.data = data;
    }
}
