package atmaauto.atmaauto.com.atmaauto.models;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransaksiPenjualan_data {

    @SerializedName("data")
    @Expose
    private List<TransaksiPenjualan> data = null;

    public List<TransaksiPenjualan> getData() {
        return data;
    }

    public void setData(List<TransaksiPenjualan> data) {
        this.data = data;
    }

}
